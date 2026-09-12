package com.iotech.qualitrack.platform.equipment.interfaces.events;

import com.iotech.qualitrack.platform.equipment.domain.model.events.MaintenanceRegisteredEvent;

/**
 * Integration event published when an equipment maintenance record is registered.
 *
 * <p>This is part of the published language of the Equipment bounded context.</p>
 *
 * @param maintenanceRecordId The maintenance record identifier.
 * @param equipmentId The related equipment identifier.
 * @param maintenanceDate The maintenance date.
 * @param type The maintenance type.
 * @param technicianName The technician responsible for the maintenance.
 */
public record MaintenanceRegisteredIntegrationEvent(
        Long maintenanceRecordId,
        Long equipmentId,
        String maintenanceDate,
        String type,
        String technicianName
) {

    /**
     * Creates an integration event from the internal Equipment domain event.
     *
     * @param event The internal domain event.
     * @return The integration event.
     */
    public static MaintenanceRegisteredIntegrationEvent from(MaintenanceRegisteredEvent event) {
        return new MaintenanceRegisteredIntegrationEvent(
                event.maintenanceRecordId(),
                event.equipmentId(),
                event.maintenanceDate().toString(),
                event.type().name(),
                event.technicianName()
        );
    }
}