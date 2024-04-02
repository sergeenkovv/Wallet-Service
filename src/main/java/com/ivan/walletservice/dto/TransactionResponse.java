package com.ivan.walletservice.dto;

import java.math.BigDecimal;

public record TransactionResponse(
        Long id,
        String type,
        BigDecimal amount) {
}