package com.ivan.walletservice.controller;

import com.ivan.walletservice.dto.JwtResponse;
import com.ivan.walletservice.dto.SecurityDto;
import com.ivan.walletservice.mappers.PlayerMapper;
import com.ivan.walletservice.model.entity.Player;
import com.ivan.walletservice.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class SecurityController {

    private final SecurityService securityService;
    private final PlayerMapper playerMapper;

    @PostMapping("/sign-up")
    public ResponseEntity<?> registration(@RequestBody SecurityDto dto) {
        Player player = securityService.registration(dto);
        return ResponseEntity.ok(playerMapper.toDto(player));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> authorization(@RequestBody SecurityDto dto) {
        JwtResponse response = securityService.authorization(dto);
        return ResponseEntity.ok(response);
    }
}