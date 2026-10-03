package com.iotech.qualitrack.platform.tracking.infrastructure.persistence.jpa.repositories;

import com.iotech.qualitrack.platform.tracking.infrastructure.persistence.jpa.entities.TelemetryHistoryPointPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Spring Data JPA repository for telemetry history point persistence entities.
 */
public interface TelemetryHistoryPointPersistenceRepository
        extends JpaRepository<TelemetryHistoryPointPersistenceEntity, Long> {
    /**
     * Finds all telemetry history points ordered by timestamp descending.
     *
     * @return telemetry history point persistence entities
     */
    List<TelemetryHistoryPointPersistenceEntity> findAllByOrderByTimestampDesc();

    /**
     * Finds history points for an equipment ordered by timestamp descending.
     *
     * @param equipmentId the equipment identifier
     * @return telemetry history point persistence entities
     */
    List<TelemetryHistoryPointPersistenceEntity> findAllByEquipmentIdOrderByTimestampDesc(Long equipmentId);

    /**
     * Finds history points between two timestamps ordered by timestamp descending.
     *
     * @param from start timestamp
     * @param to end timestamp
     * @return telemetry history point persistence entities
     */
    List<TelemetryHistoryPointPersistenceEntity> findAllByTimestampBetweenOrderByTimestampDesc(
            String from,
            String to
    );

    /**
     * Finds equipment history points between two timestamps ordered by timestamp descending.
     *
     * @param equipmentId the equipment identifier
     * @param from start timestamp
     * @param to end timestamp
     * @return telemetry history point persistence entities
     */
    List<TelemetryHistoryPointPersistenceEntity> findAllByEquipmentIdAndTimestampBetweenOrderByTimestampDesc(
            Long equipmentId,
            String from,
            String to
    );

    /**
     * Finds anomalous history points for an equipment ordered by timestamp descending.
     *
     * @param equipmentId the equipment identifier
     * @return anomalous telemetry history point persistence entities
     */
    List<TelemetryHistoryPointPersistenceEntity> findAllByEquipmentIdAndIsAnomalyTrueOrderByTimestampDesc(
            Long equipmentId
    );

    @Query("select max(p.createdAt) from TelemetryHistoryPointPersistenceEntity p where p.equipmentId = :equipmentId")
    Date findLastCreatedAtByEquipmentId(@Param("equipmentId") Long equipmentId);
}
