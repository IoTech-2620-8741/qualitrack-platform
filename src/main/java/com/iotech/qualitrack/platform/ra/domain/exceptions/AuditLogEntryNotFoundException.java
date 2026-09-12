package com.iotech.qualitrack.platform.ra.domain.exceptions;

/**
 * Exception thrown when an audit log entry is not found.
 */
public class AuditLogEntryNotFoundException extends RuntimeException {
    /**
     * Constructor for the exception.
     *
     * @param auditLogEntryId The numeric ID of the audit log entry that was not found.
     */
    public AuditLogEntryNotFoundException(Long auditLogEntryId) {
        super(String.format("Audit log entry with ID %d not found.", auditLogEntryId));
    }
}