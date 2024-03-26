package com.ivan.walletservice.dto;

import java.util.List;

public record TransactionHistoryResponse(String playerLogin,
                                         List<TransactionResponse> transactions) {
}