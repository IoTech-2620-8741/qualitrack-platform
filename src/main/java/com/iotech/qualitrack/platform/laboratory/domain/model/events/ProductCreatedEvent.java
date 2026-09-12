package com.iotech.qualitrack.platform.laboratory.domain.model.events;

import com.iotech.qualitrack.platform.laboratory.domain.model.aggregates.PharmaceuticalProduct;

/**
 * Domain event published when a pharmaceutical product is successfully created.
 *
 * <p>Other bounded contexts can react to product catalog changes without directly
 * depending on laboratory application services.</p>
 *
 * @param productId The numeric identity assigned to the product.
 * @param laboratoryId The numeric identity of the laboratory that owns the product.
 * @param code The internal product catalog code.
 * @param name The product commercial or internal name.
 */
public record ProductCreatedEvent(
        Long productId,
        Long laboratoryId,
        String code,
        String name
) {

    /**
     * Convenience factory that extracts all needed fields from a saved product.
     *
     * @param product The saved pharmaceutical product.
     * @return A fully populated {@link ProductCreatedEvent}.
     */
    public static ProductCreatedEvent from(PharmaceuticalProduct product) {
        return new ProductCreatedEvent(
                product.getId(),
                product.getLaboratoryId(),
                product.getCode(),
                product.getName()
        );
    }
}