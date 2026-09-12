package com.iotech.qualitrack.platform.tracking.domain.model.queries;

/**
 * Query used to retrieve historical telemetry points.
 *
 * @param equipmentId optional equipment identifier used to filter telemetry history
 * @param from optional start timestamp filter
 * @param to optional end timestamp filter
 */
public record GetTelemetryHistoryQuery(
        Long equipmentId,
        String from,
        String to
) {
    /**
     * Creates a query for retrieving telemetry history.
     *
     * @remarks
     * All filters are optional, but when an equipment identifier is provided it
     * must be a positive number. Date filters are represented as strings to keep
     * the REST contract aligned with the frontend ISO timestamp format.
     */
    public GetTelemetryHistoryQuery {
        if (equipmentId != null && equipmentId <= 0) {
            throw new IllegalArgumentException("Equipment id must be a positive number when provided");
        }
        if (from != null && from.isBlank()) {
            throw new IllegalArgumentException("From timestamp cannot be blank when provided");
        }
        if (to != null && to.isBlank()) {
            throw new IllegalArgumentException("To timestamp cannot be blank when provided");
        }
    }
}