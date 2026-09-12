package com.iotech.qualitrack.platform.equipment.interfaces.events;

import com.iotech.qualitrack.platform.equipment.domain.model.events.CalibrationExpiredEvent;

/**
 * Integration event published by the Equipment bounded context when an equipment calibration expires.
 *
 * <p>This event is part of the published language of the Equipment context. Other bounded contexts
 * such as Compliance and Alerts should listen to this event instead of depending on Equipment
 * internal domain events.</p>
 *
 * @param equipmentId the equipment identifier
 * @param laboratoryId the laboratory that owns the equipment
 * @param equipmentName the equipment display name
 * @param serialNumber the equipment serial number
 */
public record CalibrationExpiredIntegrationEvent(
        Long equipmentId,
        Long laboratoryId,
        String equipmentName,
        String serialNumber
) {
    /**
     * Creates an integration event from an internal domain event.
     *
     * @param event the internal calibration expired domain event
     * @return the integration event
     */
    public static CalibrationExpiredIntegrationEvent from(CalibrationExpiredEvent event) {
        return new CalibrationExpiredIntegrationEvent(
                event.equipmentId(),
                event.laboratoryId(),
                event.equipmentName(),
                event.serialNumber()
        );
    }
}