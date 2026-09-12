package com.iotech.qualitrack.platform.batch.domain.exceptions;

/**
 * Exception thrown when a production batch is not found.
 */
public class BatchNotFoundException extends RuntimeException {
    /**
     * Constructor for the exception.
     * @param batchId The numeric ID of the batch that was not found.
     */
    public BatchNotFoundException(Long batchId) {
        super(String.format("Batch with ID %d not found.", batchId));
    }
}