package com.ivan.walletservice.dto;

import java.math.BigDecimal;

public record PlayerResponse(
        String login,
        BigDecimal balance) {
}