package com.odana.CostRegister.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CostFilterDto {

    @NotNull
    @JsonProperty("start")
    private String start;

    @NotNull
    @JsonProperty("end")
    private String end;

    @NotNull
    @JsonProperty("user")
    private String user;

    public CostFilterDto(String start, String end, String user) {
        this.start = start;
        this.end = end;
        this.user = user;
    }
}
