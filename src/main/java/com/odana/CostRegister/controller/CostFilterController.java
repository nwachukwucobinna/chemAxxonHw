package com.odana.CostRegister.controller;

import com.odana.CostRegister.model.Cost;
import com.odana.CostRegister.model.dto.CostFilterDto;
import com.odana.CostRegister.service.CostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cost/filter")
public class CostFilterController {

    @Autowired
    private CostService costService;

    public CostFilterController() {}

    @RequestMapping(method = RequestMethod.GET)
    public List<Cost> costFilter(@RequestBody CostFilterDto costFilterDto) {
        return costService.filterCosts(costFilterDto);
    }
}
