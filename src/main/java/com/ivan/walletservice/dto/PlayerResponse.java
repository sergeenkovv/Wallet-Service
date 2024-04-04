package com.ivan.walletservice.dto;

import java.math.BigDecimal;

/**
 * PlayerResponse record representing a response object containing the player's login and balance.
 */
public record PlayerResponse(
        String login,
        BigDecimal balance) {
}