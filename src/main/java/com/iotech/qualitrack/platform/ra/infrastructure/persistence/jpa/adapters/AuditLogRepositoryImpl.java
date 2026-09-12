package com.iotech.qualitrack.platform.ra.infrastructure.persistence.jpa.adapters;

import com.iotech.qualitrack.platform.ra.domain.model.entities.AuditLogEntry;
import com.iotech.qualitrack.platform.ra.domain.model.valueobjects.AuditAction;
import com.iotech.qualitrack.platform.ra.domain.repositories.AuditLogRepository;
import com.iotech.qualitrack.platform.ra.infrastructure.persistence.jpa.assemblers.AuditLogEntryPersistenceAssembler;
import com.iotech.qualitrack.platform.ra.infrastructure.persistence.jpa.repositories.AuditLogEntryPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA adapter implementation for the {@link AuditLogRepository} domain port.
 */
@Repository
public class AuditLogRepositoryImpl implements AuditLogRepository {

    private static final String EQUIPMENT_ENTITY_TYPE = "EQUIPMENT";
    private static final String BATCH_ENTITY_TYPE = "BATCH";

    private final AuditLogEntryPersistenceRepository persistenceRepository;

    public AuditLogRepositoryImpl(AuditLogEntryPersistenceRepository persistenceRepository) {
        this.persistenceRepository = persistenceRepository;
    }

    @Override
    public Optional<AuditLogEntry> findById(Long id) {
        return persistenceRepository.findById(id)
                .map(AuditLogEntryPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<AuditLogEntry> findAll() {
        return persistenceRepository.findAll().stream()
                .map(AuditLogEntryPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<AuditLogEntry> findAllByEquipmentId(Long equipmentId) {
        return persistenceRepository.findAllByEntityTypeAndEntityId(EQUIPMENT_ENTITY_TYPE, equipmentId).stream()
                .map(AuditLogEntryPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<AuditLogEntry> findAllByBatchId(Long batchId) {
        return persistenceRepository.findAllByEntityTypeAndEntityId(BATCH_ENTITY_TYPE, batchId).stream()
                .map(AuditLogEntryPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<AuditLogEntry> findAllByPerformedBy(Long performedBy) {
        return persistenceRepository.findAllByPerformedBy(performedBy).stream()
                .map(AuditLogEntryPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<AuditLogEntry> findAllByAction(AuditAction action) {
        return persistenceRepository.findAllByAction(action).stream()
                .map(AuditLogEntryPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<AuditLogEntry> findAllByDateRange(String dateFrom, String dateTo) {
        return persistenceRepository.findAllByTimestampBetween(dateFrom, dateTo).stream()
                .map(AuditLogEntryPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<AuditLogEntry> findAllByEquipmentIdAndDateRange(Long equipmentId, String dateFrom, String dateTo) {
        return persistenceRepository
                .findAllByEntityTypeAndEntityIdAndTimestampBetween(
                        EQUIPMENT_ENTITY_TYPE,
                        equipmentId,
                        dateFrom,
                        dateTo
                )
                .stream()
                .map(AuditLogEntryPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<AuditLogEntry> findAllByBatchIdAndDateRange(Long batchId, String dateFrom, String dateTo) {
        return persistenceRepository
                .findAllByEntityTypeAndEntityIdAndTimestampBetween(
                        BATCH_ENTITY_TYPE,
                        batchId,
                        dateFrom,
                        dateTo
                )
                .stream()
                .map(AuditLogEntryPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public AuditLogEntry save(AuditLogEntry auditLogEntry) {
        var entity = AuditLogEntryPersistenceAssembler.toPersistenceFromDomain(auditLogEntry);
        var savedEntity = persistenceRepository.save(entity);
        return AuditLogEntryPersistenceAssembler.toDomainFromPersistence(savedEntity);
    }

    @Override
    public boolean existsById(Long id) {
        return persistenceRepository.existsById(id);
    }
}