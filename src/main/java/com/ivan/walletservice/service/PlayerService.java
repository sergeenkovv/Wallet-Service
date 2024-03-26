package com.ivan.walletservice.service;

import com.ivan.walletservice.model.entity.Player;

import java.math.BigDecimal;

public interface PlayerService {

    BigDecimal getPlayerBalance(Long id);

    Player getByLogin(String login);

    Player getById(Long id);

    void updateBalance(Long id, BigDecimal balance);
}