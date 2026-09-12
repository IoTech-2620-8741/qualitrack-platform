package com.iotech.qualitrack.platform.batch.domain.model.commands;

/**
 * Command to request the release of a production batch.
 *
 * @param batchId The numeric identifier of the batch. Cannot be null or less than 1.
 * @param releaseDate The date the batch was released. Cannot be null or blank.
 * @param notes Final quality control or compliance remarks. Cannot be null or blank.
 */
public record ReleaseBatchCommand(
        Long batchId,
        String releaseDate,
        String notes
) {
    /**
     * Compact constructor for ReleaseBatchCommand.
     * Enforces Fail-Fast validation.
     */
    public ReleaseBatchCommand {
        if (batchId == null || batchId <= 0) {
            throw new IllegalArgumentException("batchId cannot be null or less than 1");
        }
        if (releaseDate == null || releaseDate.isBlank()) {
            throw new IllegalArgumentException("releaseDate cannot be null or blank");
        }
        if (notes == null || notes.isBlank()) {
            throw new IllegalArgumentException("notes cannot be null or blank");
        }
    }
}