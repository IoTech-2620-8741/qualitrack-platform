package com.iotech.qualitrack.platform.ra.infrastructure.persistence.jpa.repositories;

import com.iotech.qualitrack.platform.ra.domain.model.valueobjects.KpiMetricStatus;
import com.iotech.qualitrack.platform.ra.infrastructure.persistence.jpa.entities.KpiMetricPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for {@link KpiMetricPersistenceEntity}.
 *
 * <p>Handles database operations for metrics associated with KPI dashboard snapshots.</p>
 */
@Repository
public interface KpiMetricPersistenceRepository extends JpaRepository<KpiMetricPersistenceEntity, Long> {

    /**
     * Finds all KPI metrics associated with a dashboard snapshot.
     *
     * @param dashboardId The numeric ID of the dashboard snapshot.
     */
    List<KpiMetricPersistenceEntity> findAllByDashboardId(Long dashboardId);

    /**
     * Finds all KPI metrics with a specific evaluated status.
     *
     * @param status The KPI metric status.
     */
    List<KpiMetricPersistenceEntity> findAllByStatus(KpiMetricStatus status);
}