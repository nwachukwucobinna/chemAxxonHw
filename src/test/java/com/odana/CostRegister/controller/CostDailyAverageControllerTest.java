package com.odana.CostRegister.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.odana.CostRegister.model.Cost;
import com.odana.CostRegister.model.dto.CostFilterDto;
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

@ExtendWith(SpringExtension.class)
@WebMvcTest(CostDailyAverageController.class)
public class CostDailyAverageControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    CostService costService;

    @Test
    @WithMockUser(username = "test", password = "test", roles = "USER")
    public void testCalculateAverageDailyCosts() throws Exception{


        Mockito.when(costService.calculateAverageDailyCosts()).thenReturn(3F);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/cost/average/daily")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
