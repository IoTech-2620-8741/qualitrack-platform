package com.iotech.qualitrack.platform.equipment.domain.model.events;

import com.iotech.qualitrack.platform.equipment.domain.model.aggregates.Equipment;

/**
 * Domain event published when a new {@link Equipment} is successfully registered and persisted.
 *
 * <p>Other bounded contexts (e.g. maintenance scheduling or inventory) can listen to this event
 * to react to equipment creation without directly coupling to the {@code equipment}
 * application services.</p>
 *
 * @param equipmentId  The numeric identity assigned to the newly created equipment.
 * @param laboratoryId The numeric identity of the laboratory where the equipment is located.
 * @param name         The display name of the equipment.
 * @param serialNumber The unique serial number for traceability.
 */
public record EquipmentRegisteredEvent(
        Long equipmentId,
        Long laboratoryId,
        String name,
        String serialNumber) {

    /**
     * Convenience factory that extracts all needed fields from a saved {@link Equipment}.
     *
     * @param equipment the saved equipment
     * @return a fully populated {@link EquipmentRegisteredEvent}
     */
    public static EquipmentRegisteredEvent from(Equipment equipment) {
        return new EquipmentRegisteredEvent(
                equipment.getId(),
                equipment.getLabId(),
                equipment.getName(),
                equipment.getSerialNumber()
        );
    }
}