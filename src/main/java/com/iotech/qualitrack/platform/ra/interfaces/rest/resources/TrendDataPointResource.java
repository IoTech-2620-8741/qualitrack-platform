package com.iotech.qualitrack.platform.ra.interfaces.rest.resources;

/**
 * Resource representation of a single deviation trend data point.
 *
 * @param timestamp The timestamp when the measurement was recorded.
 * @param recordedValue The actual measured value.
 * @param upperThreshold The upper acceptable threshold.
 * @param lowerThreshold The lower acceptable threshold.
 */
public record TrendDataPointResource(
        String timestamp,
        Double recordedValue,
        Double upperThreshold,
        Double lowerThreshold
) {
}