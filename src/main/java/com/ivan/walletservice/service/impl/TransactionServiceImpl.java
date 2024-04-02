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

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final PlayerService playerService;

    @Override
    public List<Transaction> getPlayerHistory(Long playerId) {
        return transactionRepository.findAllByPlayerId(playerId);
    }

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