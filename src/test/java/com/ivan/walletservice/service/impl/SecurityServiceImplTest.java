package com.ivan.walletservice.service.impl;

import com.ivan.walletservice.dto.JwtResponse;
import com.ivan.walletservice.exception.AuthorizationException;
import com.ivan.walletservice.exception.RegistrationException;
import com.ivan.walletservice.model.entity.Player;
import com.ivan.walletservice.repository.PlayerRepository;
import com.ivan.walletservice.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class SecurityServiceImplTest {

    @InjectMocks
    private SecurityServiceImpl securityServiceImpl;

    @Mock
    private PlayerRepository playerRepository;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtTokenProvider jwtTokenProvider;

    private Player player;

    @BeforeEach
    void setup() {
        player = Player.builder()
                .id(1L)
                .login("Ivan")
                .password(passwordEncoder.encode("1234"))
                .balance(new BigDecimal("100"))
                .build();
    }

    @Test
    void registration_Success() {
        Mockito.when(playerRepository.findByLogin(player.getLogin())).thenReturn(Optional.empty());
        Mockito.when(playerRepository.save(any(Player.class))).thenReturn(player);

        Player registerPlayer = securityServiceImpl.registration(player.getLogin(), player.getPassword());
        assertEquals(player.getLogin(), registerPlayer.getLogin());
        assertEquals(player.getPassword(), registerPlayer.getPassword());
    }

    @Test
    void registration_RegistrationException() {
        Mockito.when(playerRepository.findByLogin(player.getLogin())).thenReturn(Optional.of(player));

        assertThrows(RegistrationException.class,
                () -> securityServiceImpl.registration(player.getLogin(), player.getPassword()));
    }

    @Test
    void authorization_Success() {
        Mockito.when(playerRepository.findByLogin(player.getLogin())).thenReturn(Optional.of(player));
        Mockito.when(jwtTokenProvider.createAccessToken(player.getLogin())).thenReturn("testAccessToken");

        JwtResponse jwtResponse = securityServiceImpl.authorization(player.getLogin(), player.getPassword());
        assertEquals(player.getLogin(), jwtResponse.login());
        assertEquals("testAccessToken", jwtResponse.accessToken());
    }

    @Test
    void authorization_AuthorizationException() {
        Mockito.when(playerRepository.findByLogin(player.getLogin())).thenReturn(Optional.empty());

        assertThrows(AuthorizationException.class,
                () -> securityServiceImpl.authorization(player.getLogin(), player.getPassword()));
    }
}