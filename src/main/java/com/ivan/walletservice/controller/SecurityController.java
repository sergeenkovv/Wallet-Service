package com.ivan.walletservice.controller;

import com.ivan.walletservice.dto.JwtResponse;
import com.ivan.walletservice.dto.SecurityRequest;
import com.ivan.walletservice.mappers.PlayerMapper;
import com.ivan.walletservice.model.entity.Player;
import com.ivan.walletservice.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class SecurityController {

    private final SecurityService securityService;
    private final PlayerMapper playerMapper;

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody SecurityRequest dto) {
        Player player = securityService.registration(dto.login(), dto.password());
        return ResponseEntity.ok(playerMapper.toDto(player));
    }

    @PostMapping("/authorization")
    public ResponseEntity<?> authorization(@RequestBody SecurityRequest dto) {
        JwtResponse response = securityService.authorization(dto.login(), dto.password());
        return ResponseEntity.ok(response);
    }
}