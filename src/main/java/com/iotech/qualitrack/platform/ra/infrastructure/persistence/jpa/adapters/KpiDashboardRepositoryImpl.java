package com.iotech.qualitrack.platform.ra.infrastructure.persistence.jpa.adapters;

import com.iotech.qualitrack.platform.ra.domain.model.aggregates.KpiDashboard;
import com.iotech.qualitrack.platform.ra.domain.repositories.KpiDashboardRepository;
import com.iotech.qualitrack.platform.ra.infrastructure.persistence.jpa.assemblers.KpiDashboardPersistenceAssembler;
import com.iotech.qualitrack.platform.ra.infrastructure.persistence.jpa.assemblers.KpiMetricPersistenceAssembler;
import com.iotech.qualitrack.platform.ra.infrastructure.persistence.jpa.repositories.KpiDashboardPersistenceRepository;
import com.iotech.qualitrack.platform.ra.infrastructure.persistence.jpa.repositories.KpiMetricPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA adapter implementation for the {@link KpiDashboardRepository} domain port.
 */
@Repository
public class KpiDashboardRepositoryImpl implements KpiDashboardRepository {

    private final KpiDashboardPersistenceRepository dashboardPersistenceRepository;
    private final KpiMetricPersistenceRepository metricPersistenceRepository;

    public KpiDashboardRepositoryImpl(
            KpiDashboardPersistenceRepository dashboardPersistenceRepository,
            KpiMetricPersistenceRepository metricPersistenceRepository
    ) {
        this.dashboardPersistenceRepository = dashboardPersistenceRepository;
        this.metricPersistenceRepository = metricPersistenceRepository;
    }

    @Override
    public Optional<KpiDashboard> findById(Long id) {
        return dashboardPersistenceRepository.findById(id)
                .map(entity -> {
                    var metrics = metricPersistenceRepository.findAllByDashboardId(entity.getId()).stream()
                            .map(KpiMetricPersistenceAssembler::toDomainFromPersistence)
                            .toList();

                    return KpiDashboardPersistenceAssembler.toDomainFromPersistence(entity, metrics);
                });
    }

    @Override
    public Optional<KpiDashboard> findLatestByLaboratoryId(Long laboratoryId) {
        return dashboardPersistenceRepository.findFirstByLaboratoryIdOrderByTimestampDesc(laboratoryId)
                .map(entity -> {
                    var metrics = metricPersistenceRepository.findAllByDashboardId(entity.getId()).stream()
                            .map(KpiMetricPersistenceAssembler::toDomainFromPersistence)
                            .toList();

                    return KpiDashboardPersistenceAssembler.toDomainFromPersistence(entity, metrics);
                });
    }

    @Override
    public List<KpiDashboard> findAllByLaboratoryId(Long laboratoryId) {
        return dashboardPersistenceRepository.findAllByLaboratoryId(laboratoryId).stream()
                .map(entity -> {
                    var metrics = metricPersistenceRepository.findAllByDashboardId(entity.getId()).stream()
                            .map(KpiMetricPersistenceAssembler::toDomainFromPersistence)
                            .toList();

                    return KpiDashboardPersistenceAssembler.toDomainFromPersistence(entity, metrics);
                })
                .toList();
    }

    @Override
    public KpiDashboard save(KpiDashboard dashboard) {
        var dashboardEntity = KpiDashboardPersistenceAssembler.toPersistenceFromDomain(dashboard);
        var savedDashboardEntity = dashboardPersistenceRepository.save(dashboardEntity);

        if (dashboard.getMetrics() != null) {
            dashboard.getMetrics().stream()
                    .map(metric -> KpiMetricPersistenceAssembler.toPersistenceFromDomain(
                            savedDashboardEntity.getId(),
                            metric
                    ))
                    .forEach(metricPersistenceRepository::save);
        }

        var savedMetrics = metricPersistenceRepository.findAllByDashboardId(savedDashboardEntity.getId()).stream()
                .map(KpiMetricPersistenceAssembler::toDomainFromPersistence)
                .toList();

        return KpiDashboardPersistenceAssembler.toDomainFromPersistence(savedDashboardEntity, savedMetrics);
    }

    @Override
    public boolean existsById(Long id) {
        return dashboardPersistenceRepository.existsById(id);
    }
}