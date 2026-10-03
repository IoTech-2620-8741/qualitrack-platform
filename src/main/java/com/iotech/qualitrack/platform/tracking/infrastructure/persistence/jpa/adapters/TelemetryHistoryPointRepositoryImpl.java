package com.iotech.qualitrack.platform.tracking.infrastructure.persistence.jpa.adapters;

import com.iotech.qualitrack.platform.tracking.domain.model.entities.TelemetryHistoryPoint;
import com.iotech.qualitrack.platform.tracking.domain.repositories.TelemetryHistoryPointRepository;
import com.iotech.qualitrack.platform.tracking.infrastructure.persistence.jpa.assemblers.TelemetryHistoryPointPersistenceAssembler;
import com.iotech.qualitrack.platform.tracking.infrastructure.persistence.jpa.repositories.TelemetryHistoryPointPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * JPA adapter implementation for the {@link TelemetryHistoryPointRepository} domain port.
 */
@Repository
public class TelemetryHistoryPointRepositoryImpl implements TelemetryHistoryPointRepository {

    private final TelemetryHistoryPointPersistenceRepository persistenceRepository;

    public TelemetryHistoryPointRepositoryImpl(
            TelemetryHistoryPointPersistenceRepository persistenceRepository
    ) {
        this.persistenceRepository = persistenceRepository;
    }

    @Override
    public Optional<TelemetryHistoryPoint> findById(Long id) {
        return persistenceRepository.findById(id)
                .map(TelemetryHistoryPointPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<TelemetryHistoryPoint> findByFilters(Long equipmentId, String from, String to) {
        var hasEquipment = equipmentId != null;
        var hasDateRange = from != null && !from.isBlank() && to != null && !to.isBlank();

        if (hasEquipment && hasDateRange) {
            return persistenceRepository
                    .findAllByEquipmentIdAndTimestampBetweenOrderByTimestampDesc(equipmentId, from, to)
                    .stream()
                    .map(TelemetryHistoryPointPersistenceAssembler::toDomainFromPersistence)
                    .toList();
        }

        if (hasEquipment) {
            return findAllByEquipmentId(equipmentId);
        }

        if (hasDateRange) {
            return persistenceRepository.findAllByTimestampBetweenOrderByTimestampDesc(from, to)
                    .stream()
                    .map(TelemetryHistoryPointPersistenceAssembler::toDomainFromPersistence)
                    .toList();
        }

        return persistenceRepository.findAllByOrderByTimestampDesc().stream()
                .map(TelemetryHistoryPointPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<TelemetryHistoryPoint> findAllByEquipmentId(Long equipmentId) {
        return persistenceRepository.findAllByEquipmentIdOrderByTimestampDesc(equipmentId).stream()
                .map(TelemetryHistoryPointPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<TelemetryHistoryPoint> findAllAnomaliesByEquipmentId(Long equipmentId) {
        return persistenceRepository.findAllByEquipmentIdAndIsAnomalyTrueOrderByTimestampDesc(equipmentId).stream()
                .map(TelemetryHistoryPointPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public TelemetryHistoryPoint save(TelemetryHistoryPoint historyPoint) {
        var entity = TelemetryHistoryPointPersistenceAssembler.toPersistenceFromDomain(historyPoint);
        var saved = persistenceRepository.save(entity);

        return TelemetryHistoryPointPersistenceAssembler.toDomainFromPersistence(saved);
    }

    @Override
    public boolean existsById(Long id) {
        return persistenceRepository.existsById(id);
    }

    @Override
    public Optional<Instant> findLastReceivedAt(Long equipmentId) {
        return Optional.ofNullable(persistenceRepository.findLastCreatedAtByEquipmentId(equipmentId)).map(Date::toInstant);
    }
}
