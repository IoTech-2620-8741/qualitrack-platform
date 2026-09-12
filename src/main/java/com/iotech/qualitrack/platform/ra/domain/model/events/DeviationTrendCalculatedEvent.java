package com.iotech.qualitrack.platform.ra.domain.model.events;

import com.iotech.qualitrack.platform.ra.domain.model.valueobjects.TrendDirection;

/**
 * Event raised when a deviation trend analysis is calculated.
 *
 * @param trendId The numeric identifier of the deviation trend.
 * @param equipmentId The numeric identifier of the monitored equipment.
 * @param parameterName The monitored parameter name.
 * @param trendDirection The calculated trend direction.
 * @param calculatedAt The timestamp when the trend was calculated.
 */
public record DeviationTrendCalculatedEvent(
        Long trendId,
        Long equipmentId,
        String parameterName,
        TrendDirection trendDirection,
        String calculatedAt
) {
    /**
     * Compact constructor for DeviationTrendCalculatedEvent.
     * Enforces Fail-Fast validation.
     */
    public DeviationTrendCalculatedEvent {
        if (trendId == null || trendId <= 0) {
            throw new IllegalArgumentException("trendId cannot be null or less than 1");
        }
        if (equipmentId == null || equipmentId <= 0) {
            throw new IllegalArgumentException("equipmentId cannot be null or less than 1");
        }
        if (parameterName == null || parameterName.isBlank()) {
            throw new IllegalArgumentException("parameterName cannot be null or blank");
        }
        if (trendDirection == null) {
            throw new IllegalArgumentException("trendDirection cannot be null");
        }
        if (calculatedAt == null || calculatedAt.isBlank()) {
            throw new IllegalArgumentException("calculatedAt cannot be null or blank");
        }
    }
}