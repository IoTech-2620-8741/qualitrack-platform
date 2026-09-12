package com.iotech.qualitrack.platform.equipment.infrastructure.persistence.jpa.adapters;

import com.iotech.qualitrack.platform.equipment.domain.model.entities.BpmParameterConfig;
import com.iotech.qualitrack.platform.equipment.domain.repositories.BpmParameterConfigRepository;
import com.iotech.qualitrack.platform.equipment.infrastructure.persistence.jpa.assemblers.BpmParameterConfigPersistenceAssembler;
import com.iotech.qualitrack.platform.equipment.infrastructure.persistence.jpa.repositories.BpmParameterConfigPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA adapter implementation for the {@link BpmParameterConfigRepository} port.
 */
@Repository
public class BpmParameterConfigRepositoryImpl implements BpmParameterConfigRepository {

    private final BpmParameterConfigPersistenceRepository repository;

    public BpmParameterConfigRepositoryImpl(BpmParameterConfigPersistenceRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<BpmParameterConfig> findById(Long id) {
        return repository.findById(id)
                .map(BpmParameterConfigPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<BpmParameterConfig> findAll() {
        return repository.findAll().stream()
                .map(BpmParameterConfigPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<BpmParameterConfig> findAllByEquipmentId(Long equipmentId) {
        return repository.findAllByEquipmentId(equipmentId).stream()
                .map(BpmParameterConfigPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public Optional<BpmParameterConfig> findByEquipmentIdAndParameterName(Long equipmentId, String parameterName) {
        return repository.findByEquipmentIdAndParameterName(equipmentId, parameterName)
                .map(BpmParameterConfigPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public BpmParameterConfig save(BpmParameterConfig bpmParameterConfig) {
        var entityToSave = BpmParameterConfigPersistenceAssembler.toPersistenceFromDomain(bpmParameterConfig);
        var saved = repository.save(entityToSave);
        return BpmParameterConfigPersistenceAssembler.toDomainFromPersistence(saved);
    }

    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}