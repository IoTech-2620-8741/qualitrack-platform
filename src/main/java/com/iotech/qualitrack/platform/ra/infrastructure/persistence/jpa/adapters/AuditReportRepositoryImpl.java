package com.iotech.qualitrack.platform.ra.infrastructure.persistence.jpa.adapters;

import com.iotech.qualitrack.platform.ra.domain.model.aggregates.AuditReport;
import com.iotech.qualitrack.platform.ra.domain.model.valueobjects.ReportType;
import com.iotech.qualitrack.platform.ra.domain.repositories.AuditReportRepository;
import com.iotech.qualitrack.platform.ra.infrastructure.persistence.jpa.assemblers.AuditReportPersistenceAssembler;
import com.iotech.qualitrack.platform.ra.infrastructure.persistence.jpa.repositories.AuditReportPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA adapter implementation for the {@link AuditReportRepository} domain port.
 */
@Repository
public class AuditReportRepositoryImpl implements AuditReportRepository {

    private final AuditReportPersistenceRepository persistenceRepository;

    public AuditReportRepositoryImpl(AuditReportPersistenceRepository persistenceRepository) {
        this.persistenceRepository = persistenceRepository;
    }

    @Override
    public Optional<AuditReport> findById(Long id) {
        return persistenceRepository.findById(id)
                .map(AuditReportPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<AuditReport> findAllByLaboratoryId(Long laboratoryId) {
        return persistenceRepository.findAllByLaboratoryId(laboratoryId).stream()
                .map(AuditReportPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<AuditReport> findAllByBatchId(Long batchId) {
        return persistenceRepository.findAllByBatchId(batchId).stream()
                .map(AuditReportPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<AuditReport> findAllByEquipmentId(Long equipmentId) {
        return persistenceRepository.findAllByEquipmentId(equipmentId).stream()
                .map(AuditReportPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<AuditReport> findAllByReportType(ReportType reportType) {
        return persistenceRepository.findAllByReportType(reportType).stream()
                .map(AuditReportPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public AuditReport save(AuditReport auditReport) {
        var entity = AuditReportPersistenceAssembler.toPersistenceFromDomain(auditReport);
        var savedEntity = persistenceRepository.save(entity);
        return AuditReportPersistenceAssembler.toDomainFromPersistence(savedEntity);
    }

    @Override
    public boolean existsById(Long id) {
        return persistenceRepository.existsById(id);
    }
}