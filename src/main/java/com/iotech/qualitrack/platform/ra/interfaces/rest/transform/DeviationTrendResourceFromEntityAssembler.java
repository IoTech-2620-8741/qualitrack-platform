package com.iotech.qualitrack.platform.ra.interfaces.rest.transform;

import com.iotech.qualitrack.platform.ra.domain.model.entities.DeviationTrend;
import com.iotech.qualitrack.platform.ra.interfaces.rest.resources.DeviationTrendResource;

/**
 * Assembler that transforms deviation trend domain entities into REST resources.
 */
public final class DeviationTrendResourceFromEntityAssembler {
    private DeviationTrendResourceFromEntityAssembler() {
    }

    /**
     * Converts a deviation trend entity into its REST resource representation.
     *
     * @param entity the deviation trend entity
     * @return the deviation trend resource
     */
    public static DeviationTrendResource toResourceFromEntity(DeviationTrend entity) {
        return new DeviationTrendResource(
                entity.getId(),
                entity.getParameterName(),
                entity.getEquipmentId(),
                entity.getTrendDirection(),
                entity.getDataPoints().stream()
                        .map(TrendDataPointResourceFromEntityAssembler::toResourceFromEntity)
                        .toList()
        );
    }
}