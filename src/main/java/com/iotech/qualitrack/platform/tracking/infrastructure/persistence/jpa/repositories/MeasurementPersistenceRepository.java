package com.iotech.qualitrack.platform.tracking.infrastructure.persistence.jpa.repositories;

import com.iotech.qualitrack.platform.tracking.infrastructure.persistence.jpa.entities.MeasurementPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for telemetry measurement persistence entities.
 */
public interface MeasurementPersistenceRepository extends JpaRepository<MeasurementPersistenceEntity, Long> {
    /**
     * Finds all measurements ordered by source timestamp descending.
     *
     * @return measurement persistence entities
     */
    List<MeasurementPersistenceEntity> findAllByOrderByTimestampDesc();

    /**
     * Finds measurements for an equipment ordered by source timestamp descending.
     *
     * @param equipmentId the equipment identifier
     * @return measurement persistence entities
     */
    List<MeasurementPersistenceEntity> findAllByEquipmentIdOrderByTimestampDesc(Long equipmentId);
}