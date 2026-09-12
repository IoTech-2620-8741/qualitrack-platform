package com.iotech.qualitrack.platform.ra.domain.exceptions;

/**
 * Exception thrown when attempting to mutate an immutable audit report.
 */
public class ImmutableAuditReportException extends RuntimeException {
    /**
     * Constructor for the exception.
     *
     * @param reportId The numeric ID of the immutable audit report.
     */
    public ImmutableAuditReportException(Long reportId) {
        super(String.format("Audit report with ID %d is immutable and cannot be modified.", reportId));
    }
}