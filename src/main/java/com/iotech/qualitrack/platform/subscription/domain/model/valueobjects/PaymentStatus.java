package com.iotech.qualitrack.platform.subscription.domain.model.valueobjects;

/**
 * Represents the status of a payment transaction.
 */
public enum PaymentStatus {
    PENDING,
    PAID,
    FAILED,
    CANCELLED,
    REFUNDED
}