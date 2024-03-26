package com.ivan.walletservice.repository;

import com.ivan.walletservice.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findAllByPlayerId(Long playerId);
}