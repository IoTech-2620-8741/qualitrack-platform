package com.iotech.qualitrack.platform.iam.infrastructure.tokens.jwt;

import org.springframework.stereotype.Service;

/**
 * Service responsible for extracting JWT bearer tokens from Authorization headers.
 */
@Service
public class BearerTokenService {

    private static final String BEARER_PREFIX = "Bearer ";

    /**
     * Checks whether the provided authorization header contains a bearer token.
     *
     * @param authorizationHeader HTTP Authorization header
     * @return true when the header starts with Bearer
     */
    public boolean hasBearerToken(String authorizationHeader) {
        return authorizationHeader != null
                && authorizationHeader.startsWith(BEARER_PREFIX)
                && authorizationHeader.length() > BEARER_PREFIX.length();
    }

    /**
     * Extracts the token value from a Bearer authorization header.
     *
     * @param authorizationHeader HTTP Authorization header
     * @return token without Bearer prefix
     */
    public String extractToken(String authorizationHeader) {
        if (!hasBearerToken(authorizationHeader)) {
            throw new IllegalArgumentException("Authorization header does not contain a Bearer token");
        }

        return authorizationHeader.substring(BEARER_PREFIX.length());
    }
}