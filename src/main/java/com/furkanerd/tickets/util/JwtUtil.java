package com.furkanerd.tickets.util;

import org.springframework.security.oauth2.jwt.Jwt;

import java.util.UUID;

public class JwtUtil {

    private JwtUtil() {}

    public static  UUID parseUuid(Jwt jwt) {
        return UUID.fromString(jwt.getSubject());
    }
}
