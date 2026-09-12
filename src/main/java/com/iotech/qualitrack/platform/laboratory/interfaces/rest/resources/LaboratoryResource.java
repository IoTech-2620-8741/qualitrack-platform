package com.iotech.qualitrack.platform.laboratory.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * Laboratory resource.
 *
 * <p>Represents the data payload returned to the client when querying
 * laboratory information from the QualiTrack platform.</p>
 */
@Schema(
        name = "LaboratoryResponse",
        description = "Laboratory information response",
        example = "{\"id\": 1, \"name\": \"PharmaCorp\", \"ruc\": \"20123456789\", \"phone\": \"+51987654321\", \"applicableRegulations\": [\"ISO-9001\", \"BPA\"], \"address\": \"123 Tech Ave, Lima\", \"status\": \"ACTIVE\"}"
)
public record LaboratoryResource(

        @Schema(description = "Laboratory unique numeric identifier", example = "1")
        Long id,

        @Schema(description = "Laboratory official name", example = "PharmaCorp")
        String name,

        @Schema(description = "Tax identification number (RUC)", example = "20123456789")
        String ruc,

        @Schema(description = "Contact phone number", example = "+51987654321")
        String phone,

        @Schema(description = "List of applicable regulatory compliance codes", example = "[\"ISO-9001\", \"BPA\"]")
        List<String> applicableRegulations,

        @Schema(description = "Full physical address", example = "123 Tech Ave, Lima")
        String address,

        @Schema(description = "Current operational status", example = "ACTIVE")
        String status
) {
}