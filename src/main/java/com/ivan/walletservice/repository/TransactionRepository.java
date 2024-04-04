package com.ivan.walletservice.repository;

import com.ivan.walletservice.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * This interface represents a repository for managing Transaction entities.
 * It extends JpaRepository to provide basic CRUD operations for Transaction entities.
 */
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    /**
     * Retrieve a list of transactions by the player's ID.
     *
     * @param playerId The ID of the player to retrieve transactions for.
     * @return A list of Transaction entities associated with the specified player ID.
     */
    List<Transaction> findAllByPlayerId(Long playerId);
}