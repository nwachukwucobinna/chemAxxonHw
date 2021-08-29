package com.odana.CostRegister.service.impl;

import com.odana.CostRegister.mapper.CostMapper;
import com.odana.CostRegister.model.Cost;
import com.odana.CostRegister.model.dto.CostFilterDto;
import com.odana.CostRegister.model.dto.CostRegisterDto;
import com.odana.CostRegister.repository.CostRepository;
import com.odana.CostRegister.service.CostService;
import com.odana.CostRegister.service.cost.impl.CostServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class CostServiceImplTest {

    @Mock
    private CostRepository costRepository;

    @Mock
    private CostMapper costMapper;

    CostService costService;

    @BeforeEach
    void init() {
        costService = new CostServiceImpl(costRepository, costMapper);
    }

    @Test
    public void testRegisterCostWithDto() {

        float amount = 1;
        String user = "test@test.com";
        LocalDateTime now = LocalDateTime.now();

        CostRegisterDto costRegisterDto = new CostRegisterDto(1, 1, user);

        Cost cost = new Cost(amount, user, now);

        Cost costWithAlteredTimestamp = cost;

        costWithAlteredTimestamp.setTimestamp(now.plusDays(1));

        Mockito.when(costMapper.costRegisterDtoToCost(costRegisterDto)).thenReturn(cost);

        Mockito.when(costRepository.saveAndFlush(cost)).thenReturn(costWithAlteredTimestamp);

        //when a cost is registered successfully
        Assertions.assertThat(costService.registerCostWithDto(costRegisterDto)).isNotNull();
        Assertions.assertThat(costService.registerCostWithDto(costRegisterDto)).isEqualTo(costWithAlteredTimestamp);

    }

    @Test
    public void testFilterCosts() {

        float amount = 1;
        String user = "test@test.com";
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime earliest = now.minusDays(2);
        LocalDateTime latest = now.plusDays(2);

        CostFilterDto costFilterDto = new CostFilterDto(earliest.toString(), latest.toString(), user);

        List<Cost> costs = Arrays.asList(
                new Cost(amount,user, earliest),
                new Cost(amount,user, now),
                new Cost(amount,user, latest)
        );

        Mockito.when(costRepository.findByUserAndInterval(earliest,latest,user)).thenReturn(costs);

        //when a cost exists with the user and within the interval
        Assertions.assertThat(costService.filterCosts(costFilterDto)).isNotEmpty();
        Assertions.assertThat(costService.filterCosts(costFilterDto)).isEqualTo(costs);

        //when a cost exists with the user but not within the interval
        Assertions.assertThat(costService.filterCosts(new CostFilterDto(latest.plusDays(1).toString(), latest.plusDays(2).toString(), user))).isEmpty();

        //when a cost exists within the interval but not with the user
        Assertions.assertThat(costService.filterCosts(new CostFilterDto(earliest.toString(), latest.toString(), "fake@test.com"))).isEmpty();
    }

    @Test
    public void testSumCosts() {

        float amount = 1;
        String user = "test@test.com";
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime earliest = now.minusDays(2);
        LocalDateTime latest = now.plusDays(2);

        CostFilterDto costFilterDto = new CostFilterDto(earliest.toString(), latest.toString(), user);

        List<Cost> costs = Arrays.asList(
                new Cost(amount,user, earliest),
                new Cost(amount,user, now),
                new Cost(amount,user, latest)
        );

        Mockito.when(costRepository.findByUserAndInterval(earliest,latest,user)).thenReturn(costs);

        //when a cost exists with the user and within the interval
        Assertions.assertThat(costService.sumCosts(costFilterDto)).isNotZero();
        Assertions.assertThat(costService.sumCosts(costFilterDto)).isEqualTo(amount*costs.size());

        //when a cost exists with the user but not within the interval
        Assertions.assertThat(costService.sumCosts(new CostFilterDto(latest.plusDays(1).toString(), latest.plusDays(2).toString(), user))).isZero();

        //when a cost exists within the interval but not with the user
        Assertions.assertThat(costService.sumCosts(new CostFilterDto(earliest.toString(), latest.toString(), "fake@test.com"))).isZero();
    }

    @Test
    public void testCalculateAverageDailyCosts() {

        float amount = 1;
        String user = "test@test.com";
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime earliest = now.minusDays(2);
        LocalDateTime latest = now.plusDays(2);

        List<Cost> costs = Arrays.asList(
                new Cost(amount,user, earliest),
                new Cost(amount,user, now),
                new Cost(amount,user, latest)
        );

        Mockito.when(costRepository.findAll()).thenReturn(costs);

        //when the average daily cost is calculated successfully
        Assertions.assertThat(costService.calculateAverageDailyCosts()).isNotZero();
        Assertions.assertThat(costService.calculateAverageDailyCosts()).isEqualTo((amount*costs.size())/ ChronoUnit.DAYS.between(earliest,latest));
    }
}
