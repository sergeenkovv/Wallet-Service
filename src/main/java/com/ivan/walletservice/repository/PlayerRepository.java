package com.ivan.walletservice.repository;

import com.ivan.walletservice.model.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    Optional<Player> findByLogin(String login);
}
