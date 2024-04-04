package com.ivan.walletservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivan.walletservice.dto.PlayerResponse;
import com.ivan.walletservice.dto.TransactionHistoryResponse;
import com.ivan.walletservice.dto.TransactionRequest;
import com.ivan.walletservice.dto.TransactionResponse;
import com.ivan.walletservice.mappers.PlayerMapper;
import com.ivan.walletservice.mappers.TransactionMapper;
import com.ivan.walletservice.model.entity.Player;
import com.ivan.walletservice.model.type.TransactionType;
import com.ivan.walletservice.security.JwtTokenProvider;
import com.ivan.walletservice.service.PlayerService;
import com.ivan.walletservice.service.TransactionService;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = PlayerController.class)
@WithMockUser(username = "ivan", password = "1234")
class PlayerControllerTest {

    @MockBean
    private PlayerService playerService;
    @MockBean
    private TransactionService transactionService;
    @MockBean
    private UserDetailsServiceImpl userDetailsServiceImpl;
    @MockBean
    private PlayerMapper playerMapper;
    @MockBean
    private TransactionMapper transactionMapper;
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
    private TransactionHistoryResponse transactionHistoryResponse;
    private TransactionRequest transactionRequest;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        Long id = 1L;
        String login = "ivan";
        String password = "password";

        player = Player.builder()
                .id(id)
                .login(login)
                .password(passwordEncoder.encode(password))
                .balance(new BigDecimal("0"))
                .build();

        playerResponse = new PlayerResponse(login, new BigDecimal("0"));

        List<TransactionResponse> transactionResponses = Arrays.asList(
                new TransactionResponse(1L, TransactionType.CREDIT.toString(), new BigDecimal("100")),
                new TransactionResponse(2L, TransactionType.DEBIT.toString(), new BigDecimal("23"))
        );

        transactionHistoryResponse = new TransactionHistoryResponse(player.getLogin(), transactionResponses);

        transactionRequest = new TransactionRequest(new BigDecimal("123"), player.getLogin());

        objectMapper = new ObjectMapper();
    }

    @Test
    void getBalance_Success() throws Exception {
        given(playerService.getByLogin(player.getLogin())).willReturn(player);
        given(playerMapper.toDto(player)).willReturn(playerResponse);

        ResultActions perform = mockMvc.perform(get("/api/players/balance")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .param("login", player.getLogin())
                .accept(MediaType.APPLICATION_JSON));

        perform.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(playerResponse)));
    }

    @Test
    void getHistory_Success() throws Exception {
        given(playerService.getByLogin(player.getLogin())).willReturn(player);
        given(transactionMapper.toDtoList(transactionService.getPlayerHistory(player.getId()))).willReturn(transactionHistoryResponse.transactions());

        ResultActions perform = mockMvc.perform(get("/api/players/history")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .param("login", player.getLogin())
                .accept(MediaType.APPLICATION_JSON));

        perform.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(transactionHistoryResponse)));
    }

    @Test
    void debit_Success() throws Exception {
        given(playerService.getByLogin(player.getLogin())).willReturn(player);
        doNothing().when(transactionService).debit(transactionRequest.amount(), player.getId());

        ResultActions perform = mockMvc.perform(post("/api/players/transactions/debit")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transactionRequest))
                .accept(MediaType.APPLICATION_JSON));

        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Transaction completed successfully!"));
    }

    @Test
    void credit_Success() throws Exception {
        given(playerService.getByLogin(player.getLogin())).willReturn(player);
        doNothing().when(transactionService).credit(transactionRequest.amount(), player.getId());

        ResultActions perform = mockMvc.perform(post("/api/players/transactions/credit")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transactionRequest))
                .accept(MediaType.APPLICATION_JSON));

        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Transaction completed successfully!"));
    }
}