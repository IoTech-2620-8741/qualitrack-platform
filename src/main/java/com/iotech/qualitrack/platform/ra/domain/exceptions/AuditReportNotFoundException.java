package com.iotech.qualitrack.platform.ra.domain.exceptions;

/**
 * Exception thrown when an audit report is not found.
 */
public class AuditReportNotFoundException extends RuntimeException {
    /**
     * Constructor for the exception.
     *
     * @param reportId The numeric ID of the audit report that was not found.
     */
    public AuditReportNotFoundException(Long reportId) {
        super(String.format("Audit report with ID %d not found.", reportId));
    }
}