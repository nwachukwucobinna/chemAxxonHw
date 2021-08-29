package com.odana.CostRegister.service.cost.impl;

import com.odana.CostRegister.mapper.CostMapper;
import com.odana.CostRegister.mapper.UserMapper;
import com.odana.CostRegister.model.Cost;
import com.odana.CostRegister.model.dto.CostFilterDto;
import com.odana.CostRegister.model.dto.CostRegisterDto;
import com.odana.CostRegister.repository.CostRepository;
import com.odana.CostRegister.repository.UserRepository;
import com.odana.CostRegister.service.CostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class CostServiceImpl implements CostService {

    @Autowired
    private CostRepository costRepository;

    @Autowired
    private CostMapper costMapper;

    @Autowired
    public CostServiceImpl(CostRepository costRepository, CostMapper costMapper) {
        this.costRepository = costRepository;
        this.costMapper = costMapper;
    }

    @Override
    public Cost registerCostWithDto(CostRegisterDto costRegisterDto) {

        Cost registeredCost = costMapper.costRegisterDtoToCost(costRegisterDto);
        registeredCost
                .setTimestamp(LocalDateTime.now());
        return costRepository.saveAndFlush(registeredCost);
    }

    @Override
    public List<Cost> filterCosts(CostFilterDto costFilterDto) {

        return costRepository.findByUserAndInterval(
                LocalDateTime.parse(costFilterDto.getStart()),
                LocalDateTime.parse(costFilterDto.getEnd()),
                costFilterDto.getUser());
    }

    @Override
    public float sumCosts(CostFilterDto costFilterDto) {
        return filterCosts(costFilterDto).stream()
                .map(cost -> cost.getAmount())
                .reduce(0F, Float::sum);
    }

    @Override
    public float calculateAverageDailyCosts() {
        List<Cost> costs = costRepository.findAll();
        LocalDateTime earliest = LocalDateTime.now();
        LocalDateTime latest = LocalDateTime.MIN;
        float numOfDays = 0;
        float amountSum = costs.stream()
                .map(cost -> cost.getAmount())
                .reduce(0F, Float::sum);
        for (Cost cost: costs) {
            LocalDateTime timestamp = cost.getTimestamp();
            if(timestamp.isBefore(earliest)) {
                earliest = timestamp;
            }
            if(timestamp.isAfter(latest)) {
                latest = timestamp;
            }
        }
        numOfDays = ChronoUnit.DAYS.between(earliest,latest);
        if(numOfDays == 0) {
            return amountSum;
        }
        return amountSum/numOfDays;

    }
}
