package com.ivan.walletservice.service.impl;

import com.ivan.walletservice.exception.PlayerNotFoundException;
import com.ivan.walletservice.model.entity.Player;
import com.ivan.walletservice.repository.PlayerRepository;
import com.ivan.walletservice.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Implementation of the PlayerService interface providing operations related to players.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    /**
     * Updates the balance of a player with the specified ID.
     *
     * @param id      The ID of the player whose balance is to be updated.
     * @param balance The new balance to be set for the player.
     * @throws PlayerNotFoundException If the player with the provided ID is not found.
     */
    @Override
    @Transactional
    public void updateBalance(Long id, BigDecimal balance) {
        Player playerId = playerRepository.findById(id)
                .orElseThrow(() -> new PlayerNotFoundException("The player with id " + id + " not found!"));
        playerId.setBalance(balance);
        playerRepository.save(playerId);
    }

    /**
     * Retrieves the balance of the player with the specified ID.
     *
     * @param id The ID of the player whose balance is to be retrieved.
     * @return The balance of the player.
     * @throws PlayerNotFoundException If the player with the provided ID is not found.
     */
    @Override
    public BigDecimal getPlayerBalance(Long id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new PlayerNotFoundException("The player with id " + id + " not found!"))
                .getBalance();
    }

    /**
     * Retrieves a player by their login.
     *
     * @param login The login of the player to be retrieved.
     * @return The player object associated with the login.
     * @throws PlayerNotFoundException If the player with the provided login is not found.
     */
    @Override
    public Player getByLogin(String login) {
        return playerRepository.findByLogin(login)
                .orElseThrow(() -> new PlayerNotFoundException("Player with login " + login + " not found!"));
    }

    /**
     * Retrieves a player by their ID.
     *
     * @param id The ID of the player to be retrieved.
     * @return The player object associated with the ID.
     * @throws PlayerNotFoundException If the player with the provided ID is not found.
     */
    @Override
    public Player getById(Long id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new PlayerNotFoundException("Player with id " + id + " not found!"));
    }
}