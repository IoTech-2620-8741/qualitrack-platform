package com.iotech.qualitrack.platform.ra.interfaces.rest.resources;

import com.iotech.qualitrack.platform.ra.domain.model.valueobjects.KpiMetricStatus;

/**
 * Resource representation of a single KPI metric.
 *
 * @param id The unique numeric identifier of the KPI metric.
 * @param name The performance indicator name.
 * @param value The current measured value.
 * @param unit The unit of measurement.
 * @param targetValue The target value used to evaluate the metric.
 * @param status The evaluated KPI health status.
 * @param recordedAt The timestamp when this metric was recorded.
 */
public record KpiMetricResource(
        Long id,
        String name,
        Double value,
        String unit,
        Double targetValue,
        KpiMetricStatus status,
        String recordedAt
) {
}