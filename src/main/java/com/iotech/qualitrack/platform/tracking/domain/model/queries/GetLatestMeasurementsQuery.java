package com.iotech.qualitrack.platform.tracking.domain.model.queries;

/**
 * Query used to retrieve the latest telemetry measurements.
 *
 * @param equipmentId optional equipment identifier used to filter latest measurements
 */
public record GetLatestMeasurementsQuery(
        Long equipmentId
) {
    /**
     * Creates a query for retrieving latest telemetry measurements.
     *
     * @remarks
     * The equipment identifier is optional. When it is null, the query represents
     * a request for the latest measurements across all monitored equipment.
     */
    public GetLatestMeasurementsQuery {
        if (equipmentId != null && equipmentId <= 0) {
            throw new IllegalArgumentException("Equipment id must be a positive number when provided");
        }
    }
}