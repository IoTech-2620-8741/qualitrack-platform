package com.iotech.qualitrack.platform.equipment.domain.repositories;

import com.iotech.qualitrack.platform.equipment.domain.model.aggregates.Equipment;
import com.iotech.qualitrack.platform.equipment.domain.model.valueobjects.DeviceId;

import java.util.List;
import java.util.Optional;

/**
 * Equipment repository port.
 * * <p>Handles the persistence contract for the Equipment aggregate.</p>
 */
public interface EquipmentRepository {

    Optional<Equipment> findById(Long id);

    List<Equipment> findAll();

    List<Equipment> findAllByLabId(Long labId);

    Optional<Equipment> findBySensorExternalId(DeviceId sensorExternalId);

    Equipment save(Equipment equipment);

    boolean existsById(Long id);

    boolean existsBySerialNumber(String serialNumber);

    void deleteById(Long id);
}