package com.ivan.walletservice.service;

import com.ivan.walletservice.model.entity.Player;

import java.math.BigDecimal;

/**
 * Interface for managing player related operations.
 */
public interface PlayerService {

    /**
     * Retrieve the balance of a player by their ID.
     *
     * @param id The ID of the player
     * @return The balance of the player as a BigDecimal
     */
    BigDecimal getPlayerBalance(Long id);

    /**
     * Retrieve a player by their login.
     *
     * @param login The login of the player
     * @return The player object associated with the login
     */
    Player getByLogin(String login);

    /**
     * Retrieve a player by their ID.
     *
     * @param id The ID of the player
     * @return The player object associated with the ID
     */
    Player getById(Long id);

    /**
     * Update the balance of a player by their ID.
     *
     * @param id      The ID of the player
     * @param balance The new balance to update for the player
     */
    void updateBalance(Long id, BigDecimal balance);
}