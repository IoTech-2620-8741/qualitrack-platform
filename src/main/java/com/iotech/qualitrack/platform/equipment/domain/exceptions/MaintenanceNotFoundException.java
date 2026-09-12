package com.iotech.qualitrack.platform.equipment.domain.exceptions;

/**
 * Exception thrown when a maintenance record is not found.
 */
public class MaintenanceNotFoundException extends RuntimeException {
    /**
     * Constructor for the exception.
     * @param maintenanceId The numeric ID of the maintenance record that was not found.
     */
    public MaintenanceNotFoundException(Long maintenanceId) {
        super(String.format("Maintenance record with ID %d not found.", maintenanceId));
    }
}