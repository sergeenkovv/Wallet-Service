package com.ivan.walletservice.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "spring.security.jwt")
public class JwtProperties {

    private String secret;
    private Long access;
}