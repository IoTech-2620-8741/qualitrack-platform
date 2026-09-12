package com.iotech.qualitrack.platform.tracking.domain.repositories;

import com.iotech.qualitrack.platform.tracking.domain.model.entities.TelemetryHistoryPoint;

import java.util.List;
import java.util.Optional;

/**
 * Repository port for telemetry history points.
 *
 * @remarks
 * Defines the persistence contract for time-series telemetry data used by
 * charts, anomaly history, trend analysis, and reporting.
 */
public interface TelemetryHistoryPointRepository {
    /**
     * Finds a telemetry history point by its identifier.
     *
     * @param id the history point identifier
     * @return the telemetry history point when found
     */
    Optional<TelemetryHistoryPoint> findById(Long id);

    /**
     * Finds telemetry history points using optional filters.
     *
     * @param equipmentId optional equipment identifier
     * @param from optional start timestamp
     * @param to optional end timestamp
     * @return telemetry history points matching the filters
     */
    List<TelemetryHistoryPoint> findByFilters(Long equipmentId, String from, String to);

    /**
     * Finds telemetry history points for an equipment.
     *
     * @param equipmentId the equipment identifier
     * @return telemetry history points for the equipment
     */
    List<TelemetryHistoryPoint> findAllByEquipmentId(Long equipmentId);

    /**
     * Finds anomalous telemetry history points for an equipment.
     *
     * @param equipmentId the equipment identifier
     * @return anomalous telemetry history points
     */
    List<TelemetryHistoryPoint> findAllAnomaliesByEquipmentId(Long equipmentId);

    /**
     * Saves a telemetry history point.
     *
     * @param historyPoint the telemetry history point to save
     * @return the saved telemetry history point
     */
    TelemetryHistoryPoint save(TelemetryHistoryPoint historyPoint);

    /**
     * Verifies whether a telemetry history point exists.
     *
     * @param id the history point identifier
     * @return true when the history point exists
     */
    boolean existsById(Long id);
}