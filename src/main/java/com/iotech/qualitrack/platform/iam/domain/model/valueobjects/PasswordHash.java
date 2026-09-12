package com.iotech.qualitrack.platform.iam.domain.model.valueobjects;

import java.util.Objects;

/**
 * Value object representing a hashed password.
 *
 * @param value hashed password value
 */
public record PasswordHash(String value) {

    public PasswordHash {
        Objects.requireNonNull(value, "Password hash is required");

        if (value.isBlank()) {
            throw new IllegalArgumentException("Password hash cannot be blank");
        }
    }
}