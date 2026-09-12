package com.iotech.qualitrack.platform.shared.domain.model.valueobjects;

import java.math.BigDecimal;
import java.util.Locale;

/** Stock is consumed in its recorded unit, without implicit unit conversions. */
public final class StockUnit {
    private StockUnit() { }

    public static String normalize(String unit) {
        if (unit == null) throw new IllegalArgumentException("Stock unit is required");
        return switch (unit.trim().toLowerCase(Locale.ROOT)) {
            case "kg", "kilogram", "kilograms", "kilogramo", "kilogramos" -> "kg";
            case "g", "gram", "grams", "gramo", "gramos" -> "g";
            case "l", "liter", "liters", "litre", "litres", "litro", "litros" -> "L";
            case "ml", "milliliter", "milliliters", "mililitro", "mililitros" -> "mL";
            case "unit", "units", "unidad", "unidades" -> "units";
            default -> throw new IllegalArgumentException("Unsupported stock unit: " + unit);
        };
    }

    public static void requireSame(String stockUnit, String requestedUnit) {
        if (!normalize(stockUnit).equals(normalize(requestedUnit))) {
            throw new IllegalArgumentException("Consumption unit must match stock unit: " + stockUnit);
        }
    }

    public static void validateQuantity(BigDecimal quantity, String unit, boolean allowZero) {
        if (quantity == null || quantity.signum() < 0 || (!allowZero && quantity.signum() == 0)
                || quantity.stripTrailingZeros().scale() > 3 || quantity.compareTo(new BigDecimal("9999999999999999.999")) > 0) {
            throw new IllegalArgumentException("Quantity must be " + (allowZero ? "non-negative" : "positive")
                    + " with at most three decimal places");
        }
        if (normalize(unit).equals("units") && quantity.stripTrailingZeros().scale() > 0) {
            throw new IllegalArgumentException("Stock in units requires a whole quantity");
        }
    }
}
