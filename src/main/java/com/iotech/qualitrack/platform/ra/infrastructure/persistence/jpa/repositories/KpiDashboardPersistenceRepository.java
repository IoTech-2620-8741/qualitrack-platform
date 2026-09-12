package com.iotech.qualitrack.platform.ra.infrastructure.persistence.jpa.repositories;

import com.iotech.qualitrack.platform.ra.infrastructure.persistence.jpa.entities.KpiDashboardPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for {@link KpiDashboardPersistenceEntity}.
 *
 * <p>Handles database operations for KPI dashboard snapshots and provides
 * access to the latest calculated dashboard for a laboratory.</p>
 */
@Repository
public interface KpiDashboardPersistenceRepository extends JpaRepository<KpiDashboardPersistenceEntity, Long> {

    /**
     * Finds all KPI dashboard snapshots associated with a laboratory.
     *
     * @param laboratoryId The numeric ID of the laboratory.
     */
    List<KpiDashboardPersistenceEntity> findAllByLaboratoryId(Long laboratoryId);

    /**
     * Finds the latest KPI dashboard snapshot associated with a laboratory.
     *
     * @param laboratoryId The numeric ID of the laboratory.
     */
    Optional<KpiDashboardPersistenceEntity> findFirstByLaboratoryIdOrderByTimestampDesc(Long laboratoryId);
}