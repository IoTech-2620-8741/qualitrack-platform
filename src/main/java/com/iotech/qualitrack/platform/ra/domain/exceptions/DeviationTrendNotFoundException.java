package com.iotech.qualitrack.platform.ra.domain.exceptions;

/**
 * Exception thrown when a deviation trend is not found.
 */
public class DeviationTrendNotFoundException extends RuntimeException {
    /**
     * Constructor for the exception.
     *
     * @param trendId The numeric ID of the deviation trend that was not found.
     */
    public DeviationTrendNotFoundException(Long trendId) {
        super(String.format("Deviation trend with ID %d not found.", trendId));
    }
}