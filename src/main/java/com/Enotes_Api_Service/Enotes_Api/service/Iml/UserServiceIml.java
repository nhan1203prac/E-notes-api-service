package com.Enotes_Api_Service.Enotes_Api.service.Iml;

import com.Enotes_Api_Service.Enotes_Api.Utils.Validation;
import com.Enotes_Api_Service.Enotes_Api.dto.UserDto;
import com.Enotes_Api_Service.Enotes_Api.entity.Role;
import com.Enotes_Api_Service.Enotes_Api.entity.User;
import com.Enotes_Api_Service.Enotes_Api.repository.RoleRepository;
import com.Enotes_Api_Service.Enotes_Api.repository.UserRepository;
import com.Enotes_Api_Service.Enotes_Api.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class UserServiceIml implements UserService {
    @Autowired
    private UserRepository UserRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private Validation validation;

    @Override
    public Boolean registerUser(UserDto userDto) {
        validation.userValidation(userDto);
        User user = modelMapper.map(userDto,User.class);
        setRole(userDto, user);
        User savedUser = UserRepository.save(user);
        if(!ObjectUtils.isEmpty(savedUser)){
            return true;
        }
        return false;
    }
    void setRole(UserDto userDto, User user) {
        List<Integer> roleDto = userDto.getRoles().stream().map(r->r.getId()).toList();
        List<Role> role = roleRepository.findAllById(roleDto);
        user.setRoles(role);
    }
}
