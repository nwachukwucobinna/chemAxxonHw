package com.odana.CostRegister.mapper;


import com.odana.CostRegister.model.User;
import com.odana.CostRegister.model.dto.UserSignUpDto;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring"
)
public interface UserMapper {
    User userSignUpDtoToUser(UserSignUpDto userSignUpDto);
}
