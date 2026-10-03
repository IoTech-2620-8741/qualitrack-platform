package com.iotech.qualitrack.platform.tracking.application.queryservices;

import com.iotech.qualitrack.platform.tracking.domain.model.entities.EquipmentTelemetryStatus;
import com.iotech.qualitrack.platform.tracking.domain.model.entities.Measurement;
import com.iotech.qualitrack.platform.tracking.domain.model.entities.TelemetryHistoryPoint;
import com.iotech.qualitrack.platform.tracking.domain.model.queries.GetDeviceConnectionQuery;
import com.iotech.qualitrack.platform.tracking.domain.model.queries.GetEquipmentTelemetryStatusByEquipmentIdQuery;
import com.iotech.qualitrack.platform.tracking.domain.model.queries.GetLatestMeasurementsQuery;
import com.iotech.qualitrack.platform.tracking.domain.model.queries.GetTelemetryHistoryQuery;
import com.iotech.qualitrack.platform.tracking.domain.model.valueobjects.DeviceConnection;

import java.util.List;
import java.util.Optional;

/**
 * Query service contract for Tracking read operations.
 *
 * @remarks
 * This service coordinates read-only use cases for telemetry measurements,
 * current equipment status, and historical telemetry data.
 */
public interface TrackingQueryService {
    /**
     * Handles a query for retrieving latest telemetry measurements.
     *
     * @param query the latest measurements query
     * @return the latest telemetry measurements
     */
    List<Measurement> handle(GetLatestMeasurementsQuery query);

    /**
     * Handles a query for retrieving the latest telemetry status of an equipment.
     *
     * @param query the equipment telemetry status query
     * @return the latest equipment telemetry status when found
     */
    Optional<EquipmentTelemetryStatus> handle(GetEquipmentTelemetryStatusByEquipmentIdQuery query);

    /**
     * Handles a query for retrieving telemetry history points.
     *
     * @param query the telemetry history query
     * @return telemetry history points matching the query filters
     */
    List<TelemetryHistoryPoint> handle(GetTelemetryHistoryQuery query);

    /**
     * Resolves whether an IoT device located in the environment is communicating (US55).
     *
     * @param query laboratory, environment and device
     * @return the connection state, or empty when the device is not an IoT device located in the environment
     */
    Optional<DeviceConnection> handle(GetDeviceConnectionQuery query);
}