package com.iotech.qualitrack.platform.tracking.infrastructure.persistence.jpa.adapters;

import com.iotech.qualitrack.platform.tracking.domain.model.entities.Measurement;
import com.iotech.qualitrack.platform.tracking.domain.repositories.MeasurementRepository;
import com.iotech.qualitrack.platform.tracking.infrastructure.persistence.jpa.assemblers.MeasurementPersistenceAssembler;
import com.iotech.qualitrack.platform.tracking.infrastructure.persistence.jpa.repositories.MeasurementPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA adapter implementation for the {@link MeasurementRepository} domain port.
 */
@Repository
public class MeasurementRepositoryImpl implements MeasurementRepository {

    private static final int DEFAULT_LATEST_LIMIT = 50;

    private final MeasurementPersistenceRepository persistenceRepository;

    public MeasurementRepositoryImpl(MeasurementPersistenceRepository persistenceRepository) {
        this.persistenceRepository = persistenceRepository;
    }

    @Override
    public Optional<Measurement> findById(Long id) {
        return persistenceRepository.findById(id)
                .map(MeasurementPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Measurement> findLatest() {
        return persistenceRepository.findAllByOrderByTimestampDesc().stream()
                .limit(DEFAULT_LATEST_LIMIT)
                .map(MeasurementPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<Measurement> findLatestByEquipmentId(Long equipmentId) {
        return persistenceRepository.findAllByEquipmentIdOrderByTimestampDesc(equipmentId).stream()
                .limit(DEFAULT_LATEST_LIMIT)
                .map(MeasurementPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public Measurement save(Measurement measurement) {
        var entity = MeasurementPersistenceAssembler.toPersistenceFromDomain(measurement);
        var saved = persistenceRepository.save(entity);

        return MeasurementPersistenceAssembler.toDomainFromPersistence(saved);
    }

    @Override
    public boolean existsById(Long id) {
        return persistenceRepository.existsById(id);
    }
}