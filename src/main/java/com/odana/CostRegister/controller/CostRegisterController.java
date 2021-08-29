package com.odana.CostRegister.controller;

import com.odana.CostRegister.model.Cost;
import com.odana.CostRegister.model.dto.CostRegisterDto;
import com.odana.CostRegister.service.CostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/cost/register")
public class CostRegisterController {

    @Autowired
    private CostService costService;

    public CostRegisterController() {}

    @RequestMapping(method = RequestMethod.POST)
    public Cost costRegister(@RequestBody CostRegisterDto costRegisterDto) {
        return costService.registerCostWithDto(costRegisterDto);
    }
}
