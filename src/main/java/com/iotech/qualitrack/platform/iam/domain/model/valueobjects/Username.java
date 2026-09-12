package com.iotech.qualitrack.platform.iam.domain.model.valueobjects;

import java.util.Objects;

/**
 * Value object representing a username.
 *
 * @param value username value
 */
public record Username(String value) {

    public Username {
        Objects.requireNonNull(value, "Username is required");

        if (value.isBlank()) {
            throw new IllegalArgumentException("Username cannot be blank");
        }

        if (value.length() < 3) {
            throw new IllegalArgumentException("Username must have at least 3 characters");
        }

        if (value.length() > 80) {
            throw new IllegalArgumentException("Username cannot exceed 80 characters");
        }
    }
}