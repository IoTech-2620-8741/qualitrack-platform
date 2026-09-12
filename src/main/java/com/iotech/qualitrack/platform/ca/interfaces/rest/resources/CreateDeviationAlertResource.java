package com.iotech.qualitrack.platform.ca.interfaces.rest.resources;

import com.iotech.qualitrack.platform.ca.domain.model.valueobjects.AlertSeverity;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "CreateDeviationAlertRequest",
        description = "Request payload for creating a deviation alert",
        example = "{\"batchId\": 2, \"parameterName\": \"TEMPERATURE\", \"recordedValue\": 9.5, \"thresholdValue\": 8.0, \"unit\": \"Celsius\", \"timestamp\": \"2026-05-12T11:00:00Z\", \"severity\": \"CRITICAL\"}"
)
public record CreateDeviationAlertResource(
        @Schema(description = "Batch numeric identifier, if applicable", example = "2")
        Long batchId,

        @Schema(description = "Monitored parameter name", example = "TEMPERATURE")
        String parameterName,

        @Schema(description = "Measured value that triggered the alert", example = "9.5")
        Double recordedValue,

        @Schema(description = "Configured threshold value", example = "8.0")
        Double thresholdValue,

        @Schema(description = "Measurement unit", example = "Celsius")
        String unit,

        @Schema(description = "Timestamp when the deviation occurred", example = "2026-05-12T11:00:00Z")
        String timestamp,

        @Schema(description = "Alert severity", example = "CRITICAL")
        AlertSeverity severity
) {
    public CreateDeviationAlertResource {
        if (batchId != null && batchId <= 0) {
            throw new IllegalArgumentException("batchId cannot be less than 1");
        }
        if (parameterName == null || parameterName.isBlank()) {
            throw new IllegalArgumentException("parameterName cannot be null or blank");
        }
        if (recordedValue == null) {
            throw new IllegalArgumentException("recordedValue cannot be null");
        }
        if (thresholdValue == null) {
            throw new IllegalArgumentException("thresholdValue cannot be null");
        }
        if (unit == null || unit.isBlank()) {
            throw new IllegalArgumentException("unit cannot be null or blank");
        }
        if (timestamp == null || timestamp.isBlank()) {
            throw new IllegalArgumentException("timestamp cannot be null or blank");
        }
        if (severity == null) {
            throw new IllegalArgumentException("severity cannot be null");
        }
    }
}