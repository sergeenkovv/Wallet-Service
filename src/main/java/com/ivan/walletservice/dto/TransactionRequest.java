package com.ivan.walletservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class TransactionRequest {

    private BigDecimal amount;
    private String playerLogin;
}