package com.odana.CostRegister.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.time.LocalDateTime;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CostSumController.class)
public class CostSumControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    CostService costService;

    @Test
    @WithMockUser(username = "test", password = "test", roles = "USER")
    public void testCostSum() throws Exception {
        String user = "test@test.com";
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime earliest = now.minusDays(2);
        LocalDateTime latest = now.plusDays(2);

        CostFilterDto costFilterDto = new CostFilterDto(earliest.toString(), latest.toString(), user);


        Mockito.when(costService.sumCosts(costFilterDto)).thenReturn(3F);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/cost/sum")
                        .content(new ObjectMapper().writeValueAsString(costFilterDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
