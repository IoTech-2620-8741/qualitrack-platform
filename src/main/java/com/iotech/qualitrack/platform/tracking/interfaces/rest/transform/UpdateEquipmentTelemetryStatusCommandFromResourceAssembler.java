package com.iotech.qualitrack.platform.tracking.interfaces.rest.transform;

import com.iotech.qualitrack.platform.tracking.domain.model.commands.UpdateEquipmentTelemetryStatusCommand;
import com.iotech.qualitrack.platform.tracking.interfaces.rest.resources.UpdateEquipmentTelemetryStatusResource;

/**
 * Assembler that transforms equipment telemetry status REST resources into application commands.
 */
public final class UpdateEquipmentTelemetryStatusCommandFromResourceAssembler {

    private UpdateEquipmentTelemetryStatusCommandFromResourceAssembler() {
    }

    /**
     * Converts an update equipment telemetry status resource into a command.
     *
     * @param equipmentId the equipment numeric identifier from the route path
     * @param resource the update equipment telemetry status resource
     * @return the update equipment telemetry status command
     */
    public static UpdateEquipmentTelemetryStatusCommand toCommandFromResource(
            Long equipmentId,
            UpdateEquipmentTelemetryStatusResource resource
    ) {
        return new UpdateEquipmentTelemetryStatusCommand(
                equipmentId,
                resource.isOnline(),
                resource.currentStatus(),
                resource.lastHeartbeat()
        );
    }
}