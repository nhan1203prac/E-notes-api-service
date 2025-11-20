package com.Enotes_Api_Service.Enotes_Api.service.Iml;

import com.Enotes_Api_Service.Enotes_Api.Utils.EmailSend;
import com.Enotes_Api_Service.Enotes_Api.dto.EmailRequest;
import com.Enotes_Api_Service.Enotes_Api.dto.PasswordChangeRequest;
import com.Enotes_Api_Service.Enotes_Api.dto.PasswordResetRequest;
import com.Enotes_Api_Service.Enotes_Api.entity.User;
import com.Enotes_Api_Service.Enotes_Api.exception.ResourceNotfoundException;
import com.Enotes_Api_Service.Enotes_Api.handler.CommonUtil;
import com.Enotes_Api_Service.Enotes_Api.repository.UserRepository;
import com.Enotes_Api_Service.Enotes_Api.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Service
public class UserServiceIml implements UserService {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailSend emailSend;
    @Override
    public void changePassword(PasswordChangeRequest passwordChangeRequest) {
        User user = CommonUtil.getLoggedUser();
        if(bCryptPasswordEncoder.matches(passwordChangeRequest.getOldPassword(), user.getPassword())){
            throw new IllegalArgumentException("Old password does not match");
        }
        user.setPassword(bCryptPasswordEncoder.encode(passwordChangeRequest.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public void sendEmailPasswordReset(String email, HttpServletRequest req) throws ResourceNotfoundException, MessagingException, UnsupportedEncodingException {
        User user = userRepository.findByEmail(email);
        if(ObjectUtils.isEmpty(user)){
           throw new ResourceNotfoundException("Invalid email");
        }
        String passwordToken  = UUID.randomUUID().toString();
        user.getStatus().setPasswordResetToken(passwordToken);
        User updatedUser = userRepository.save(user);
        sendEmailRequest(updatedUser, req);

    }



    public void sendEmailRequest(User user, HttpServletRequest url) throws  MessagingException, UnsupportedEncodingException {
        String link = CommonUtil.getUrl(url);
        String message = String.format(
                "Hi, <b>%s</b><br>" +
                        "<p>You have requested to reset your password.</p><br>" +
                        "<p>Click the link below to change your password</p><br>"+
                        "<p><a href='%s/api/v1/home/verify-password-link?uid=%d&&code=%s'>Change my password</a></p>"+
                        "Thanks,<br>Enotes.com",
                user.getFirstName(),
                link,
                user.getId(),
                user.getStatus().getPasswordResetToken()
        );

        EmailRequest emailRequest = EmailRequest.builder()
                .to(user.getEmail())
                .subject("Password reset link")
                .message(message)
                .title("Password Reset")
                .build();

        emailSend.send(emailRequest);
    }

    @Override
    public void verifyPasswordResetLink(Integer uid, String code) throws ResourceNotfoundException {
        User user = userRepository.findById(uid).orElseThrow(()->new ResourceNotfoundException("User not found with id = "+uid));
        if(StringUtils.hasText(code)){
            if(!StringUtils.hasText(user.getStatus().getPasswordResetToken())){
                throw new IllegalArgumentException("Password already reset");
            }
            if(!user.getStatus().getPasswordResetToken().equals(code)){
                throw new IllegalArgumentException("Password reset token does not match");
            }
        }else{
            throw new IllegalArgumentException("Invalid code provided");
        }
    }

    @Override
    public void resetPassword(PasswordResetRequest passwordResetRequest) throws ResourceNotfoundException {
        User user = userRepository.findById(passwordResetRequest.getUid()).orElseThrow(()-> new ResourceNotfoundException("User not found with id = "+passwordResetRequest.getUid()));
        user.setPassword(bCryptPasswordEncoder.encode(passwordResetRequest.getNewPassword()));
        user.getStatus().setPasswordResetToken(null);
        userRepository.save(user);
    }
}
