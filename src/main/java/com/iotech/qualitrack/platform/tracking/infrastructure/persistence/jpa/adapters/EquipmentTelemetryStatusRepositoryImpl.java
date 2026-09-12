package com.iotech.qualitrack.platform.tracking.infrastructure.persistence.jpa.adapters;

import com.iotech.qualitrack.platform.tracking.domain.model.entities.EquipmentTelemetryStatus;
import com.iotech.qualitrack.platform.tracking.domain.repositories.EquipmentTelemetryStatusRepository;
import com.iotech.qualitrack.platform.tracking.infrastructure.persistence.jpa.assemblers.EquipmentTelemetryStatusPersistenceAssembler;
import com.iotech.qualitrack.platform.tracking.infrastructure.persistence.jpa.repositories.EquipmentTelemetryStatusPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA adapter implementation for the {@link EquipmentTelemetryStatusRepository} domain port.
 */
@Repository
public class EquipmentTelemetryStatusRepositoryImpl implements EquipmentTelemetryStatusRepository {

    private final EquipmentTelemetryStatusPersistenceRepository persistenceRepository;

    public EquipmentTelemetryStatusRepositoryImpl(
            EquipmentTelemetryStatusPersistenceRepository persistenceRepository
    ) {
        this.persistenceRepository = persistenceRepository;
    }

    @Override
    public Optional<EquipmentTelemetryStatus> findById(Long id) {
        return persistenceRepository.findById(id)
                .map(EquipmentTelemetryStatusPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Optional<EquipmentTelemetryStatus> findLatestByEquipmentId(Long equipmentId) {
        return persistenceRepository.findFirstByEquipmentIdOrderByLastHeartbeatDesc(equipmentId)
                .map(EquipmentTelemetryStatusPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<EquipmentTelemetryStatus> findAllLatest() {
        return persistenceRepository.findAllByOrderByLastHeartbeatDesc().stream()
                .map(EquipmentTelemetryStatusPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public EquipmentTelemetryStatus save(EquipmentTelemetryStatus status) {
        var entity = EquipmentTelemetryStatusPersistenceAssembler.toPersistenceFromDomain(status);
        var saved = persistenceRepository.save(entity);

        return EquipmentTelemetryStatusPersistenceAssembler.toDomainFromPersistence(saved);
    }

    @Override
    public boolean existsById(Long id) {
        return persistenceRepository.existsById(id);
    }
}