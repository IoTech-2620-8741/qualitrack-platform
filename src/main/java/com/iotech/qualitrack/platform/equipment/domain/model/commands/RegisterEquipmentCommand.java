package com.iotech.qualitrack.platform.equipment.domain.model.commands;

/**
 * Command to request the registration of a new equipment in the system.
 *
 * @param labId The numeric identifier of the laboratory. Cannot be null or less than 1.
 * @param name The display name of the equipment. Cannot be null or blank.
 * @param type The category of the equipment. Cannot be null or blank.
 * @param model The model of the equipment. Cannot be null or blank.
 * @param serialNumber The serial number for traceability. Cannot be null or blank.
 */
public record RegisterEquipmentCommand(
        Long labId,
        String name,
        String type,
        String model,
        String serialNumber
) {
    /**
     * Compact constructor for RegisterEquipmentCommand.
     * Enforces Fail-Fast validation.
     */
    public RegisterEquipmentCommand {
        if (labId == null || labId <= 0) {
            throw new IllegalArgumentException("labId cannot be null or less than 1");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name cannot be null or blank");
        }
        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("type cannot be null or blank");
        }
        if (model == null || model.isBlank()) {
            throw new IllegalArgumentException("model cannot be null or blank");
        }
        if (serialNumber == null || serialNumber.isBlank()) {
            throw new IllegalArgumentException("serialNumber cannot be null or blank");
        }
    }
}