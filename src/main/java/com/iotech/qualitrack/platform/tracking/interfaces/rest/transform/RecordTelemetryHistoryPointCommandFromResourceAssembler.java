package com.iotech.qualitrack.platform.tracking.interfaces.rest.transform;

import com.iotech.qualitrack.platform.tracking.domain.model.commands.RecordTelemetryHistoryPointCommand;
import com.iotech.qualitrack.platform.tracking.interfaces.rest.resources.RecordTelemetryHistoryPointResource;

/**
 * Assembler that transforms telemetry history point REST resources into application commands.
 */
public final class RecordTelemetryHistoryPointCommandFromResourceAssembler {

    private RecordTelemetryHistoryPointCommandFromResourceAssembler() {
    }

    /**
     * Converts a record telemetry history point resource into a command.
     *
     * @param equipmentId the equipment numeric identifier from the route path
     * @param resource the record telemetry history point resource
     * @return the record telemetry history point command
     */
    public static RecordTelemetryHistoryPointCommand toCommandFromResource(
            Long equipmentId,
            RecordTelemetryHistoryPointResource resource
    ) {
        return new RecordTelemetryHistoryPointCommand(
                equipmentId,
                resource.parameterName(),
                resource.recordedValue(),
                resource.timestamp(),
                resource.isAnomaly()
        );
    }
}