package com.iotech.qualitrack.platform.subscription.domain.model.valueobjects;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

/**
 * Value object representing a monetary amount.
 *
 * @param amount The monetary amount.
 * @param currency The ISO currency code.
 */
public record Money(
        BigDecimal amount,
        String currency
) {

    /**
     * Creates a validated Money value object.
     */
    public Money {
        Objects.requireNonNull(amount, "Amount is required");

        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }

        if (currency == null || currency.isBlank()) {
            throw new IllegalArgumentException("Currency is required");
        }

        Currency.getInstance(currency);
    }

    /**
     * Creates a zero amount in the provided currency.
     *
     * @param currency The ISO currency code.
     * @return A zero Money value.
     */
    public static Money zero(String currency) {
        return new Money(BigDecimal.ZERO, currency);
    }
}