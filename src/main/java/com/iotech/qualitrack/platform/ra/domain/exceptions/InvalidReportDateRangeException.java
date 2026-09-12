package com.iotech.qualitrack.platform.ra.domain.exceptions;

/**
 * Exception thrown when a report date range is invalid.
 */
public class InvalidReportDateRangeException extends RuntimeException {
    /**
     * Constructor for the exception.
     *
     * @param startDate The start date received.
     * @param endDate The end date received.
     */
    public InvalidReportDateRangeException(String startDate, String endDate) {
        super(String.format("Invalid report date range. startDate: %s, endDate: %s.", startDate, endDate));
    }
}