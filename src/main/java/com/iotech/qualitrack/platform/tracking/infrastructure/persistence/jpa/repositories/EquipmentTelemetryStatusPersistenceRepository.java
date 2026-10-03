package com.iotech.qualitrack.platform.tracking.infrastructure.persistence.jpa.repositories;

import com.iotech.qualitrack.platform.tracking.infrastructure.persistence.jpa.entities.EquipmentTelemetryStatusPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for equipment telemetry status persistence entities.
 */
public interface EquipmentTelemetryStatusPersistenceRepository
        extends JpaRepository<EquipmentTelemetryStatusPersistenceEntity, Long> {
    /**
     * Finds the latest telemetry status for an equipment.
     *
     * @param equipmentId the equipment identifier
     * @return the latest telemetry status persistence entity when found
     */
    Optional<EquipmentTelemetryStatusPersistenceEntity> findFirstByEquipmentIdOrderByLastHeartbeatDesc(
            Long equipmentId
    );

    /**
     * Finds status records ordered by latest heartbeat descending.
     *
     * @return equipment telemetry status persistence entities
     */
    List<EquipmentTelemetryStatusPersistenceEntity> findAllByOrderByLastHeartbeatDesc();

    @Query("select max(s.createdAt) from EquipmentTelemetryStatusPersistenceEntity s where s.equipmentId = :equipmentId")
    Date findLastCreatedAtByEquipmentId(@Param("equipmentId") Long equipmentId);
}
