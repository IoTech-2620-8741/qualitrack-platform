package com.iotech.qualitrack.platform.tracking.domain.repositories;

import com.iotech.qualitrack.platform.tracking.domain.model.aggregates.EquipmentTelemetry;

import java.util.Optional;

/**
 * Repository port for equipment telemetry aggregates.
 *
 * @remarks
 * This port is useful when the application needs to work with the complete
 * telemetry state of a monitored equipment as a consistency boundary.
 */
public interface EquipmentTelemetryRepository {
    /**
     * Finds an equipment telemetry aggregate by its aggregate identifier.
     *
     * @param id the aggregate identifier
     * @return the aggregate when found
     */
    Optional<EquipmentTelemetry> findById(Long id);

    /**
     * Finds the telemetry aggregate associated with an equipment.
     *
     * @param equipmentId the equipment identifier
     * @return the aggregate when found
     */
    Optional<EquipmentTelemetry> findByEquipmentId(Long equipmentId);

    /**
     * Saves an equipment telemetry aggregate.
     *
     * @param equipmentTelemetry the aggregate to save
     * @return the saved aggregate
     */
    EquipmentTelemetry save(EquipmentTelemetry equipmentTelemetry);

    /**
     * Verifies whether an aggregate exists for the equipment.
     *
     * @param equipmentId the equipment identifier
     * @return true when an aggregate exists for the equipment
     */
    boolean existsByEquipmentId(Long equipmentId);
}