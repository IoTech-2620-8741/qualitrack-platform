package com.iotech.qualitrack.platform.ra.domain.model.entities;

import lombok.Getter;

/**
 * Domain entity representing a single measured data point within a deviation trend.
 *
 * <p>Each data point stores a recorded value and its acceptable lower and upper
 * thresholds at a specific timestamp.</p>
 */
@Getter
public class TrendDataPoint {

    /**
     * Timestamp when the measurement was recorded.
     */
    private String timestamp;

    /**
     * Actual recorded value.
     */
    private Double recordedValue;

    /**
     * Upper acceptable threshold.
     */
    private Double upperThreshold;

    /**
     * Lower acceptable threshold.
     */
    private Double lowerThreshold;

    /**
     * Default constructor.
     * Required by the persistence and mapping layers to reconstruct the entity.
     */
    public TrendDataPoint() {
        // Required for reconstruction by JPA or Assemblers
    }

    /**
     * Creates or reconstructs a trend data point.
     *
     * @param timestamp The measurement timestamp.
     * @param recordedValue The actual recorded value.
     * @param upperThreshold The upper acceptable threshold.
     * @param lowerThreshold The lower acceptable threshold.
     */
    public TrendDataPoint(
            String timestamp,
            Double recordedValue,
            Double upperThreshold,
            Double lowerThreshold
    ) {
        this.timestamp = timestamp;
        this.recordedValue = recordedValue;
        this.upperThreshold = upperThreshold;
        this.lowerThreshold = lowerThreshold;
    }

    /**
     * Indicates whether the recorded value is outside the acceptable threshold range.
     *
     * @return true if the value is below the lower threshold or above the upper threshold.
     */
    public boolean isDeviation() {
        if (recordedValue == null || upperThreshold == null || lowerThreshold == null) {
            return false;
        }

        return recordedValue > upperThreshold || recordedValue < lowerThreshold;
    }
}