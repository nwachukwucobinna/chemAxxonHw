package com.odana.CostRegister.controller;

import com.odana.CostRegister.model.User;
import com.odana.CostRegister.model.dto.UserSignUpDto;
import com.odana.CostRegister.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/signup")
public class UserSignUpController {

    @Autowired
    private UserService userService;

    public UserSignUpController() {}

    @RequestMapping(method = RequestMethod.POST)
    public User userSignUp(@RequestBody UserSignUpDto userSignUpDto) {
        return userService.signUpUserWithDto(userSignUpDto);
    }
}
