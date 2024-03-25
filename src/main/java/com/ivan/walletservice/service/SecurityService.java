package com.ivan.walletservice.service;

import com.ivan.walletservice.dto.JwtResponse;
import com.ivan.walletservice.model.entity.Player;

public interface SecurityService {

    Player registration(String login, String password);

    JwtResponse authorization(String login, String password);
}