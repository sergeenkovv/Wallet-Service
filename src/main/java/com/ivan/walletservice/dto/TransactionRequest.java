package com.ivan.walletservice.dto;

import java.math.BigDecimal;

public record TransactionRequest(BigDecimal amount,
                                 String playerLogin) {
}