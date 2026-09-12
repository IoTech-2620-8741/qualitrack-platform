package com.iotech.qualitrack.platform.equipment.domain.exceptions;

/**
 * Exception thrown when an equipment is not found.
 */
public class EquipmentNotFoundException extends RuntimeException {
    /**
     * Constructor for the exception.
     * @param equipmentId The numeric ID of the equipment that was not found.
     */
    public EquipmentNotFoundException(Long equipmentId) {
        super(String.format("Equipment with ID %d not found.", equipmentId));
    }
}