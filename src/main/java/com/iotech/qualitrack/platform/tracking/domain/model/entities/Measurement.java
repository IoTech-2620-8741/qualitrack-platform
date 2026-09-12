package com.iotech.qualitrack.platform.tracking.domain.model.entities;

/**
 * Represents a single telemetry measurement emitted by monitored equipment.
 *
 * @remarks
 * Measurements are the real-time telemetry records consumed by dashboards,
 * status evaluators, anomaly detection processes, and historical analysis.
 */
public class Measurement {
    private Long id;
    private Long equipmentId;
    private String parameterName;
    private Double value;
    private String unit;
    private String timestamp;
    private String createdAt;

    /**
     * Creates an empty measurement entity.
     */
    public Measurement() {
    }

    /**
     * Creates a measurement entity with all persisted properties.
     *
     * @param id the measurement identifier
     * @param equipmentId the equipment identifier
     * @param parameterName the monitored parameter name
     * @param value the measured value
     * @param unit the measurement unit
     * @param timestamp the source timestamp
     * @param createdAt the persistence creation timestamp
     */
    public Measurement(
            Long id,
            Long equipmentId,
            String parameterName,
            Double value,
            String unit,
            String timestamp,
            String createdAt
    ) {
        this.id = id;
        this.equipmentId = equipmentId;
        this.parameterName = parameterName;
        this.value = value;
        this.unit = unit;
        this.timestamp = timestamp;
        this.createdAt = createdAt;
    }

    /**
     * Creates a new measurement before persistence.
     *
     * @param equipmentId the equipment identifier
     * @param parameterName the monitored parameter name
     * @param value the measured value
     * @param unit the measurement unit
     * @param timestamp the source timestamp
     * @return a new measurement entity
     */
    public static Measurement record(
            Long equipmentId,
            String parameterName,
            Double value,
            String unit,
            String timestamp
    ) {
        return new Measurement(null, equipmentId, parameterName, value, unit, timestamp, null);
    }

    public Long getId() {
        return id;
    }

    public Long getEquipmentId() {
        return equipmentId;
    }

    public String getParameterName() {
        return parameterName;
    }

    public Double getValue() {
        return value;
    }

    public String getUnit() {
        return unit;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}