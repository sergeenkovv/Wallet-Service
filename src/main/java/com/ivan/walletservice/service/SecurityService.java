package com.ivan.walletservice.service;

import com.ivan.walletservice.dto.JwtResponse;
import com.ivan.walletservice.dto.SecurityDto;
import com.ivan.walletservice.model.entity.Player;

public interface SecurityService {

    Player registration(SecurityDto securityDto);

    JwtResponse authorization(SecurityDto securityDto);
}
