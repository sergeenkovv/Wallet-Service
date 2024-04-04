package com.ivan.walletservice.service;

import com.ivan.walletservice.model.entity.Transaction;

import java.math.BigDecimal;
import java.util.List;

/**
 * Interface for managing transaction operations such as retrieving player history, debiting, and crediting amounts.
 */
public interface TransactionService {

    /**
     * Retrieve the transaction history for a player by player ID.
     *
     * @param playerId The ID of the player to retrieve the transaction history
     * @return List of transactions related to the player
     */
    List<Transaction> getPlayerHistory(Long playerId);

    /**
     * Debit a specified amount from a player's balance.
     *
     * @param amount   The amount to be debited
     * @param playerId The ID of the player
     */
    void debit(BigDecimal amount, Long playerId);

    /**
     * Credit a specified amount to a player's balance.
     *
     * @param amount   The amount to be credited
     * @param playerId The ID of the player
     */
    void credit(BigDecimal amount, Long playerId);
}