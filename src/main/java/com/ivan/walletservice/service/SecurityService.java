package com.ivan.walletservice.service;

import com.ivan.walletservice.dto.JwtResponse;
import com.ivan.walletservice.model.entity.Player;

/**
 * Interface for handling security-related operations such as user registration and authorization.
 */
public interface SecurityService {

    /**
     * Perform user registration with the provided login and password.
     *
     * @param login    The login of the user to register
     * @param password The password of the user to register
     * @return The Player object representing the registered user
     */
    Player registration(String login, String password);

    /**
     * Perform user authorization with the given login and password to generate a JWT response.
     *
     * @param login    The login of the user for authorization
     * @param password The password of the user for authorization
     * @return A JwtResponse containing the JWT token for the authorized user
     */
    JwtResponse authorization(String login, String password);
}