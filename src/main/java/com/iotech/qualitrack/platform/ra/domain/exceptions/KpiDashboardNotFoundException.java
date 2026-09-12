package com.iotech.qualitrack.platform.ra.domain.exceptions;

/**
 * Exception thrown when a KPI dashboard snapshot is not found.
 */
public class KpiDashboardNotFoundException extends RuntimeException {
    /**
     * Constructor for the exception.
     *
     * @param laboratoryId The numeric ID of the laboratory whose KPI dashboard was not found.
     */
    public KpiDashboardNotFoundException(Long laboratoryId) {
        super(String.format("KPI dashboard for laboratory with ID %d not found.", laboratoryId));
    }
}