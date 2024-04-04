package com.ivan.walletservice.service.impl;

import com.ivan.walletservice.exception.TransactionOperationException;
import com.ivan.walletservice.model.entity.Transaction;
import com.ivan.walletservice.repository.TransactionRepository;
import com.ivan.walletservice.service.PlayerService;
import com.ivan.walletservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static com.ivan.walletservice.model.type.TransactionType.CREDIT;
import static com.ivan.walletservice.model.type.TransactionType.DEBIT;

/**
 * Service implementation for managing transactions.
 * This service handles debit and credit operations for player balances.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final PlayerService playerService;

    /**
     * Retrieve the transaction history for a specific player.
     *
     * @param playerId The ID of the player to retrieve the transaction history for
     * @return List of transactions related to the player
     */
    @Override
    public List<Transaction> getPlayerHistory(Long playerId) {
        return transactionRepository.findAllByPlayerId(playerId);
    }

    /**
     * Debit an amount from a player's balance.
     *
     * @param amount   The amount to be debited
     * @param playerId The ID of the player
     * @throws TransactionOperationException if the player balance is insufficient
     */
    @Override
    @Transactional
    public void debit(BigDecimal amount, Long playerId) {
        BigDecimal playerBalance = playerService.getPlayerBalance(playerId);

        if (playerBalance.compareTo(amount) < 0) {
            throw new TransactionOperationException("Insufficient funds!");
        }

        BigDecimal result = playerBalance.subtract(amount);

        transactionRepository.save(Transaction.builder()
                .type(DEBIT)
                .amount(result)
                .player(playerService.getById(playerId))
                .build());

        playerService.updateBalance(playerId, result);
    }

    /**
     * Credit an amount to a player's balance.
     *
     * @param amount   The amount to be credited
     * @param playerId The ID of the player
     */
    @Override
    @Transactional
    public void credit(BigDecimal amount, Long playerId) {
        BigDecimal playerBalance = playerService.getPlayerBalance(playerId);

        BigDecimal result = playerBalance.add(amount);

        transactionRepository.save(Transaction.builder()
                .type(CREDIT)
                .amount(result)
                .player(playerService.getById(playerId))
                .build());

        playerService.updateBalance(playerId, result);
    }
}