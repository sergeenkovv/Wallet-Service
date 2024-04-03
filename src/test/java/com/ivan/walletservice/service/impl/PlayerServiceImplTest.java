package com.ivan.walletservice.service.impl;

import com.ivan.walletservice.exception.PlayerNotFoundException;
import com.ivan.walletservice.model.entity.Player;
import com.ivan.walletservice.repository.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayerServiceImplTest {

    @InjectMocks
    private PlayerServiceImpl playerServiceImpl;

    @Mock
    private PlayerRepository playerRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    private Player player;

    @BeforeEach
    void setup() {
        String login = "ivan";
        String password = "password";
        player = Player.builder()
                .id(1L)
                .login(login)
                .password(passwordEncoder.encode(password))
                .balance(new BigDecimal("100"))
                .build();
    }

    @Test
    void updateBalance_Success() {
        when(playerRepository.findById(player.getId())).thenReturn(Optional.of(player));

        BigDecimal newBalance = new BigDecimal("1230");
        playerServiceImpl.updateBalance(player.getId(), newBalance);

        assertThat(player.getBalance()).isEqualTo(newBalance);
    }

    @Test
    void updateBalance_PlayerNotFoundException() {
        when(playerRepository.findById(player.getId())).thenReturn(Optional.empty());

        assertThrows(PlayerNotFoundException.class,
                () -> playerServiceImpl.updateBalance(player.getId(), new BigDecimal("101")));
    }

    @Test
    void getPlayerBalance_Success() {
        when(playerRepository.findById(player.getId())).thenReturn(Optional.of(player));

        BigDecimal returnedBalance = playerServiceImpl.getPlayerBalance(player.getId());

        assertThat(returnedBalance).isEqualTo(player.getBalance());
    }

    @Test
    void getPlayerBalance_PlayerNotFoundException() {
        when(playerRepository.findById(player.getId())).thenReturn(Optional.empty());

        assertThrows(PlayerNotFoundException.class,
                () -> playerServiceImpl.getPlayerBalance(player.getId()));
    }

    @Test
    void getByLogin_Success() {
        when(playerRepository.findByLogin(player.getLogin())).thenReturn(Optional.of(player));

        Player returnedPlayer = playerServiceImpl.getByLogin(player.getLogin());

        assertThat(returnedPlayer).isEqualTo(player);
    }

    @Test
    void getByLogin_PlayerNotFoundException() {
        when(playerRepository.findByLogin(player.getLogin())).thenReturn(Optional.empty());

        assertThrows(PlayerNotFoundException.class,
                () -> playerServiceImpl.getByLogin(player.getLogin()));
    }

    @Test
    void getById_Success() {
        when(playerRepository.findById(player.getId())).thenReturn(Optional.of(player));

        Player returnedPlayer = playerServiceImpl.getById(player.getId());

        assertThat(returnedPlayer).isEqualTo(player);
    }

    @Test
    void getById_PlayerNotFoundException() {
        when(playerRepository.findById(player.getId())).thenReturn(Optional.empty());

        assertThrows(PlayerNotFoundException.class,
                () -> playerServiceImpl.getById(player.getId()));
    }
}