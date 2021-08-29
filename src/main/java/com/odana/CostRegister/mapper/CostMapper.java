package com.odana.CostRegister.mapper;

import com.odana.CostRegister.model.Cost;
import com.odana.CostRegister.model.dto.CostRegisterDto;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring"
)
public interface CostMapper {
    Cost costRegisterDtoToCost(CostRegisterDto costRegisterDto);
}
