package com.iotech.qualitrack.platform.tracking.domain.repositories;

import com.iotech.qualitrack.platform.tracking.domain.model.entities.Measurement;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Repository port for telemetry measurements.
 *
 * @remarks
 * Defines the persistence contract required by the Tracking bounded context
 * to store and retrieve real-time telemetry measurements.
 */
public interface MeasurementRepository {
    /**
     * Finds a measurement by its identifier.
     *
     * @param id the measurement identifier
     * @return the measurement when found
     */
    Optional<Measurement> findById(Long id);

    /**
     * Finds the latest measurements across all monitored equipment.
     *
     * @return the latest measurement entities
     */
    List<Measurement> findLatest();

    /**
     * Finds the latest measurements for a specific equipment.
     *
     * @param equipmentId the equipment identifier
     * @return the latest measurement entities for the equipment
     */
    List<Measurement> findLatestByEquipmentId(Long equipmentId);

    /**
     * Saves a telemetry measurement.
     *
     * @param measurement the measurement to save
     * @return the saved measurement
     */
    Measurement save(Measurement measurement);

    /**
     * Verifies whether a measurement exists.
     *
     * @param id the measurement identifier
     * @return true when the measurement exists
     */
    boolean existsById(Long id);

    /**
     * Moment in which the platform received the latest measurement of the equipment, used to know whether
     * the device is communicating (US55).
     *
     * @param equipmentId equipment or IoT device identifier
     * @return the latest reception instant, or empty when nothing was received
     */
    Optional<Instant> findLastReceivedAt(Long equipmentId);
}
