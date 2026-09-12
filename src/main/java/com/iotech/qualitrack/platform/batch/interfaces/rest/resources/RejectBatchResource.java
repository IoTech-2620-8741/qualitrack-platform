package com.iotech.qualitrack.platform.batch.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Reject batch resource.
 */
@Schema(
        name = "RejectBatchRequest",
        description = "Request payload for rejecting a production batch",
        example = "{\"rejectionDate\": \"2026-05-20\", \"reason\": \"Failed final quality control validation\"}"
)
public record RejectBatchResource(

        @Schema(description = "Batch rejection date in ISO 8601 format", example = "2026-05-20")
        String rejectionDate,

        @Schema(description = "Reason for rejecting the batch", example = "Failed final quality control validation")
        String reason
) {
    public RejectBatchResource {
        if (rejectionDate == null || rejectionDate.isBlank()) {
            throw new IllegalArgumentException("Rejection date is required");
        }
        if (reason == null || reason.isBlank()) {
            throw new IllegalArgumentException("Reason is required");
        }
    }
}