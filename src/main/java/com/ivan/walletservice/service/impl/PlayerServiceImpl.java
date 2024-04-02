package com.ivan.walletservice.service.impl;

import com.ivan.walletservice.exception.PlayerNotFoundException;
import com.ivan.walletservice.model.entity.Player;
import com.ivan.walletservice.repository.PlayerRepository;
import com.ivan.walletservice.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    @Override
    @Transactional
    public void updateBalance(Long id, BigDecimal balance) {
        Player playerId = playerRepository.findById(id)
                .orElseThrow(() -> new PlayerNotFoundException("The player with id " + id + " not found!"));
        playerId.setBalance(balance);
        playerRepository.save(playerId);
    }

    @Override
    public BigDecimal getPlayerBalance(Long id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new PlayerNotFoundException("The player with id " + id + " not found!"))
                .getBalance();
    }

    @Override
    public Player getByLogin(String login) {
        return playerRepository.findByLogin(login)
                .orElseThrow(() -> new PlayerNotFoundException("Player with login " + login + " not found!"));
    }

    @Override
    public Player getById(Long id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new PlayerNotFoundException("Player with id " + id + " not found!"));
    }
}