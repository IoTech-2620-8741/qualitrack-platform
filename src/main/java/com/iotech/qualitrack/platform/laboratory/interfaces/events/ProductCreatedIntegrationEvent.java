package com.iotech.qualitrack.platform.laboratory.interfaces.events;

import com.iotech.qualitrack.platform.laboratory.domain.model.events.ProductCreatedEvent;

/**
 * Integration event published when a pharmaceutical product is created.
 *
 * <p>This is part of the published language of the Laboratory bounded context.</p>
 *
 * @param productId The created product identifier.
 * @param laboratoryId The laboratory identifier that owns the product.
 * @param code The internal product catalog code.
 * @param name The product name.
 */
public record ProductCreatedIntegrationEvent(
        Long productId,
        Long laboratoryId,
        String code,
        String name
) {

    /**
     * Creates an integration event from the internal Laboratory domain event.
     *
     * @param event The internal domain event.
     * @return The integration event.
     */
    public static ProductCreatedIntegrationEvent from(ProductCreatedEvent event) {
        return new ProductCreatedIntegrationEvent(
                event.productId(),
                event.laboratoryId(),
                event.code(),
                event.name()
        );
    }
}