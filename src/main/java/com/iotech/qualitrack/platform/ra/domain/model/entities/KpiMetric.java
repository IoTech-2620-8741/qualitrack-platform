package com.iotech.qualitrack.platform.ra.domain.model.entities;

import com.iotech.qualitrack.platform.ra.domain.model.valueobjects.KpiMetricStatus;
import lombok.Getter;

import java.util.Objects;

/**
 * Domain entity representing a single KPI metric within a dashboard snapshot.
 *
 * <p>A KPI metric captures the current value of a laboratory performance
 * indicator, its target value, unit of measurement, evaluated status, and
 * recording timestamp.</p>
 */
@Getter
public class KpiMetric {

    /**
     * The unique numeric identifier of the KPI metric.
     */
    private Long id;

    /**
     * The display name of the KPI metric.
     */
    private String name;

    /**
     * The current measured value.
     */
    private Double value;

    /**
     * The measurement unit.
     */
    private String unit;

    /**
     * The target value used to evaluate the metric.
     */
    private Double targetValue;

    /**
     * The evaluated health status of the KPI metric.
     */
    private KpiMetricStatus status;

    /**
     * The timestamp when the metric was recorded.
     */
    private String recordedAt;

    /**
     * Default constructor.
     * Required by the persistence and mapping layers to reconstruct the entity.
     */
    public KpiMetric() {
        // Required for reconstruction by JPA or Assemblers
    }

    /**
     * Reconstructs a KpiMetric entity from persistence data.
     *
     * @param id The numeric metric identifier.
     * @param name The metric name.
     * @param value The current measured value.
     * @param unit The measurement unit.
     * @param targetValue The target value.
     * @param status The evaluated metric status.
     * @param recordedAt The recording timestamp.
     */
    public KpiMetric(
            Long id,
            String name,
            Double value,
            String unit,
            Double targetValue,
            KpiMetricStatus status,
            String recordedAt
    ) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.unit = unit;
        this.targetValue = targetValue;
        this.status = status;
        this.recordedAt = recordedAt;
    }

    /**
     * Creates a new KPI metric and evaluates its status.
     *
     * @param name The metric name.
     * @param value The current measured value.
     * @param unit The measurement unit.
     * @param targetValue The target value.
     * @param recordedAt The recording timestamp.
     */
    public KpiMetric(
            String name,
            Double value,
            String unit,
            Double targetValue,
            String recordedAt
    ) {
        this.name = Objects.requireNonNull(name, "name cannot be null");
        this.value = Objects.requireNonNull(value, "value cannot be null");
        this.unit = Objects.requireNonNull(unit, "unit cannot be null");
        this.targetValue = Objects.requireNonNull(targetValue, "targetValue cannot be null");
        this.recordedAt = Objects.requireNonNull(recordedAt, "recordedAt cannot be null");
        this.status = evaluateStatus(value, targetValue);
    }

    /**
     * Evaluates the KPI metric status against its target value.
     *
     * @param value The measured value.
     * @param targetValue The target value.
     * @return The evaluated KPI metric status.
     */
    private KpiMetricStatus evaluateStatus(Double value, Double targetValue) {
        if (value == null || targetValue == null || targetValue == 0) {
            return KpiMetricStatus.UNKNOWN;
        }

        double ratio = value / targetValue;

        if (ratio >= 0.9) return KpiMetricStatus.ON_TRACK;
        if (ratio >= 0.7) return KpiMetricStatus.AT_RISK;
        return KpiMetricStatus.CRITICAL;
    }
}