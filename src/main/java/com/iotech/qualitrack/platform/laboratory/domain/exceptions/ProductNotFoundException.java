package com.iotech.qualitrack.platform.laboratory.domain.exceptions;

/**
 * Exception thrown when a pharmaceutical product is not found.
 */
public class ProductNotFoundException extends RuntimeException {
    /**
     * Constructor for the exception.
     * @param productId The numeric ID of the product that was not found.
     */
    public ProductNotFoundException(Long productId) {
        super(String.format("Pharmaceutical product with ID %d not found.", productId));
    }
}