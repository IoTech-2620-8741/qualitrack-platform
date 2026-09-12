package com.iotech.qualitrack.platform.laboratory.interfaces.events;

import com.iotech.qualitrack.platform.laboratory.domain.model.events.LaboratoryRegisteredEvent;

/**
 * Integration event published by the Laboratory bounded context when a laboratory is registered.
 *
 * <p>This event is part of the published language of Laboratory. Other bounded contexts
 * should listen to this event instead of depending on internal domain events.</p>
 *
 * @param laboratoryId the registered laboratory identifier
 * @param name the official laboratory name
 */
public record LaboratoryRegisteredIntegrationEvent(
        Long laboratoryId,
        String name
) {
    /**
     * Creates an integration event from an internal domain event.
     *
     * @param event the internal laboratory registered domain event
     * @return the integration event
     */
    public static LaboratoryRegisteredIntegrationEvent from(LaboratoryRegisteredEvent event) {
        return new LaboratoryRegisteredIntegrationEvent(
                event.laboratoryId(),
                event.name()
        );
    }
}