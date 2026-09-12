package com.iotech.qualitrack.platform.ra.interfaces.events;

import com.iotech.qualitrack.platform.ra.domain.model.events.DeviationTrendCalculatedEvent;
import com.iotech.qualitrack.platform.ra.domain.model.valueobjects.TrendDirection;

/**
 * Integration event published when a deviation trend has been calculated.
 *
 * <p>This event can be consumed by other bounded contexts when trend analysis
 * becomes relevant for notifications, compliance alerts, or operational decisions.</p>
 *
 * @param trendId The deviation trend identifier.
 * @param equipmentId The related equipment identifier.
 * @param parameterName The analyzed parameter name.
 * @param trendDirection The calculated trend direction.
 * @param calculatedAt The timestamp when the trend was calculated.
 */
public record DeviationTrendCalculatedIntegrationEvent(
        Long trendId,
        Long equipmentId,
        String parameterName,
        TrendDirection trendDirection,
        String calculatedAt
) {

    /**
     * Creates an integration event from the internal RA domain event.
     *
     * @param event The internal domain event.
     * @return The integration event.
     */
    public static DeviationTrendCalculatedIntegrationEvent from(DeviationTrendCalculatedEvent event) {
        return new DeviationTrendCalculatedIntegrationEvent(
                event.trendId(),
                event.equipmentId(),
                event.parameterName(),
                event.trendDirection(),
                event.calculatedAt()
        );
    }
}