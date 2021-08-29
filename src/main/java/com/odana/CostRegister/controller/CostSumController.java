package com.odana.CostRegister.controller;

import com.odana.CostRegister.model.Cost;
import com.odana.CostRegister.model.dto.CostFilterDto;
import com.odana.CostRegister.model.dto.CostRegisterDto;
import com.odana.CostRegister.service.CostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cost/sum")
public class CostSumController {

    @Autowired
    private CostService costService;

    public CostSumController() {}

    @RequestMapping(method = RequestMethod.GET)
    public float costSum(@RequestBody CostFilterDto costFilterDto) {
        return costService.sumCosts(costFilterDto);
    }
}
