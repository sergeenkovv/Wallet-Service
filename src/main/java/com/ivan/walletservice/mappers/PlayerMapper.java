package com.ivan.walletservice.mappers;

import com.ivan.walletservice.dto.PlayerDto;
import com.ivan.walletservice.model.entity.Player;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PlayerMapper {

    @Mapping(source = "login", target = "login")
    @Mapping(source = "balance", target = "balance")
    PlayerDto toDto(Player entity);
}