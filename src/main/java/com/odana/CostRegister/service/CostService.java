package com.odana.CostRegister.service;

import com.odana.CostRegister.model.Cost;
import com.odana.CostRegister.model.dto.CostFilterDto;
import com.odana.CostRegister.model.dto.CostRegisterDto;

import java.util.List;

public interface CostService {
    Cost registerCostWithDto(CostRegisterDto costRegisterDto);
    List<Cost> filterCosts(CostFilterDto costFilterDto);
    float sumCosts(CostFilterDto costFilterDto);
    float calculateAverageDailyCosts();
}
