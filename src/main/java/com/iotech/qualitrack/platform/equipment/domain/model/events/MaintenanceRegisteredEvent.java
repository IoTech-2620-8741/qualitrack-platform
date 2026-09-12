package com.iotech.qualitrack.platform.equipment.domain.model.events;

import com.iotech.qualitrack.platform.equipment.domain.model.aggregates.MaintenanceRecord;
import com.iotech.qualitrack.platform.equipment.domain.model.valueobjects.MaintenanceType;

import java.time.LocalDate;

/**
 * Domain event published when an equipment maintenance record is registered.
 *
 * <p>Other bounded contexts can react to maintenance events without directly
 * depending on equipment application services.</p>
 *
 * @param maintenanceRecordId The maintenance record identifier.
 * @param equipmentId The related equipment identifier.
 * @param maintenanceDate The maintenance date.
 * @param type The maintenance type.
 * @param technicianName The technician responsible for the maintenance.
 */
public record MaintenanceRegisteredEvent(
        Long maintenanceRecordId,
        Long equipmentId,
        LocalDate maintenanceDate,
        MaintenanceType type,
        String technicianName
) {

    /**
     * Convenience factory that extracts all needed fields from a saved maintenance record.
     *
     * @param record The saved maintenance record.
     * @return A fully populated {@link MaintenanceRegisteredEvent}.
     */
    public static MaintenanceRegisteredEvent from(MaintenanceRecord record) {
        return new MaintenanceRegisteredEvent(
                record.getId(),
                record.getEquipmentId(),
                record.getMaintenanceDate(),
                record.getType(),
                record.getTechnicianName()
        );
    }
}