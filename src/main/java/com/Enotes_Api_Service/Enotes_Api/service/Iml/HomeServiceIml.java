package com.Enotes_Api_Service.Enotes_Api.service.Iml;

import com.Enotes_Api_Service.Enotes_Api.entity.AccountStatus;
import com.Enotes_Api_Service.Enotes_Api.entity.User;
import com.Enotes_Api_Service.Enotes_Api.exception.ResourceNotfoundException;
import com.Enotes_Api_Service.Enotes_Api.exception.SuccessException;
import com.Enotes_Api_Service.Enotes_Api.repository.UserRepository;
import com.Enotes_Api_Service.Enotes_Api.service.HomeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class HomeServiceIml implements HomeService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public Boolean verifyAccount(Integer userId, String verificationCode) throws ResourceNotfoundException {
        log.info("HomeServiceImpl : verifyAccount() : Start");
        User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotfoundException("invalid user"));
        if(user.getStatus().getVerificationCode() == null) {
            log.info("message : Account already verified");
            throw new SuccessException("Account already verified");
        }
        if(user.getStatus().getVerificationCode().equals(verificationCode)){
            AccountStatus status = user.getStatus();
            status.setVerificationCode(null);
            status.setIsActive(true);
            userRepository.save(user);
            log.info("message : Account verified successfully");
            return true;
        }
        return false;
    }
}
