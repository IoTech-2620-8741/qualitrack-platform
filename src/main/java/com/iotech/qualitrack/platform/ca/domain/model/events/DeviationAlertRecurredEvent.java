package com.iotech.qualitrack.platform.ca.domain.model.events;

import com.iotech.qualitrack.platform.ca.domain.model.aggregates.DeviationAlert;
import com.iotech.qualitrack.platform.ca.domain.model.valueobjects.AlertSeverity;

import java.time.Instant;

/**
 * Domain event published when the condition of an open alert had returned to normal and deviated again without
 * raising its severity, so the laboratory is told again.
 *
 * @param alertId        the open alert of the incident
 * @param laboratoryId   the laboratory of the alert
 * @param environmentId  the environment where the deviation was detected
 * @param equipmentId    the device that detected it
 * @param parameterName  the monitored variable
 * @param recordedValue  the value of the new deviation
 * @param unit           the unit of the value
 * @param severity       the severity of the new deviation
 * @param detectedAt     when the new deviation was measured
 * @param deviationCount deviations of the incident, the new one included
 */
public record DeviationAlertRecurredEvent(
        Long alertId,
        Long laboratoryId,
        Long environmentId,
        Long equipmentId,
        String parameterName,
        Double recordedValue,
        String unit,
        AlertSeverity severity,
        Instant detectedAt,
        Integer deviationCount
) {
    public static DeviationAlertRecurredEvent from(DeviationAlert alert, AlertSeverity severity) {
        return new DeviationAlertRecurredEvent(alert.getId(), alert.getLaboratoryId(), alert.getEnvironmentId(),
                alert.getEquipmentId(), alert.getParameterName(), alert.getLastRecordedValue(), alert.getUnit(),
                severity, alert.getLastDetectedAt(), alert.getDeviationCount());
    }
}
