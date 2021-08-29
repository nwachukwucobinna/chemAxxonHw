package com.odana.CostRegister.service.impl;

import com.odana.CostRegister.mapper.UserMapper;
import com.odana.CostRegister.model.User;
import com.odana.CostRegister.model.dto.UserSignUpDto;
import com.odana.CostRegister.repository.UserRepository;
import com.odana.CostRegister.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    UserService userService;

    @BeforeEach
    void init() {
        userService = new UserServiceImpl(userRepository, userMapper);
    }

    @Test
    public void testFindByEmail() {
        String email = "test@test.com";

        User user = new User(email, "test", "test", "test");

        when(userRepository.findByEmail(email)).thenReturn(user);

        User userByEmail = userService.findByEmail(email);

        //when a user exists with the email
        assertThat(userByEmail).isNotNull();

        //when a user does not exist with the email
        assertThat(userService.findByEmail("fake@test.com")).isNull();

    }

    @Test
    public void testSignUpUserWithDto() {

        String email = "test@test.com";
        String password = "test";
        String name = "test";
        String surname = "test";
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);



        UserSignUpDto userSignUpDto = new UserSignUpDto(1,email, password, name, surname);

        User user = new User(email,password,name,surname);

        User userWithEncodedPassword = user;

        userWithEncodedPassword.setPassword(encodedPassword);

        when(userMapper.userSignUpDtoToUser(userSignUpDto)).thenReturn(user);

        when(userRepository.saveAndFlush(user)).thenReturn(userWithEncodedPassword);

        //when a user signs up successfully
        assertThat(userService.signUpUserWithDto(userSignUpDto)).isNotNull();
        assertThat(userService.signUpUserWithDto(userSignUpDto)).isEqualTo(userWithEncodedPassword);
    }
}
