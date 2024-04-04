package com.ivan.walletservice.service.impl;

import com.ivan.walletservice.dto.JwtResponse;
import com.ivan.walletservice.exception.AuthorizationException;
import com.ivan.walletservice.exception.RegistrationException;
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

/**
 * Implementation of the SecurityService interface providing registration and authorization functionalities.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SecurityServiceImpl implements SecurityService {

    private final PlayerRepository playerRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    /**
     * Registers a new player with the provided login and password.
     *
     * @param login    The login of the player to be registered.
     * @param password The password of the player to be registered.
     * @return The newly registered player.
     * @throws RegistrationException If a player with the provided login already exists.
     */
    @Transactional
    @Override
    public Player registration(String login, String password) {
        Optional<Player> player = playerRepository.findByLogin(login);

        if (player.isPresent()) {
            throw new RegistrationException("The player with this login already exists!");
        }

        Player newplayer = Player.builder()
                .login(login)
                .password(passwordEncoder.encode(password))
                .balance(new BigDecimal(0))
                .build();
        return playerRepository.save(newplayer);
    }

    /**
     * Authenticates a player with the provided login and password.
     *
     * @param login    The login of the player to be authenticated.
     * @param password The password of the player to be authenticated.
     * @return A JwtResponse containing the generated access token for successful authentication.
     * @throws AuthorizationException If there is no player with the provided login or authentication fails.
     */
    @Transactional
    @Override
    public JwtResponse authorization(String login, String password) {
        Optional<Player> optionalPlayer = playerRepository.findByLogin(login);

        if (optionalPlayer.isEmpty()) {
            throw new AuthorizationException("There is no player with this login in the database!");
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));

        String accessToken = jwtTokenProvider.createAccessToken(login);
        return new JwtResponse(login, accessToken);
    }
}