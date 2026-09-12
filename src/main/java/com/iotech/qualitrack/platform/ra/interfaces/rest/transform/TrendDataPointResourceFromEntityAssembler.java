package com.iotech.qualitrack.platform.ra.interfaces.rest.transform;

import com.iotech.qualitrack.platform.ra.domain.model.entities.TrendDataPoint;
import com.iotech.qualitrack.platform.ra.interfaces.rest.resources.TrendDataPointResource;

/**
 * Assembler that transforms deviation trend data points into REST resources.
 */
public final class TrendDataPointResourceFromEntityAssembler {
    private TrendDataPointResourceFromEntityAssembler() {
    }

    /**
     * Converts a trend data point entity into its REST resource representation.
     *
     * @param entity the trend data point entity
     * @return the trend data point resource
     */
    public static TrendDataPointResource toResourceFromEntity(TrendDataPoint entity) {
        return new TrendDataPointResource(
                entity.getTimestamp(),
                entity.getRecordedValue(),
                entity.getUpperThreshold(),
                entity.getLowerThreshold()
        );
    }
}