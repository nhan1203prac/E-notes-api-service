package com.Enotes_Api_Service.Enotes_Api.service.Iml;

import com.Enotes_Api_Service.Enotes_Api.entity.AccountStatus;
import com.Enotes_Api_Service.Enotes_Api.entity.User;
import com.Enotes_Api_Service.Enotes_Api.exception.ResourceNotfoundException;
import com.Enotes_Api_Service.Enotes_Api.exception.SuccessException;
import com.Enotes_Api_Service.Enotes_Api.repository.UserRepository;
import com.Enotes_Api_Service.Enotes_Api.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomeServiceIml implements HomeService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public Boolean verifyAccount(Integer userId, String verificationCode) throws ResourceNotfoundException {
        User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotfoundException("invalid user"));
        if(user.getStatus().getVerificationCode() == null) {
            throw new SuccessException("Account already verified");
        }
        if(user.getStatus().getVerificationCode().equals(verificationCode)){
            AccountStatus status = user.getStatus();
            status.setVerificationCode(null);
            status.setIsActive(true);
            userRepository.save(user);

            return true;
        }
        return false;
    }
}
