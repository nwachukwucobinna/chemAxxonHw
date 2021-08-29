package com.odana.CostRegister.service.impl;

import com.odana.CostRegister.mapper.UserMapper;
import com.odana.CostRegister.model.User;

import com.odana.CostRegister.model.dto.UserSignUpDto;
import com.odana.CostRegister.repository.UserRepository;
import com.odana.CostRegister.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public User signUpUserWithDto(UserSignUpDto userSignUpDto) {
        User signedUpUser = userMapper.userSignUpDtoToUser(userSignUpDto);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(signedUpUser.getPassword());
        signedUpUser.setPassword(encodedPassword);
        return userRepository.saveAndFlush(signedUpUser);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
