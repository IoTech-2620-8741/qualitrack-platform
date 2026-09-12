package com.iotech.qualitrack.platform.ca.interfaces.rest.resources;

import com.iotech.qualitrack.platform.ca.domain.model.valueobjects.AlertSeverity;
import com.iotech.qualitrack.platform.ca.domain.model.valueobjects.AlertStatus;

public record DeviationAlertResource(
        Long id,
        Long equipmentId,
        Long batchId,
        String parameterName,
        Double recordedValue,
        Double thresholdValue,
        String unit,
        String timestamp,
        AlertSeverity severity,
        AlertStatus status,
        Long acknowledgedBy,
        Long resolvedBy,
        String resolutionNotes
) {
}