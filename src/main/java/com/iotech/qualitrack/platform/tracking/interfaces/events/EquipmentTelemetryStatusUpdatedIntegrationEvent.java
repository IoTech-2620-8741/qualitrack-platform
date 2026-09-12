package com.iotech.qualitrack.platform.tracking.interfaces.events;

import com.iotech.qualitrack.platform.tracking.domain.model.events.EquipmentTelemetryStatusUpdatedEvent;
import com.iotech.qualitrack.platform.tracking.domain.model.valueobjects.TelemetryStatus;

/**
 * Integration event published when equipment telemetry status has been updated.
 *
 * <p>This is part of the published language of the Tracking bounded context.</p>
 *
 * @param statusId The telemetry status record identifier.
 * @param equipmentId The related equipment identifier.
 * @param isOnline Whether the equipment is online.
 * @param currentStatus The current telemetry status.
 * @param lastHeartbeat The last heartbeat timestamp.
 */
public record EquipmentTelemetryStatusUpdatedIntegrationEvent(
        Long statusId,
        Long equipmentId,
        Boolean isOnline,
        TelemetryStatus currentStatus,
        String lastHeartbeat
) {

    /**
     * Creates an integration event from the internal Tracking domain event.
     *
     * @param event The internal domain event.
     * @return The integration event.
     */
    public static EquipmentTelemetryStatusUpdatedIntegrationEvent from(EquipmentTelemetryStatusUpdatedEvent event) {
        return new EquipmentTelemetryStatusUpdatedIntegrationEvent(
                event.statusId(),
                event.equipmentId(),
                event.isOnline(),
                event.currentStatus(),
                event.lastHeartbeat()
        );
    }
}