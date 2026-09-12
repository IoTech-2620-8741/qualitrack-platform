package com.iotech.qualitrack.platform.tracking.domain.model.aggregates;

import com.iotech.qualitrack.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import com.iotech.qualitrack.platform.tracking.domain.model.entities.EquipmentTelemetryStatus;
import com.iotech.qualitrack.platform.tracking.domain.model.entities.Measurement;
import com.iotech.qualitrack.platform.tracking.domain.model.entities.TelemetryHistoryPoint;
import com.iotech.qualitrack.platform.tracking.domain.model.valueobjects.TelemetryStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Aggregate root that represents the telemetry state of a monitored equipment.
 *
 * @remarks
 * This aggregate groups the current connectivity and health status of an
 * equipment together with its latest measurements and historical telemetry
 * points. It is the main consistency boundary for Tracking operations related
 * to a single equipment.
 */
public class EquipmentTelemetry extends AbstractDomainAggregateRoot<EquipmentTelemetry> {
    private Long id;
    private Long equipmentId;
    private EquipmentTelemetryStatus status;
    private final List<Measurement> measurements;
    private final List<TelemetryHistoryPoint> historyPoints;

    /**
     * Creates an empty equipment telemetry aggregate.
     */
    public EquipmentTelemetry() {
        this.measurements = new ArrayList<>();
        this.historyPoints = new ArrayList<>();
    }

    /**
     * Creates an equipment telemetry aggregate.
     *
     * @param id the aggregate identifier
     * @param equipmentId the monitored equipment identifier
     * @param status the current telemetry status
     * @param measurements the latest telemetry measurements
     * @param historyPoints the historical telemetry points
     */
    public EquipmentTelemetry(
            Long id,
            Long equipmentId,
            EquipmentTelemetryStatus status,
            List<Measurement> measurements,
            List<TelemetryHistoryPoint> historyPoints
    ) {
        this.id = id;
        this.equipmentId = equipmentId;
        this.status = status;
        this.measurements = new ArrayList<>(measurements == null ? List.of() : measurements);
        this.historyPoints = new ArrayList<>(historyPoints == null ? List.of() : historyPoints);
    }

    /**
     * Creates a new telemetry aggregate for an equipment.
     *
     * @param equipmentId the monitored equipment identifier
     * @return a new equipment telemetry aggregate
     */
    public static EquipmentTelemetry createForEquipment(Long equipmentId) {
        if (equipmentId == null || equipmentId <= 0) {
            throw new IllegalArgumentException("Equipment id must be a positive number");
        }

        var offlineStatus = EquipmentTelemetryStatus.update(
                equipmentId,
                false,
                TelemetryStatus.OFFLINE,
                java.time.LocalDateTime.now().toString()
        );

        return new EquipmentTelemetry(null, equipmentId, offlineStatus, List.of(), List.of());
    }

    /**
     * Records a new latest measurement in the aggregate.
     *
     * @param measurement the measurement to add
     */
    public void recordMeasurement(Measurement measurement) {
        if (measurement == null) {
            throw new IllegalArgumentException("Measurement is required");
        }
        if (!measurement.getEquipmentId().equals(this.equipmentId)) {
            throw new IllegalArgumentException("Measurement does not belong to this equipment telemetry aggregate");
        }

        this.measurements.add(measurement);
    }

    /**
     * Records a telemetry history point in the aggregate.
     *
     * @param historyPoint the history point to add
     */
    public void recordHistoryPoint(TelemetryHistoryPoint historyPoint) {
        if (historyPoint == null) {
            throw new IllegalArgumentException("Telemetry history point is required");
        }
        if (!historyPoint.getEquipmentId().equals(this.equipmentId)) {
            throw new IllegalArgumentException("History point does not belong to this equipment telemetry aggregate");
        }

        this.historyPoints.add(historyPoint);
    }

    /**
     * Updates the current telemetry status of the equipment.
     *
     * @param status the new telemetry status entity
     */
    public void updateStatus(EquipmentTelemetryStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Equipment telemetry status is required");
        }
        if (!status.getEquipmentId().equals(this.equipmentId)) {
            throw new IllegalArgumentException("Status does not belong to this equipment telemetry aggregate");
        }

        this.status = status;
    }

    /**
     * Determines whether the aggregate contains anomalous telemetry points.
     *
     * @return true when at least one historical point is anomalous
     */
    public boolean hasAnomalies() {
        return historyPoints.stream().anyMatch(TelemetryHistoryPoint::isAnomaly);
    }

    /**
     * Gets the amount of anomalous historical telemetry points.
     *
     * @return the anomaly count
     */
    public long anomalyCount() {
        return historyPoints.stream().filter(TelemetryHistoryPoint::isAnomaly).count();
    }

    /**
     * Determines whether the equipment is currently online.
     *
     * @return true when the current status is online
     */
    public boolean isOnline() {
        return status != null && Boolean.TRUE.equals(status.getIsOnline());
    }

    public Long getId() {
        return id;
    }

    public Long getEquipmentId() {
        return equipmentId;
    }

    public EquipmentTelemetryStatus getStatus() {
        return status;
    }

    public List<Measurement> getMeasurements() {
        return Collections.unmodifiableList(measurements);
    }

    public List<TelemetryHistoryPoint> getHistoryPoints() {
        return Collections.unmodifiableList(historyPoints);
    }
}