package com.iotech.qualitrack.platform.equipment.application.queryservices;

import com.iotech.qualitrack.platform.equipment.domain.model.entities.BpmParameterConfig;
// Asegúrate de crear este Query Record en la carpeta de queries de tu dominio
import com.iotech.qualitrack.platform.equipment.domain.model.queries.GetBpmParameterConfigsByEquipmentIdQuery;

import java.util.List;

/**
 * Application service contract for BPM parameter configuration read queries.
 */
public interface BpmConfigQueryService {

    /**
     * Handles retrieval of all configured BPM parameter ranges (e.g., Temperature, Pressure)
     * for a specific equipment.
     *
     * @param query equipment-id query
     * @return list of parameter configurations for the given equipment
     * @see GetBpmParameterConfigsByEquipmentIdQuery
     */
    List<BpmParameterConfig> handle(GetBpmParameterConfigsByEquipmentIdQuery query);
}