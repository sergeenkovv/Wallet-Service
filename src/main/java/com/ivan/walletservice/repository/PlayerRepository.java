package com.ivan.walletservice.repository;

import com.ivan.walletservice.model.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * This interface represents a repository for managing Player entities.
 * It extends JpaRepository to provide basic CRUD operations for Player entities.
 */
public interface PlayerRepository extends JpaRepository<Player, Long> {

    /**
     * Retrieves an optional Player entity by its login.
     *
     * @param login The login of the Player to retrieve.
     * @return An Optional containing the Player entity if found, or else an empty Optional.
     */
    Optional<Player> findByLogin(String login);
}