package com.odana.CostRegister.controller;

import com.odana.CostRegister.service.CostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cost/average/daily")
public class CostDailyAverageController {

    @Autowired
    private CostService costService;

    public CostDailyAverageController() {}

    @RequestMapping(method = RequestMethod.GET)
    public float calculateAverageDailyCosts() {
        return costService.calculateAverageDailyCosts();
    }
}
