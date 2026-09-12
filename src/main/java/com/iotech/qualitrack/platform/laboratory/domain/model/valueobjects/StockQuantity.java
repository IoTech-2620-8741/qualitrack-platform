package com.iotech.qualitrack.platform.laboratory.domain.model.valueobjects;

public record StockQuantity(Integer value) {

    public StockQuantity {
        if (value == null) {
            throw new IllegalArgumentException("Stock quantity cannot be null");
        }
        if (value < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be negative");
        }
    }

    public StockQuantity add(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Cannot add a negative amount to stock");
        }
        return new StockQuantity(this.value + amount);
    }

    public StockQuantity subtract(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Cannot subtract a negative amount");
        }
        if (this.value - amount < 0) {
            throw new IllegalArgumentException("Insufficient stock quantity");
        }
        return new StockQuantity(this.value - amount);
    }
}