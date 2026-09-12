package com.iotech.qualitrack.platform.ra.infrastructure.persistence.jpa.repositories;

import com.iotech.qualitrack.platform.ra.infrastructure.persistence.jpa.entities.DeviationTrendPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for {@link DeviationTrendPersistenceEntity}.
 *
 * <p>Handles database operations for deviation trend analysis records.</p>
 */
@Repository
public interface DeviationTrendPersistenceRepository extends JpaRepository<DeviationTrendPersistenceEntity, Long> {

    /**
     * Finds all deviation trends associated with an equipment.
     *
     * @param equipmentId The numeric ID of the equipment.
     */
    List<DeviationTrendPersistenceEntity> findAllByEquipmentId(Long equipmentId);

    /**
     * Finds all deviation trends associated with an equipment and parameter name.
     *
     * @param equipmentId The numeric ID of the equipment.
     * @param parameterName The monitored parameter name.
     */
    List<DeviationTrendPersistenceEntity> findAllByEquipmentIdAndParameterName(Long equipmentId, String parameterName);
}