package com.iotech.qualitrack.platform.batch.domain.exceptions;

/**
 * Exception thrown when a raw material usage record is not found.
 */
public class RawMaterialUsageNotFoundException extends RuntimeException {
    /**
     * Constructor for the exception.
     * @param usageId The numeric ID of the raw material usage record that was not found.
     */
    public RawMaterialUsageNotFoundException(Long usageId) {
        super(String.format("Raw material usage record with ID %d not found.", usageId));
    }
}