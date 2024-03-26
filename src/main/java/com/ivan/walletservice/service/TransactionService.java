package com.ivan.walletservice.service;

import com.ivan.walletservice.model.entity.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {

    List<Transaction> getPlayerHistory(Long playerId);

    void debit(BigDecimal amount, Long playerId);

    void credit(BigDecimal amount, Long playerId);
}