package com.iotech.qualitrack.platform.laboratory.domain.model.commands;

/**
 * Command to request the creation of a new Pharmaceutical Product.
 *
 * @param laboratoryId The numeric identifier of the laboratory. Cannot be null or less than 1.
 * @param code The internal catalog code. Cannot be null or blank.
 * @param name The commercial or scientific name. Cannot be null or blank.
 * @param description A human-readable description. Cannot be null or blank.
 * @param specifications The technical specifications. Cannot be null or blank.
 */
public record CreateProductCommand(
        Long laboratoryId,
        String code,
        String name,
        String description,
        String specifications
) {
    /**
     * Compact constructor for CreateProductCommand.
     * Enforces Fail-Fast validation.
     */
    public CreateProductCommand {
        if (laboratoryId == null || laboratoryId <= 0) {
            throw new IllegalArgumentException("laboratoryId cannot be null or less than 1");
        }
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("code cannot be null or blank");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name cannot be null or blank");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("description cannot be null or blank");
        }
        if (specifications == null || specifications.isBlank()) {
            throw new IllegalArgumentException("specifications cannot be null or blank");
        }
    }
}