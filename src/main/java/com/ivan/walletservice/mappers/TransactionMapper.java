package com.ivan.walletservice.mappers;

import com.ivan.walletservice.dto.TransactionResponse;
import com.ivan.walletservice.model.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TransactionMapper {

    List<TransactionResponse> toDtoList(List<Transaction> entities);
}