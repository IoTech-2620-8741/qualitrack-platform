package com.iotech.qualitrack.platform.laboratory.domain.model.commands;

import java.util.List;

/**
 * Command to request the creation of a new Laboratory.
 *
 * <p>Contains the flat primitive data matching the incoming DTO from the
 * presentation layer. No ID is provided here because it will be auto-generated
 * by the persistence mechanism.</p>
 *
 * @param name The official registered name. Cannot be null or blank.
 * @param ruc The tax identification number (RUC). Cannot be null or blank.
 * @param address The physical address. Cannot be null or blank.
 * @param phone The contact phone number. Cannot be null or blank.
 * @param applicableRegulations The list of regulatory frameworks. Cannot be null or empty.
 */
public record CreateLaboratoryCommand(
        String name,
        String ruc,
        String address,
        String phone,
        List<String> applicableRegulations
) {
    /**
     * Compact constructor for CreateLaboratoryCommand.
     */
    public CreateLaboratoryCommand {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name cannot be null or blank");
        }
        if (ruc == null || ruc.isBlank()) {
            throw new IllegalArgumentException("ruc cannot be null or blank");
        }
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("address cannot be null or blank");
        }
        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("phone cannot be null or blank");
        }
        if (applicableRegulations == null || applicableRegulations.isEmpty()) {
            throw new IllegalArgumentException("applicableRegulations cannot be null or empty");
        }
    }
}