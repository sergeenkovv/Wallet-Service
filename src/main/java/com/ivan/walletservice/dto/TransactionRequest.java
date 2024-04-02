package com.ivan.walletservice.dto;

import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;

public record TransactionRequest(
        String playerLogin,
        @DecimalMin(message = "Amount must not less than 0.0!", value = "0.0", inclusive = false)
        BigDecimal amount) {
}