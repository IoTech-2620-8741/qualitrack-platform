package com.iotech.qualitrack.platform.ra.domain.model.entities;

import com.iotech.qualitrack.platform.ra.domain.model.valueobjects.TrendDirection;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

/**
 * Domain entity representing a deviation trend analysis for an equipment parameter.
 *
 * <p>A trend groups historical data points for a monitored process parameter and
 * exposes the calculated direction of the parameter behavior.</p>
 */
@Getter
public class DeviationTrend {

    /**
     * The unique numeric identifier of the trend analysis.
     */
    private Long id;

    /**
     * The monitored parameter name.
     */
    private String parameterName;

    /**
     * The numeric identifier of the equipment associated with this trend.
     */
    private Long equipmentId;

    /**
     * The calculated direction of the deviation trend.
     */
    private TrendDirection trendDirection;

    /**
     * Historical data points used to evaluate this trend.
     */
    private List<TrendDataPoint> dataPoints;

    /**
     * Default constructor.
     * Required by the persistence and mapping layers to reconstruct the entity.
     */
    public DeviationTrend() {
        // Required for reconstruction by JPA or Assemblers
    }

    /**
     * Reconstructs a DeviationTrend entity from persistence data.
     *
     * @param id The numeric trend identifier.
     * @param parameterName The monitored parameter name.
     * @param equipmentId The equipment identifier.
     * @param trendDirection The calculated trend direction.
     * @param dataPoints Historical data points.
     */
    public DeviationTrend(
            Long id,
            String parameterName,
            Long equipmentId,
            TrendDirection trendDirection,
            List<TrendDataPoint> dataPoints
    ) {
        this.id = id;
        this.parameterName = parameterName;
        this.equipmentId = equipmentId;
        this.trendDirection = trendDirection;
        this.dataPoints = dataPoints;
    }

    /**
     * Creates a new deviation trend analysis entity.
     *
     * @param parameterName The monitored parameter name.
     * @param equipmentId The equipment identifier.
     * @param dataPoints Historical data points.
     */
    public DeviationTrend(
            String parameterName,
            Long equipmentId,
            List<TrendDataPoint> dataPoints
    ) {
        this.parameterName = Objects.requireNonNull(parameterName, "parameterName cannot be null");
        this.equipmentId = Objects.requireNonNull(equipmentId, "equipmentId cannot be null");
        this.dataPoints = Objects.requireNonNull(dataPoints, "dataPoints cannot be null");
        this.trendDirection = calculateTrendDirection(dataPoints);
    }

    /**
     * Calculates the trend direction based on the first and last recorded values.
     *
     * @param dataPoints Historical measurement points.
     * @return The calculated trend direction.
     */
    private TrendDirection calculateTrendDirection(List<TrendDataPoint> dataPoints) {
        if (dataPoints == null || dataPoints.size() < 2) {
            return TrendDirection.STABLE;
        }

        var first = dataPoints.getFirst().getRecordedValue();
        var last = dataPoints.getLast().getRecordedValue();

        if (first == null || last == null) return TrendDirection.STABLE;
        if (last > first) return TrendDirection.INCREASING;
        if (last < first) return TrendDirection.DECREASING;

        return TrendDirection.STABLE;
    }
}