package com.iotech.qualitrack.platform.ra.domain.exceptions;

/**
 * Exception thrown when a report generation process fails.
 */
public class ReportGenerationException extends RuntimeException {
    /**
     * Constructor for the exception.
     *
     * @param message The failure reason.
     */
    public ReportGenerationException(String message) {
        super(message);
    }

    /**
     * Constructor for the exception.
     *
     * @param message The failure reason.
     * @param cause The original cause of the failure.
     */
    public ReportGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}