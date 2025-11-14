package com.devmam.slmapiv2.services;

import com.nimbusds.jwt.JWTClaimsSet;

import java.util.Set;
import java.util.UUID;

public interface JwtService {
    String buildScope(Set<String> roles);

    String generateToken(String id, String email, Set<String> roles, String userAgent);

    Integer getUserId(String token);

    String getKeycloakUserIdFromToken(String token);

    String getCurrentUserId();

    JWTClaimsSet getClaimsFromToken(String token);

    String getTokenFromAuthHeader(String authHeader);
}
