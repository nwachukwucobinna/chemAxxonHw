package com.odana.CostRegister.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserSignUpDto {

    @JsonProperty("id")
    private int id;

    @Email
    @NotNull
    @JsonProperty("email")
    private String email;

    @NotNull
    @JsonProperty("password")
    private String password;

    @NotNull
    @JsonProperty("name")
    private String name;

    @NotNull
    @JsonProperty("surname")
    private String surname;

    public UserSignUpDto(int id, String email, String password, String name, String surname) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
    }
}
