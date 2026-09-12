package com.iotech.qualitrack.platform.iam.infrastructure.authorization.sfs.model;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

/**
 * Builder for Spring Security authentication tokens.
 */
public class UsernamePasswordAuthenticationTokenBuilder {

    private UsernamePasswordAuthenticationTokenBuilder() {
    }

    /**
     * Builds an authenticated Spring Security token from user details.
     *
     * @param userDetails authenticated user details
     * @return authentication token
     */
    public static Authentication build(UserDetailsImpl userDetails) {
        return new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
    }
}