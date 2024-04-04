package com.ivan.walletservice.mappers;

import com.ivan.walletservice.dto.TransactionResponse;
import com.ivan.walletservice.model.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Interface TransactionMapper defining conversion methods between a list of Transaction entities and a list of TransactionResponse DTOs.
 */
@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TransactionMapper {

    /**
     * Maps a list of Transaction entities to a list of TransactionResponse DTOs.
     *
     * @param entities The list of Transaction entities to map.
     * @return List of TransactionResponse DTOs representing the mapped entities.
     */
    List<TransactionResponse> toDtoList(List<Transaction> entities);
}