package com.iotech.qualitrack.platform.equipment.domain.model.commands;

/**
 * Command to request the registration of a maintenance activity for an equipment.
 *
 * @param equipmentId The numeric identifier of the equipment. Cannot be null or less than 1.
 * @param maintenanceDate The date the maintenance was performed. Cannot be null or blank.
 * @param technicianName The name of the responsible technician. Cannot be null or blank.
 * @param description Summary of the activity. Cannot be null or blank.
 * @param type The type of maintenance performed. Cannot be null or blank.
 */
public record RegisterMaintenanceCommand(
        Long equipmentId,
        String maintenanceDate,
        String technicianName,
        String description,
        String type
) {
    /**
     * Compact constructor for RegisterMaintenanceCommand.
     * Enforces Fail-Fast validation.
     */
    public RegisterMaintenanceCommand {
        if (equipmentId == null || equipmentId <= 0) {
            throw new IllegalArgumentException("equipmentId cannot be null or less than 1");
        }
        if (maintenanceDate == null || maintenanceDate.isBlank()) {
            throw new IllegalArgumentException("maintenanceDate cannot be null or blank");
        }
        if (technicianName == null || technicianName.isBlank()) {
            throw new IllegalArgumentException("technicianName cannot be null or blank");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("description cannot be null or blank");
        }
        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("type cannot be null or blank");
        }
    }
}