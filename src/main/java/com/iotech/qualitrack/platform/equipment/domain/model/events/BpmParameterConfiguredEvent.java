package com.iotech.qualitrack.platform.equipment.domain.model.events;

import com.iotech.qualitrack.platform.equipment.domain.model.entities.BpmParameterConfig;

/**
 * Domain event published when BPM parameters are configured for equipment.
 *
 * <p>Other bounded contexts can react to BPM threshold changes without directly
 * depending on equipment application services.</p>
 *
 * @param configId The BPM parameter configuration identifier.
 * @param equipmentId The related equipment identifier.
 * @param parameterName The configured parameter name.
 * @param minValue The minimum accepted value.
 * @param maxValue The maximum accepted value.
 * @param unit The measurement unit.
 */
public record BpmParameterConfiguredEvent(
        Long configId,
        Long equipmentId,
        String parameterName,
        Double minValue,
        Double maxValue,
        String unit
) {

    /**
     * Convenience factory that extracts all needed fields from a saved BPM configuration.
     *
     * @param config The saved BPM parameter configuration.
     * @return A fully populated {@link BpmParameterConfiguredEvent}.
     */
    public static BpmParameterConfiguredEvent from(BpmParameterConfig config) {
        return new BpmParameterConfiguredEvent(
                config.getId(),
                config.getEquipmentId(),
                config.getParameterName().name(),
                config.getMinValue(),
                config.getMaxValue(),
                config.getUnit()
        );
    }
}