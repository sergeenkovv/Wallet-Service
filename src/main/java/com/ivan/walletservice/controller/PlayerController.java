package com.ivan.walletservice.controller;

import com.ivan.walletservice.dto.*;
import com.ivan.walletservice.mappers.PlayerMapper;
import com.ivan.walletservice.mappers.TransactionMapper;
import com.ivan.walletservice.model.entity.Player;
import com.ivan.walletservice.security.SecurityUtils;
import com.ivan.walletservice.service.PlayerService;
import com.ivan.walletservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/players")
public class PlayerController {

    private final PlayerService playerService;
    private final TransactionService transactionService;
    private final PlayerMapper playerMapper;
    private final TransactionMapper transactionMapper;

    @GetMapping("/balance")
    public ResponseEntity<?> getBalance(@RequestParam String login) {
        if (!SecurityUtils.isValidLogin((login))) return ResponseEntity.badRequest()
                .body(new ExceptionResponse("Incorrect login"));
        Player player = playerService.getByLogin(login);
        return ResponseEntity.ok(playerMapper.toDto(player));
    }

    @GetMapping("/history")
    public ResponseEntity<?> getHistory(@RequestParam String login) {
        if (!SecurityUtils.isValidLogin(login)) return ResponseEntity.badRequest()
                .body(new ExceptionResponse("Incorrect login"));
        Player player = playerService.getByLogin(login);
        List<TransactionResponse> playerHistory = transactionMapper.toDTOList(
                transactionService.getPlayerHistory(player.getId()));
        return ResponseEntity.ok().body(new TransactionHistoryResponse(login, playerHistory));
    }

    @PostMapping("/transactions/debit")
    public ResponseEntity<?> debit(@RequestBody TransactionRequest request) {
        if (!SecurityUtils.isValidLogin(request.getPlayerLogin())) return ResponseEntity.badRequest()
                .body(new ExceptionResponse("Incorrect login"));
        Player player = playerService.getByLogin(request.getPlayerLogin());
        transactionService.debit(request.getAmount(), player.getId());
        return ResponseEntity.ok(new SuccessResponse("Transaction completed successfully!"));
    }

    @PostMapping("/transactions/credit")
    public ResponseEntity<?> credit(@RequestBody TransactionRequest request) {
        if (!SecurityUtils.isValidLogin(request.getPlayerLogin())) return ResponseEntity.badRequest()
                .body(new ExceptionResponse("Incorrect login"));
        Player player = playerService.getByLogin(request.getPlayerLogin());
        transactionService.credit(request.getAmount(), player.getId());
        return ResponseEntity.ok(new SuccessResponse("Transaction completed successfully!"));
    }
}