package com.ivan.walletservice.dto;

import java.math.BigDecimal;

/**
 * TransactionResponse record representing a response object for transactions with an ID, type, and amount.
 */
public record TransactionResponse(
        Long id,
        String type,
        BigDecimal amount) {
}