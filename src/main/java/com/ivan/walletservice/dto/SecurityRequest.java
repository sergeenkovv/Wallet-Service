package com.ivan.walletservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SecurityRequest(
        @NotBlank
        @Size(min = 4, message = "Login must be at least 4 characters long!")
        String login,
        @NotBlank
        @Size(min = 4, message = "Password must be at least 4 characters long!")
        String password) {
}