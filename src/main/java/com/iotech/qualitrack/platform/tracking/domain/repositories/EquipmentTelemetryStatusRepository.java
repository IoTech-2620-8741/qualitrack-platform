package com.iotech.qualitrack.platform.tracking.domain.repositories;

import com.iotech.qualitrack.platform.tracking.domain.model.entities.EquipmentTelemetryStatus;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Repository port for equipment telemetry status records.
 *
 * @remarks
 * Defines the persistence contract for querying and updating the current
 * telemetry health and connectivity state of monitored equipment.
 */
public interface EquipmentTelemetryStatusRepository {
    /**
     * Finds a telemetry status by its identifier.
     *
     * @param id the status identifier
     * @return the telemetry status when found
     */
    Optional<EquipmentTelemetryStatus> findById(Long id);

    /**
     * Finds the latest telemetry status for an equipment.
     *
     * @param equipmentId the equipment identifier
     * @return the latest telemetry status when found
     */
    Optional<EquipmentTelemetryStatus> findLatestByEquipmentId(Long equipmentId);

    /**
     * Finds all latest status records.
     *
     * @return equipment telemetry status entities
     */
    List<EquipmentTelemetryStatus> findAllLatest();

    /**
     * Saves an equipment telemetry status.
     *
     * @param status the telemetry status to save
     * @return the saved telemetry status
     */
    EquipmentTelemetryStatus save(EquipmentTelemetryStatus status);

    /**
     * Verifies whether a telemetry status exists.
     *
     * @param id the status identifier
     * @return true when the status exists
     */
    boolean existsById(Long id);

    /**
     * Moment in which the platform received the latest telemetry status heartbeat of the equipment, used to know whether
     * the device is communicating (US55).
     *
     * @param equipmentId equipment or IoT device identifier
     * @return the latest reception instant, or empty when nothing was received
     */
    Optional<Instant> findLastReceivedAt(Long equipmentId);
}
