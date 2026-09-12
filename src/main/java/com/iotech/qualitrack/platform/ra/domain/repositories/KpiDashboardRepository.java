package com.iotech.qualitrack.platform.ra.domain.repositories;

import com.iotech.qualitrack.platform.ra.domain.model.aggregates.KpiDashboard;

import java.util.List;
import java.util.Optional;

/**
 * Repository port for KPI dashboard snapshots.
 *
 * <p>Defines the persistence contract for calculated KPI dashboards and allows
 * retrieving the latest snapshot for a laboratory.</p>
 */
public interface KpiDashboardRepository {

    /**
     * Finds a KPI dashboard snapshot by its unique numeric identifier.
     *
     * @param id The dashboard identifier.
     * @return The matching KPI dashboard, if found.
     */
    Optional<KpiDashboard> findById(Long id);

    /**
     * Finds the latest KPI dashboard snapshot for a laboratory.
     *
     * @param laboratoryId The laboratory identifier.
     * @return The latest KPI dashboard snapshot, if found.
     */
    Optional<KpiDashboard> findLatestByLaboratoryId(Long laboratoryId);

    /**
     * Finds all KPI dashboard snapshots associated with a laboratory.
     *
     * @param laboratoryId The laboratory identifier.
     * @return List of KPI dashboard snapshots.
     */
    List<KpiDashboard> findAllByLaboratoryId(Long laboratoryId);

    /**
     * Saves a KPI dashboard snapshot.
     *
     * @param dashboard The KPI dashboard aggregate to persist.
     * @return The persisted KPI dashboard aggregate.
     */
    KpiDashboard save(KpiDashboard dashboard);

    /**
     * Checks whether a dashboard exists by its unique numeric identifier.
     *
     * @param id The dashboard identifier.
     * @return true if the dashboard exists; otherwise false.
     */
    boolean existsById(Long id);
}