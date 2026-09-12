package com.iotech.qualitrack.platform.tracking.interfaces.acl;

import com.iotech.qualitrack.platform.tracking.domain.model.entities.EquipmentTelemetryStatus;
import com.iotech.qualitrack.platform.tracking.domain.model.entities.Measurement;
import com.iotech.qualitrack.platform.tracking.domain.model.entities.TelemetryHistoryPoint;

import java.util.List;
import java.util.Optional;

/**
 * Facade exposed by the Tracking bounded context for internal cross-context access.
 *
 * @remarks
 * Other bounded contexts should use this facade instead of depending directly
 * on Tracking repositories, application services, or REST controllers.
 */
public interface TrackingContextFacade {
    /**
     * Retrieves latest telemetry measurements for an equipment.
     *
     * @param equipmentId the equipment identifier
     * @return latest measurements for the equipment
     */
    List<Measurement> getLatestMeasurementsByEquipmentId(Long equipmentId);

    /**
     * Retrieves the latest telemetry status for an equipment.
     *
     * @param equipmentId the equipment identifier
     * @return latest telemetry status when found
     */
    Optional<EquipmentTelemetryStatus> getEquipmentStatusByEquipmentId(Long equipmentId);

    /**
     * Retrieves telemetry history points for an equipment.
     *
     * @param equipmentId the equipment identifier
     * @return telemetry history points for the equipment
     */
    List<TelemetryHistoryPoint> getTelemetryHistoryByEquipmentId(Long equipmentId);

    /**
     * Determines whether an equipment has anomalous telemetry history points.
     *
     * @param equipmentId the equipment identifier
     * @return true when the equipment has telemetry anomalies
     */
    boolean hasAnomaliesByEquipmentId(Long equipmentId);
}