package com.odana.CostRegister.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.odana.CostRegister.model.Cost;
import com.odana.CostRegister.model.dto.CostRegisterDto;
import com.odana.CostRegister.service.CostService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CostRegisterController.class)
public class CostRegisterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    CostService costService;

    @Test
    @WithMockUser(username = "test", password = "test", roles = "USER")
    public void testCostRegister() throws Exception{

        float amount = 1;
        String user = "test@test.com";
        LocalDateTime now = LocalDateTime.now();

        CostRegisterDto costRegisterDto = new CostRegisterDto(1, 1, user);

        Cost cost = new Cost(amount, user, now);

        Cost costWithAlteredTimestamp = cost;

        costWithAlteredTimestamp.setTimestamp(now.plusDays(1));

        Mockito.when(costService.registerCostWithDto(costRegisterDto)).thenReturn(costWithAlteredTimestamp);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/cost/register")
                        .content(new ObjectMapper().writeValueAsString(costRegisterDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
