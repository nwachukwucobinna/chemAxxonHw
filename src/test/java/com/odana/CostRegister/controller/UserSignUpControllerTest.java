package com.odana.CostRegister.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.odana.CostRegister.model.User;
import com.odana.CostRegister.model.dto.UserSignUpDto;
import com.odana.CostRegister.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserSignUpController.class)
public class UserSignUpControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Test
    @WithMockUser(username = "test", password = "test", roles = "USER")
    public void testUserSignUp() throws Exception {
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

        Mockito.when(userService.signUpUserWithDto(userSignUpDto)).thenReturn(user);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/user/signup")
                    .content(new ObjectMapper().writeValueAsString(userSignUpDto))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
