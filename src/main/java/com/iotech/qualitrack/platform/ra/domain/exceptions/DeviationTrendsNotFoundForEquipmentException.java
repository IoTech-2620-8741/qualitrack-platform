package com.iotech.qualitrack.platform.ra.domain.exceptions;

/**
 * Exception thrown when no deviation trends are found for an equipment.
 */
public class DeviationTrendsNotFoundForEquipmentException extends RuntimeException {
    /**
     * Constructor for the exception.
     *
     * @param equipmentId The numeric ID of the equipment whose deviation trends were not found.
     */
    public DeviationTrendsNotFoundForEquipmentException(Long equipmentId) {
        super(String.format("Deviation trends for equipment with ID %d not found.", equipmentId));
    }
}