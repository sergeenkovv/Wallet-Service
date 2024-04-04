package com.ivan.walletservice.dto;

/**
 * JwtResponse record representing a response object containing the login and access token.
 */
public record JwtResponse(
        String login,
        String accessToken) {
}