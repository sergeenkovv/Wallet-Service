package com.ivan.walletservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class TransactionResponse {

    private Long id;
    private String type;
    private BigDecimal amount;
}