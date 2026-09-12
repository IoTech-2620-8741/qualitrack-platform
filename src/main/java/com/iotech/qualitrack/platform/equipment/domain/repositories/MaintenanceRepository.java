package com.iotech.qualitrack.platform.equipment.domain.repositories;

import com.iotech.qualitrack.platform.equipment.domain.model.aggregates.MaintenanceRecord;

import java.util.List;
import java.util.Optional;

/**
 * Maintenance record repository port.
 * * <p>Handles the persistence contract for the MaintenanceRecord aggregate.</p>
 */
public interface MaintenanceRepository {

    Optional<MaintenanceRecord> findById(Long id);

    List<MaintenanceRecord> findAll();

    List<MaintenanceRecord> findAllByEquipmentId(Long equipmentId);

    MaintenanceRecord save(MaintenanceRecord maintenanceRecord);

    boolean existsById(Long id);

    void deleteById(Long id);
}