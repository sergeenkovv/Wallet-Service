package com.ivan.walletservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SecurityDto {

    private String login;
    private String password;
}