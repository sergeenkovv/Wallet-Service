package com.ivan.walletservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class PlayerDto {

    private String login;
    private BigDecimal balance;
}