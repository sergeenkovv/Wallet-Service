package com.ivan.walletservice.service.impl;

import com.ivan.walletservice.model.entity.Player;
import com.ivan.walletservice.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {

    private PlayerRepository playerRepository;

    @Autowired
    public UserDetailsServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Player player = playerRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Failed to retrieve user: " + username + "!"));
        return new User(player.getLogin(), player.getPassword(), Collections.emptyList());
    }
}