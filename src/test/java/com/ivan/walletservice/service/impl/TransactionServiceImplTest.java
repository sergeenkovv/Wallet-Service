package com.ivan.walletservice.service.impl;

import com.ivan.walletservice.exception.TransactionOperationException;
import com.ivan.walletservice.model.entity.Player;
import com.ivan.walletservice.model.entity.Transaction;
import com.ivan.walletservice.model.type.TransactionType;
import com.ivan.walletservice.repository.TransactionRepository;
import com.ivan.walletservice.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private PlayerService playerService;
    @Mock
    private PasswordEncoder passwordEncoder;

    private Player player;

    @BeforeEach
    void setup() {
        String login = "ivan";
        String password = "password";
        player = Player.builder()
                .id(1L)
                .login(login)
                .password(passwordEncoder.encode(password))
                .balance(new BigDecimal("100"))
                .build();
    }

    @Test
    void getPlayerHistory_Success() {
        List<Transaction> expectedTransactions = Arrays.asList(
                Transaction.builder()
                        .id(player.getId())
                        .type(TransactionType.DEBIT)
                        .amount(new BigDecimal("100.00"))
                        .player(player)
                        .build(),

                Transaction.builder()
                        .id(player.getId())
                        .type(TransactionType.DEBIT)
                        .amount(new BigDecimal("100.00"))
                        .player(player)
                        .build(),

                Transaction.builder()
                        .id(player.getId())
                        .type(TransactionType.DEBIT)
                        .amount(new BigDecimal("100.00"))
                        .player(player)
                        .build()
        );
        when(transactionRepository.findAllByPlayerId(player.getId())).thenReturn(expectedTransactions);

        List<Transaction> actualPlayerHistory = transactionService.getPlayerHistory(player.getId());

        assertThat(actualPlayerHistory).containsExactlyElementsOf(expectedTransactions);
    }

    @Test
    void debit_Success() {
        BigDecimal initialBalance = player.getBalance();
        when(playerService.getPlayerBalance(player.getId())).thenReturn(initialBalance);

        BigDecimal debitAmount = new BigDecimal("50");
        transactionService.debit(debitAmount, player.getId());

        BigDecimal result = initialBalance.subtract(debitAmount);

        verify(playerService).updateBalance(player.getId(), result);
    }

    @Test
    void debit_TransactionOperationException() {
        BigDecimal initialBalance = player.getBalance();
        BigDecimal debitAmount = new BigDecimal("110");

        when(playerService.getPlayerBalance(player.getId())).thenReturn(initialBalance);

        assertThrows(TransactionOperationException.class,
                () -> transactionService.debit(debitAmount, player.getId()));
    }

    @Test
    void credit_Success() {
        BigDecimal initialBalance = player.getBalance();
        when(playerService.getPlayerBalance(player.getId())).thenReturn(initialBalance);

        BigDecimal creditAmount = new BigDecimal("50");
        transactionService.credit(creditAmount, player.getId());

        BigDecimal result = initialBalance.add(creditAmount);

        verify(playerService).updateBalance(player.getId(), result);
    }
}