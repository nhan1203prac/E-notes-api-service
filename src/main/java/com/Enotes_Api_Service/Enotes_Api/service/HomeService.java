package com.Enotes_Api_Service.Enotes_Api.service;

import com.Enotes_Api_Service.Enotes_Api.exception.ResourceNotfoundException;

public interface HomeService {
    public Boolean verifyAccount(Integer userId, String verificationCode) throws ResourceNotfoundException;
}
