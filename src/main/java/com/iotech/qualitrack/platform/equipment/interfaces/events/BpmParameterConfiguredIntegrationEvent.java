package com.iotech.qualitrack.platform.equipment.interfaces.events;

import com.iotech.qualitrack.platform.equipment.domain.model.events.BpmParameterConfiguredEvent;

/**
 * Integration event published when BPM parameters are configured for equipment.
 *
 * <p>This is part of the published language of the Equipment bounded context.</p>
 *
 * @param configId The BPM parameter configuration identifier.
 * @param equipmentId The related equipment identifier.
 * @param parameterName The configured parameter name.
 * @param minValue The minimum accepted value.
 * @param maxValue The maximum accepted value.
 * @param unit The measurement unit.
 */
public record BpmParameterConfiguredIntegrationEvent(
        Long configId,
        Long equipmentId,
        String parameterName,
        Double minValue,
        Double maxValue,
        String unit
) {

    /**
     * Creates an integration event from the internal Equipment domain event.
     *
     * @param event The internal domain event.
     * @return The integration event.
     */
    public static BpmParameterConfiguredIntegrationEvent from(BpmParameterConfiguredEvent event) {
        return new BpmParameterConfiguredIntegrationEvent(
                event.configId(),
                event.equipmentId(),
                event.parameterName(),
                event.minValue(),
                event.maxValue(),
                event.unit()
        );
    }
}