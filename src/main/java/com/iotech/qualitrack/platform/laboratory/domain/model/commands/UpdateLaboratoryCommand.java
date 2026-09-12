package com.iotech.qualitrack.platform.laboratory.domain.model.commands;

import java.util.List;

/**
 * Command to request an update to an existing Laboratory's profile.
 *
 * @param laboratoryId The numeric ID of the laboratory to update. Cannot be null or less than 1.
 * @param name The updated official registered name. Cannot be null or blank.
 * @param address The updated physical address. Cannot be null or blank.
 * @param phone The updated contact phone number. Cannot be null or blank.
 * @param applicableRegulations The updated list of regulatory frameworks. Cannot be null.
 */
public record UpdateLaboratoryCommand(
        Long laboratoryId,
        String name,
        String address,
        String phone,
        List<String> applicableRegulations
) {
    /**
     * Compact constructor for UpdateLaboratoryCommand.
     */
    public UpdateLaboratoryCommand {
        if (laboratoryId == null || laboratoryId <= 0) {
            throw new IllegalArgumentException("laboratoryId cannot be null or less than 1");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name cannot be null or blank");
        }
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("address cannot be null or blank");
        }
        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("phone cannot be null or blank");
        }
        if (applicableRegulations == null) {
            throw new IllegalArgumentException("applicableRegulations cannot be null");
        }
    }
}