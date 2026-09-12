package com.iotech.qualitrack.platform.ra.interfaces.rest.resources;

/**
 * Resource used to request deviation trend calculation.
 *
 * @param parameterName The monitored parameter name.
 */
public record CalculateDeviationTrendResource(
        String parameterName
) {
}