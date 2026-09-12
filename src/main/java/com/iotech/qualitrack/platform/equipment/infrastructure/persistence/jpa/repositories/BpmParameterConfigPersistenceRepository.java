package com.iotech.qualitrack.platform.equipment.infrastructure.persistence.jpa.repositories;

import com.iotech.qualitrack.platform.equipment.infrastructure.persistence.jpa.entities.BpmParameterConfigPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for {@link BpmParameterConfigPersistenceEntity}.
 */
@Repository
public interface BpmParameterConfigPersistenceRepository extends JpaRepository<BpmParameterConfigPersistenceEntity, Long> {

    List<BpmParameterConfigPersistenceEntity> findAllByEquipmentId(Long equipmentId);

    Optional<BpmParameterConfigPersistenceEntity> findByEquipmentIdAndParameterName(Long equipmentId, String parameterName);
}