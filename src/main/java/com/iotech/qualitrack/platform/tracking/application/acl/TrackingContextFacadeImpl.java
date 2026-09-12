package com.iotech.qualitrack.platform.tracking.application.acl;

import com.iotech.qualitrack.platform.tracking.application.queryservices.TrackingQueryService;
import com.iotech.qualitrack.platform.tracking.domain.model.entities.EquipmentTelemetryStatus;
import com.iotech.qualitrack.platform.tracking.domain.model.entities.Measurement;
import com.iotech.qualitrack.platform.tracking.domain.model.entities.TelemetryHistoryPoint;
import com.iotech.qualitrack.platform.tracking.domain.model.queries.GetEquipmentTelemetryStatusByEquipmentIdQuery;
import com.iotech.qualitrack.platform.tracking.domain.model.queries.GetLatestMeasurementsQuery;
import com.iotech.qualitrack.platform.tracking.domain.model.queries.GetTelemetryHistoryQuery;
import com.iotech.qualitrack.platform.tracking.interfaces.acl.TrackingContextFacade;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Application ACL implementation for the Tracking bounded context facade.
 *
 * @remarks
 * This class adapts internal Tracking query services into a stable facade
 * contract that can be consumed by other bounded contexts.
 */
@Service
public class TrackingContextFacadeImpl implements TrackingContextFacade {

    private final TrackingQueryService trackingQueryService;

    /**
     * Creates a new TrackingContextFacadeImpl.
     *
     * @param trackingQueryService Tracking query service
     */
    public TrackingContextFacadeImpl(TrackingQueryService trackingQueryService) {
        this.trackingQueryService = trackingQueryService;
    }

    /**
     * Retrieves latest telemetry measurements for an equipment.
     *
     * @param equipmentId the equipment identifier
     * @return latest measurements for the equipment
     */
    @Override
    public List<Measurement> getLatestMeasurementsByEquipmentId(Long equipmentId) {
        try {
            return trackingQueryService.handle(new GetLatestMeasurementsQuery(equipmentId));
        } catch (IllegalArgumentException exception) {
            return List.of();
        }
    }

    /**
     * Retrieves the latest telemetry status for an equipment.
     *
     * @param equipmentId the equipment identifier
     * @return latest telemetry status when found
     */
    @Override
    public Optional<EquipmentTelemetryStatus> getEquipmentStatusByEquipmentId(Long equipmentId) {
        try {
            return trackingQueryService.handle(
                    new GetEquipmentTelemetryStatusByEquipmentIdQuery(equipmentId)
            );
        } catch (IllegalArgumentException exception) {
            return Optional.empty();
        }
    }

    /**
     * Retrieves telemetry history points for an equipment.
     *
     * @param equipmentId the equipment identifier
     * @return telemetry history points for the equipment
     */
    @Override
    public List<TelemetryHistoryPoint> getTelemetryHistoryByEquipmentId(Long equipmentId) {
        try {
            return trackingQueryService.handle(
                    new GetTelemetryHistoryQuery(equipmentId, null, null)
            );
        } catch (IllegalArgumentException exception) {
            return List.of();
        }
    }

    /**
     * Determines whether an equipment has anomalous telemetry history points.
     *
     * @param equipmentId the equipment identifier
     * @return true when at least one telemetry point is anomalous
     */
    @Override
    public boolean hasAnomaliesByEquipmentId(Long equipmentId) {
        return getTelemetryHistoryByEquipmentId(equipmentId).stream()
                .anyMatch(TelemetryHistoryPoint::isAnomaly);
    }
}