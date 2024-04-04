package com.ivan.walletservice.dto;

import java.util.List;

/**
 * TransactionHistoryResponse record representing a response object containing player's login and a list of transaction responses.
 */
public record TransactionHistoryResponse(
        String playerLogin,
        List<TransactionResponse> transactions) {
}