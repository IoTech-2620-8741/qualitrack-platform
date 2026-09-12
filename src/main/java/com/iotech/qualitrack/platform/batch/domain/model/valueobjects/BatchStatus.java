package com.iotech.qualitrack.platform.batch.domain.model.valueobjects;

/**
 * Value Object representing the lifecycle status of a production batch.
 */
public enum BatchStatus {
    PENDING,
    IN_PROGRESS,
    RELEASED,
    REJECTED
}