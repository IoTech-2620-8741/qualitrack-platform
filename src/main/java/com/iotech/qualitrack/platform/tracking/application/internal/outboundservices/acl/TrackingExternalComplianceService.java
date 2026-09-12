package com.iotech.qualitrack.platform.tracking.application.internal.outboundservices.acl;

import com.iotech.qualitrack.platform.ca.application.commandservices.CaCommandService;
import com.iotech.qualitrack.platform.ca.domain.model.commands.CreateDeviationAlertCommand;
import com.iotech.qualitrack.platform.ca.domain.model.valueobjects.AlertSeverity;
import org.springframework.stereotype.Service;

/**
 * Outbound ACL service used by Tracking to interact with Compliance Alerts.
 *
 * @remarks
 * Tracking uses this service to report telemetry anomalies as deviation alerts
 * without exposing CA implementation details to the Tracking domain.
 */
@Service
public class TrackingExternalComplianceService {

    private final CaCommandService caCommandService;

    /**
     * Creates a new TrackingExternalComplianceService.
     *
     * @param caCommandService command service from the CA bounded context
     */
    public TrackingExternalComplianceService(CaCommandService caCommandService) {
        this.caCommandService = caCommandService;
    }

    /**
     * Creates a deviation alert for an anomalous telemetry reading.
     *
     * @param equipmentId the equipment identifier
     * @param parameterName the telemetry parameter name
     * @param recordedValue the recorded telemetry value
     * @param thresholdValue the threshold value exceeded
     * @param unit the measurement unit
     * @param timestamp the anomaly timestamp
     * @param severity the alert severity
     * @return true when the alert creation request succeeds
     */
    public boolean createTelemetryDeviationAlert(
            Long equipmentId,
            String parameterName,
            Double recordedValue,
            Double thresholdValue,
            String unit,
            String timestamp,
            AlertSeverity severity
    ) {
        var result = caCommandService.handle(new CreateDeviationAlertCommand(
                equipmentId,
                null,
                parameterName,
                recordedValue,
                thresholdValue,
                unit,
                timestamp,
                severity
        ));

        return result.isSuccess();
    }
}