package com.ivan.walletservice.mappers;

import com.ivan.walletservice.dto.PlayerResponse;
import com.ivan.walletservice.model.entity.Player;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

/**
 * PlayerMapper interface that defines conversion methods between the Player entity and the PlayerResponse DTO.
 */
@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PlayerMapper {

    /**
     * Converts the Player entity to PlayerResponse DTO
     *
     * @param entity The Player entity to be converted.
     * @return PlayerResponse DTO representing the converted entity.
     */
    PlayerResponse toDto(Player entity);
}