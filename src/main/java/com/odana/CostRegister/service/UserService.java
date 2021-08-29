package com.odana.CostRegister.service;

import com.odana.CostRegister.model.User;
import com.odana.CostRegister.model.dto.UserSignUpDto;

public interface UserService {
    User signUpUserWithDto(UserSignUpDto user);
    User findByEmail(String email);
}
