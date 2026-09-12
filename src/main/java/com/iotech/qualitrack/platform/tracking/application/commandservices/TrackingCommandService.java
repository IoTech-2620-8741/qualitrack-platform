package com.iotech.qualitrack.platform.tracking.application.commandservices;

import com.iotech.qualitrack.platform.shared.application.result.ApplicationError;
import com.iotech.qualitrack.platform.shared.application.result.Result;
import com.iotech.qualitrack.platform.tracking.domain.model.commands.RecordMeasurementCommand;
import com.iotech.qualitrack.platform.tracking.domain.model.commands.RecordTelemetryHistoryPointCommand;
import com.iotech.qualitrack.platform.tracking.domain.model.commands.UpdateEquipmentTelemetryStatusCommand;

/**
 * Command service contract for Tracking write operations.
 *
 * @remarks
 * This service coordinates use cases that mutate telemetry state, including
 * measurement ingestion, telemetry history persistence, and equipment status
 * updates.
 */
public interface TrackingCommandService {
    /**
     * Handles the recording of a new telemetry measurement.
     *
     * @param command the record measurement command
     * @return the identifier of the recorded measurement or an application error
     */
    Result<Long, ApplicationError> handle(RecordMeasurementCommand command);

    /**
     * Handles the recording of a new telemetry history point.
     *
     * @param command the record telemetry history point command
     * @return the identifier of the recorded telemetry history point or an application error
     */
    Result<Long, ApplicationError> handle(RecordTelemetryHistoryPointCommand command);

    /**
     * Handles the update of the current telemetry status for equipment.
     *
     * @param command the update equipment telemetry status command
     * @return the identifier of the saved telemetry status or an application error
     */
    Result<Long, ApplicationError> handle(UpdateEquipmentTelemetryStatusCommand command);
}