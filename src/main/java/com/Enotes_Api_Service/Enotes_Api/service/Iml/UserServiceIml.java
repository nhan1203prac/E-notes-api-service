package com.Enotes_Api_Service.Enotes_Api.service.Iml;

import com.Enotes_Api_Service.Enotes_Api.Config.CustomUserDetail;
import com.Enotes_Api_Service.Enotes_Api.Utils.EmailSend;
import com.Enotes_Api_Service.Enotes_Api.Utils.Validation;
import com.Enotes_Api_Service.Enotes_Api.dto.EmailRequest;
import com.Enotes_Api_Service.Enotes_Api.dto.LoginRequest;
import com.Enotes_Api_Service.Enotes_Api.dto.UserDto;
import com.Enotes_Api_Service.Enotes_Api.entity.AccountStatus;
import com.Enotes_Api_Service.Enotes_Api.entity.Role;
import com.Enotes_Api_Service.Enotes_Api.entity.User;
import com.Enotes_Api_Service.Enotes_Api.repository.RoleRepository;
import com.Enotes_Api_Service.Enotes_Api.repository.UserRepository;
import com.Enotes_Api_Service.Enotes_Api.response.LoginResponse;
import com.Enotes_Api_Service.Enotes_Api.service.JwtService;
import com.Enotes_Api_Service.Enotes_Api.service.UserService;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceIml implements UserService {
    @Autowired
    private UserRepository UserRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private EmailSend emailSend;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private Validation validation;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Override
    public Boolean registerUser(UserDto userDto, String url) throws MessagingException, UnsupportedEncodingException {
        validation.userValidation(userDto);
        User user = modelMapper.map(userDto,User.class);
        setRole(userDto, user);
        AccountStatus status = AccountStatus.builder()
                .isActive(false)
                .verificationCode(UUID.randomUUID().toString())
                .build();
        user.setStatus(status);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User savedUser = UserRepository.save(user);
        if(!ObjectUtils.isEmpty(savedUser)){
            sendEmail(savedUser, url);
            return true;
        }
        return false;
    }



    private void sendEmail(User savedUser, String url) throws MessagingException, UnsupportedEncodingException {
        String message = String.format(
                "Hi, <b>%s</b><br>" +
                        "Your account has been successfully registered.<br>" +
                        "<a href='%s/api/v1/home/verify?uid=%d&&code=%s'>Click here to verify your account</a><br><br>" +
                        "Thanks,<br>Enotes.com",
                savedUser.getFirstName(),
                url,
                savedUser.getId(),
                savedUser.getStatus().getVerificationCode()
        );

        EmailRequest emailRequest = EmailRequest.builder()
                .to(savedUser.getEmail())
                .subject("Account Created")
                .message(message)
                .title("Account Creating Confirmation")
                .build();

        emailSend.send(emailRequest);
    }

    void setRole(UserDto userDto, User user) {
        List<Integer> roleDto = userDto.getRoles().stream().map(r->r.getId()).toList();
        List<Role> role = roleRepository.findAllById(roleDto);
        log.info("Role List: {}", role);
        user.setRoles(role);
    }

//    @Override
//    public LoginResponse login(LoginRequest user) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
//        );
//        if(authentication.isAuthenticated()){
//
//            CustomUserDetail customUserDetail = (CustomUserDetail) authentication.getPrincipal();
//            String token = jwtService.generateJwtToken(customUserDetail.getUser());
//            LoginResponse loginResponse = LoginResponse.builder()
//                    .user(modelMapper.map(customUserDetail.getUser(), UserDto.class))
//                    .token(token)
//                    .build();
//            return loginResponse;
//        }
//        return null;
//    }
    @Override
    public LoginResponse login(LoginRequest user) {
        try {
            System.out.println("password matches: " +
                    passwordEncoder.matches(user.getPassword(), UserRepository.findByEmail(user.getUsername()).getPassword()));

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );
            CustomUserDetail customUserDetail = (CustomUserDetail) authentication.getPrincipal();
            String token = jwtService.generateJwtToken(customUserDetail.getUser());
            return LoginResponse.builder()
                    .user(modelMapper.map(customUserDetail.getUser(), UserDto.class))
                    .token(token)
                    .build();
        } catch (AuthenticationException ex) {
            log.error("Authentication failed: {}", ex.getMessage());
            return null; // hoặc ném custom exception / trả response có thông báo
        }
    }

}
