package com.ivan.walletservice.service.impl;

import com.ivan.walletservice.dto.JwtResponse;
import com.ivan.walletservice.dto.SecurityDto;
import com.ivan.walletservice.exception.AuthorizeException;
import com.ivan.walletservice.exception.RegisterException;
import com.ivan.walletservice.model.entity.Player;
import com.ivan.walletservice.repository.PlayerRepository;
import com.ivan.walletservice.security.JwtTokenProvider;
import com.ivan.walletservice.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SecurityServiceImpl implements SecurityService {

    private final PlayerRepository playerRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public Player registration(SecurityDto dto) {
        Optional<Player> player = playerRepository.findByLogin(dto.getLogin());
        if (player.isPresent()) {
            throw new RegisterException("The player with this login already exists.");
        }

        Player newplayer = Player.builder()
                .login(dto.getLogin())
                .password(passwordEncoder.encode(dto.getPassword()))
                .balance(new BigDecimal(0))
                .build();
        return playerRepository.save(newplayer);
    }

    @Transactional
    @Override
    public JwtResponse authorization(SecurityDto dto) {
        Optional<Player> optionalPlayer = playerRepository.findByLogin(dto.getLogin());
        if (optionalPlayer.isEmpty()) {
            throw new AuthorizeException("There is no player with this login in the database.");
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getLogin(), dto.getPassword()));

        String accessToken = jwtTokenProvider.createAccessToken(dto.getLogin());
        return new JwtResponse(dto.getLogin(), accessToken);
    }
}