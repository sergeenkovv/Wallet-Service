package com.ivan.walletservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivan.walletservice.dto.JwtResponse;
import com.ivan.walletservice.dto.PlayerResponse;
import com.ivan.walletservice.dto.SecurityRequest;
import com.ivan.walletservice.mappers.PlayerMapper;
import com.ivan.walletservice.model.entity.Player;
import com.ivan.walletservice.security.JwtTokenProvider;
import com.ivan.walletservice.service.SecurityService;
import com.ivan.walletservice.service.impl.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = SecurityController.class)
class SecurityControllerTest {

    @MockBean
    private SecurityService securityService;
    @MockBean
    private UserDetailsServiceImpl userDetailsServiceImpl;
    @MockBean
    private PlayerMapper playerMapper;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;

    @Autowired
    private MockMvc mockMvc;

    private Player player;
    private PlayerResponse playerResponse;
    private JwtResponse jwtResponse;
    private SecurityRequest securityRequest;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        String login = "ivan";
        String password = "password";

        player = Player.builder()
                .id(1L)
                .login(login)
                .password(passwordEncoder.encode(password))
                .balance(new BigDecimal("0"))
                .build();

        playerResponse = new PlayerResponse(login, new BigDecimal("0"));

        jwtResponse = new JwtResponse(login, "accessToken");

        securityRequest = new SecurityRequest(login, password);

        objectMapper = new ObjectMapper();
    }

    @Test
    void registration_Success() throws Exception {
        given(securityService.registration(anyString(), anyString())).willReturn(player);
        given(playerMapper.toDto(player)).willReturn(playerResponse);

        ResultActions perform = mockMvc.perform(post("/api/auth/registration")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(securityRequest))
                .accept(MediaType.APPLICATION_JSON));

        perform.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(playerResponse)));
    }

    @Test
    void authorization_Success() throws Exception {
        given(securityService.authorization(anyString(), anyString())).willReturn(jwtResponse);

        ResultActions perform = mockMvc.perform(post("/api/auth/authorization")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(securityRequest))
                .accept(MediaType.APPLICATION_JSON));

        perform.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(jwtResponse)));
    }
}