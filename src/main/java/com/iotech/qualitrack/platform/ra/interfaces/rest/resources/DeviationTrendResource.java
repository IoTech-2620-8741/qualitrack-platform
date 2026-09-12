package com.iotech.qualitrack.platform.ra.interfaces.rest.resources;

import com.iotech.qualitrack.platform.ra.domain.model.valueobjects.TrendDirection;

import java.util.List;

/**
 * Resource representation of a deviation trend analysis.
 *
 * @param id The unique numeric identifier of the deviation trend.
 * @param parameterName The monitored parameter name.
 * @param equipmentId The numeric identifier of the equipment.
 * @param trendDirection The calculated trend direction.
 * @param dataPoints Historical measurements used in the trend.
 */
public record DeviationTrendResource(
        Long id,
        String parameterName,
        Long equipmentId,
        TrendDirection trendDirection,
        List<TrendDataPointResource> dataPoints
) {
}