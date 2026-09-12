package com.iotech.qualitrack.platform.batch.interfaces.rest.resources;

import com.iotech.qualitrack.platform.batch.domain.model.valueobjects.BatchStatus;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Resource used to request a production batch status update.
 *
 * @param status target batch lifecycle status
 * @param releaseDate batch release date, required when status is RELEASED
 * @param notes release notes, required when status is RELEASED
 * @param rejectionDate batch rejection date, required when status is REJECTED
 * @param reason rejection reason, required when status is REJECTED
 */
@Schema(
        name = "UpdateBatchStatusRequest",
        description = "Request payload for updating a production batch lifecycle status"
)
public record UpdateBatchStatusResource(
        @Schema(description = "Target batch status", example = "RELEASED")
        BatchStatus status,

        @Schema(description = "Batch release date in ISO 8601 format", example = "2026-05-20")
        String releaseDate,

        @Schema(description = "Final quality control notes", example = "All quality controls passed")
        String notes,

        @Schema(description = "Batch rejection date in ISO 8601 format", example = "2026-05-20")
        String rejectionDate,

        @Schema(description = "Reason for rejecting the batch", example = "Failed final quality control validation")
        String reason
) {
}