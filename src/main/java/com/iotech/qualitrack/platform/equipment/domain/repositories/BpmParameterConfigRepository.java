package com.iotech.qualitrack.platform.equipment.domain.repositories;

import com.iotech.qualitrack.platform.equipment.domain.model.entities.BpmParameterConfig;

import java.util.List;
import java.util.Optional;

/**
 * Repository port for the BpmParameterConfig entity.
 *
 * <p>Handles the persistence contract for equipment telemetry and operational thresholds.</p>
 */
public interface BpmParameterConfigRepository {

    Optional<BpmParameterConfig> findById(Long id);

    List<BpmParameterConfig> findAll();

    /**
     * Finds all BPM parameter configurations associated with a specific equipment.
     * @param equipmentId The numeric ID of the equipment.
     */
    List<BpmParameterConfig> findAllByEquipmentId(Long equipmentId);

    /**
     * Finds a specific parameter configuration by equipment ID and parameter name.
     * Useful for checking if a limit already exists before creating a new one or updating it.
     *
     * @param equipmentId The numeric ID of the equipment.
     * @param parameterName The string name of the parameter (e.g., "Temperature").
     */
    Optional<BpmParameterConfig> findByEquipmentIdAndParameterName(Long equipmentId, String parameterName);

    BpmParameterConfig save(BpmParameterConfig bpmParameterConfig);

    boolean existsById(Long id);

    void deleteById(Long id);
}