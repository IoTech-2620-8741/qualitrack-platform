package com.iotech.qualitrack.platform.equipment.interfaces.events;

import com.iotech.qualitrack.platform.equipment.domain.model.events.EquipmentRegisteredEvent;

/**
 * Integration event published by the Equipment bounded context when equipment is registered.
 *
 * <p>This event is part of the published language of Equipment. Other bounded contexts
 * should listen to this integration event instead of depending on internal domain events.</p>
 *
 * @param equipmentId the registered equipment identifier
 * @param laboratoryId the laboratory where the equipment belongs
 * @param name the equipment display name
 * @param serialNumber the equipment serial number
 */
public record EquipmentRegisteredIntegrationEvent(
        Long equipmentId,
        Long laboratoryId,
        String name,
        String serialNumber
) {
    /**
     * Creates an integration event from an internal domain event.
     *
     * @param event the internal equipment registered domain event
     * @return the integration event
     */
    public static EquipmentRegisteredIntegrationEvent from(EquipmentRegisteredEvent event) {
        return new EquipmentRegisteredIntegrationEvent(
                event.equipmentId(),
                event.laboratoryId(),
                event.name(),
                event.serialNumber()
        );
    }
}