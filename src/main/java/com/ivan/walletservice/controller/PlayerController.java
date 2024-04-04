package com.ivan.walletservice.controller;

import com.ivan.walletservice.dto.*;
import com.ivan.walletservice.mappers.PlayerMapper;
import com.ivan.walletservice.mappers.TransactionMapper;
import com.ivan.walletservice.model.entity.Player;
import com.ivan.walletservice.security.SecurityUtils;
import com.ivan.walletservice.service.PlayerService;
import com.ivan.walletservice.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * PlayerController class that handles player-related API endpoints.
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/players")
public class PlayerController {

    private final PlayerService playerService;
    private final TransactionService transactionService;
    private final PlayerMapper playerMapper;
    private final TransactionMapper transactionMapper;

    /**
     * Retrieves the balance for a player based on the provided login.
     *
     * @param login The login of the player.
     * @return ResponseEntity<?> containing the player's balance.
     */
    @GetMapping("/balance")
    public ResponseEntity<?> getBalance(@RequestParam String login) {
        if (!SecurityUtils.isValidLogin((login))) return ResponseEntity.badRequest()
                .body(new ExceptionResponse("Incorrect login!"));
        Player player = playerService.getByLogin(login);
        return ResponseEntity.ok(playerMapper.toDto(player));
    }

    /**
     * Retrieves the transaction history for a player based on the provided login.
     *
     * @param login The login of the player.
     * @return ResponseEntity<?> containing the player's transaction history.
     */
    @GetMapping("/history")
    public ResponseEntity<?> getHistory(@RequestParam String login) {
        if (!SecurityUtils.isValidLogin(login)) return ResponseEntity.badRequest()
                .body(new ExceptionResponse("Incorrect login!"));
        Player player = playerService.getByLogin(login);
        List<TransactionResponse> playerHistory = transactionMapper.toDtoList(
                transactionService.getPlayerHistory(player.getId()));
        return ResponseEntity.ok().body(new TransactionHistoryResponse(login, playerHistory));
    }

    /**
     * Processes a debit transaction for a player.
     *
     * @param request The TransactionRequest object containing transaction details.
     * @return ResponseEntity<?> indicating the status of the transaction.
     */
    @PostMapping("/transactions/debit")
    public ResponseEntity<?> debit(@RequestBody @Valid TransactionRequest request) {
        if (!SecurityUtils.isValidLogin(request.playerLogin())) return ResponseEntity.badRequest()
                .body(new ExceptionResponse("Incorrect login"));
        Player player = playerService.getByLogin(request.playerLogin());
        transactionService.debit(request.amount(), player.getId());
        return ResponseEntity.ok(new SuccessResponse("Transaction completed successfully!"));
    }

    /**
     * Processes a credit transaction for a player.
     *
     * @param request The TransactionRequest object containing transaction details.
     * @return ResponseEntity<?> indicating the status of the transaction.
     */
    @PostMapping("/transactions/credit")
    public ResponseEntity<?> credit(@RequestBody @Valid TransactionRequest request) {
        if (!SecurityUtils.isValidLogin(request.playerLogin())) return ResponseEntity.badRequest()
                .body(new ExceptionResponse("Incorrect login"));
        Player player = playerService.getByLogin(request.playerLogin());
        transactionService.credit(request.amount(), player.getId());
        return ResponseEntity.ok(new SuccessResponse("Transaction completed successfully!"));
    }
}