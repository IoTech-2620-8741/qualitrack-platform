package com.iotech.qualitrack.platform.iam.application.internal.outboundservices.hashing;

/**
 * Outbound service contract for password hashing operations.
 */
public interface HashingService {

    /**
     * Encodes a raw password.
     *
     * @param rawPassword raw password
     * @return encoded password hash
     */
    String encode(String rawPassword);

    /**
     * Verifies whether a raw password matches an encoded password hash.
     *
     * @param rawPassword raw password
     * @param encodedPassword encoded password hash
     * @return true when password matches
     */
    boolean matches(String rawPassword, String encodedPassword);
}