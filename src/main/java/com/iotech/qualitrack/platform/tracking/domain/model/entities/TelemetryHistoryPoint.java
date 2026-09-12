package com.iotech.qualitrack.platform.tracking.domain.model.entities;

/**
 * Represents a historical telemetry point stored for charts, anomaly views,
 * and long-term equipment analysis.
 *
 * @remarks
 * History points preserve telemetry readings over time and include an anomaly
 * flag so the frontend can highlight deviations in tables and charts.
 */
public class TelemetryHistoryPoint {
    private Long id;
    private Long equipmentId;
    private String parameterName;
    private Double recordedValue;
    private String timestamp;
    private Boolean isAnomaly;
    private String createdAt;

    /**
     * Creates an empty telemetry history point entity.
     */
    public TelemetryHistoryPoint() {
    }

    /**
     * Creates a telemetry history point entity with all persisted properties.
     *
     * @param id the history point identifier
     * @param equipmentId the equipment identifier
     * @param parameterName the monitored parameter name
     * @param recordedValue the recorded telemetry value
     * @param timestamp the source timestamp
     * @param isAnomaly whether this point represents an anomaly
     * @param createdAt the persistence creation timestamp
     */
    public TelemetryHistoryPoint(
            Long id,
            Long equipmentId,
            String parameterName,
            Double recordedValue,
            String timestamp,
            Boolean isAnomaly,
            String createdAt
    ) {
        this.id = id;
        this.equipmentId = equipmentId;
        this.parameterName = parameterName;
        this.recordedValue = recordedValue;
        this.timestamp = timestamp;
        this.isAnomaly = isAnomaly;
        this.createdAt = createdAt;
    }

    /**
     * Creates a new telemetry history point before persistence.
     *
     * @param equipmentId the equipment identifier
     * @param parameterName the monitored parameter name
     * @param recordedValue the recorded telemetry value
     * @param timestamp the source timestamp
     * @param isAnomaly whether this point represents an anomaly
     * @return a new telemetry history point entity
     */
    public static TelemetryHistoryPoint record(
            Long equipmentId,
            String parameterName,
            Double recordedValue,
            String timestamp,
            Boolean isAnomaly
    ) {
        return new TelemetryHistoryPoint(
                null,
                equipmentId,
                parameterName,
                recordedValue,
                timestamp,
                isAnomaly,
                null
        );
    }

    /**
     * Determines whether the telemetry point should be treated as anomalous.
     *
     * @return true when the point was marked as an anomaly
     */
    public boolean isAnomaly() {
        return Boolean.TRUE.equals(isAnomaly);
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

    public Double getRecordedValue() {
        return recordedValue;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public Boolean getIsAnomaly() {
        return isAnomaly;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}