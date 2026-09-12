package com.iotech.qualitrack.platform.equipment.application.queryservices;

import com.iotech.qualitrack.platform.equipment.domain.model.aggregates.MaintenanceRecord;
import com.iotech.qualitrack.platform.equipment.domain.model.queries.GetMaintenanceByEquipmentIdQuery;

import java.util.List;

/**
 * Application service contract for maintenance record read queries.
 */
public interface MaintenanceQueryService {

    /**
     * Handles retrieval of the maintenance history for a specific equipment.
     *
     * @param query equipment-id query
     * @return list of maintenance records associated with the equipment
     * @see GetMaintenanceByEquipmentIdQuery
     */
    List<MaintenanceRecord> handle(GetMaintenanceByEquipmentIdQuery query);
}