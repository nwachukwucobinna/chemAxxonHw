package com.odana.CostRegister.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CostRegisterDto {
    @JsonProperty("id")
    private int id;

    @NotNull
    @JsonProperty("amount")
    private float amount;

    @NotNull
    @JsonProperty("user")
    private String user;

    public CostRegisterDto(int id, float amount, String user) {
        this.id = id;
        this.amount = amount;
        this.user = user;

    }
}
