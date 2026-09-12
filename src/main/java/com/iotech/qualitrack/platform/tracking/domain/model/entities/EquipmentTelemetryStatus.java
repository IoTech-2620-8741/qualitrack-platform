package com.iotech.qualitrack.platform.tracking.domain.model.entities;

import com.iotech.qualitrack.platform.tracking.domain.model.valueobjects.TelemetryStatus;

/**
 * Represents the current telemetry connectivity and health status of equipment.
 *
 * @remarks
 * This entity is optimized for the frontend dashboard, where users need to know
 * if equipment is online and whether its latest telemetry values are healthy,
 * risky, critical, or unavailable.
 */
public class EquipmentTelemetryStatus {
    private Long id;
    private Long equipmentId;
    private Boolean isOnline;
    private TelemetryStatus currentStatus;
    private String lastHeartbeat;
    private String createdAt;

    /**
     * Creates an empty equipment telemetry status entity.
     */
    public EquipmentTelemetryStatus() {
    }

    /**
     * Creates an equipment telemetry status entity with all persisted properties.
     *
     * @param id the status identifier
     * @param equipmentId the equipment identifier
     * @param isOnline whether the equipment is online
     * @param currentStatus the evaluated telemetry status
     * @param lastHeartbeat the latest heartbeat timestamp
     * @param createdAt the persistence creation timestamp
     */
    public EquipmentTelemetryStatus(
            Long id,
            Long equipmentId,
            Boolean isOnline,
            TelemetryStatus currentStatus,
            String lastHeartbeat,
            String createdAt
    ) {
        this.id = id;
        this.equipmentId = equipmentId;
        this.isOnline = isOnline;
        this.currentStatus = currentStatus;
        this.lastHeartbeat = lastHeartbeat;
        this.createdAt = createdAt;
    }

    /**
     * Creates a new telemetry status before persistence.
     *
     * @param equipmentId the equipment identifier
     * @param isOnline whether the equipment is online
     * @param currentStatus the evaluated telemetry status
     * @param lastHeartbeat the latest heartbeat timestamp
     * @return a new equipment telemetry status entity
     */
    public static EquipmentTelemetryStatus update(
            Long equipmentId,
            Boolean isOnline,
            TelemetryStatus currentStatus,
            String lastHeartbeat
    ) {
        return new EquipmentTelemetryStatus(
                null,
                equipmentId,
                isOnline,
                currentStatus,
                lastHeartbeat,
                null
        );
    }

    /**
     * Determines whether the equipment is currently considered operational.
     *
     * @return true when the equipment is online and operational
     */
    public boolean isOperational() {
        return Boolean.TRUE.equals(isOnline) && currentStatus == TelemetryStatus.OPERATIONAL;
    }

    /**
     * Determines whether the equipment requires immediate attention.
     *
     * @return true when the equipment status is critical or offline
     */
    public boolean requiresAttention() {
        return currentStatus == TelemetryStatus.CRITICAL || currentStatus == TelemetryStatus.OFFLINE;
    }

    public Long getId() {
        return id;
    }

    public Long getEquipmentId() {
        return equipmentId;
    }

    public Boolean getIsOnline() {
        return isOnline;
    }

    public TelemetryStatus getCurrentStatus() {
        return currentStatus;
    }

    public String getLastHeartbeat() {
        return lastHeartbeat;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}