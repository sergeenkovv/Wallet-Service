package com.ivan.walletservice.controller;

import com.ivan.walletservice.dto.JwtResponse;
import com.ivan.walletservice.dto.SecurityRequest;
import com.ivan.walletservice.mappers.PlayerMapper;
import com.ivan.walletservice.model.entity.Player;
import com.ivan.walletservice.service.SecurityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SecurityController class that handles security-related API endpoints for authentication and authorization.
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class SecurityController {

    private final SecurityService securityService;
    private final PlayerMapper playerMapper;

    /**
     * Handles the registration process for a new player.
     *
     * @param dto The SecurityRequest object containing registration details.
     * @return ResponseEntity<?> containing the registered player information.
     */
    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody @Valid SecurityRequest dto) {
        Player player = securityService.registration(dto.login(), dto.password());
        return ResponseEntity.ok(playerMapper.toDto(player));
    }

    /**
     * Handles the authorization process for an existing player.
     *
     * @param dto The SecurityRequest object containing authorization details.
     * @return ResponseEntity<?> containing the JWT response for successful authorization.
     */
    @PostMapping("/authorization")
    public ResponseEntity<?> authorization(@RequestBody @Valid SecurityRequest dto) {
        JwtResponse response = securityService.authorization(dto.login(), dto.password());
        return ResponseEntity.ok(response);
    }
}