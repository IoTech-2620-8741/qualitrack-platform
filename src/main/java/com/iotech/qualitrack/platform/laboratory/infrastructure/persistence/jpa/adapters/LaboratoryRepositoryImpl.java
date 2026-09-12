package com.iotech.qualitrack.platform.laboratory.infrastructure.persistence.jpa.adapters;

import com.iotech.qualitrack.platform.laboratory.domain.model.aggregates.Laboratory;
import com.iotech.qualitrack.platform.laboratory.domain.model.valueobjects.LaboratoryName;
import com.iotech.qualitrack.platform.laboratory.domain.repositories.LaboratoryRepository;
import com.iotech.qualitrack.platform.laboratory.infrastructure.persistence.jpa.assemblers.LaboratoryPersistenceAssembler;
import com.iotech.qualitrack.platform.laboratory.infrastructure.persistence.jpa.repositories.LaboratoryPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA adapter implementation for the {@link LaboratoryRepository} port.
 *
 * <p>Implements the domain-driven design repository pattern, translating domain
 * requests into Spring Data JPA repository calls and mapping the results back
 * to pure domain aggregates.</p>
 */
@Repository
public class LaboratoryRepositoryImpl implements LaboratoryRepository {

    private final LaboratoryPersistenceRepository repository;

    public LaboratoryRepositoryImpl(LaboratoryPersistenceRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Laboratory> findById(Long id) {
        // Now uses the native JPA findById
        return repository.findById(id)
                .map(LaboratoryPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Optional<Laboratory> findByName(LaboratoryName name) {
        return repository.findByName(name.name())
                .map(LaboratoryPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Laboratory> findAll() {
        return repository.findAll().stream()
                .map(LaboratoryPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public Laboratory save(Laboratory laboratory) {

        var entityToSave = LaboratoryPersistenceAssembler.toPersistenceFromDomain(laboratory);

        var saved = repository.save(entityToSave);

        return LaboratoryPersistenceAssembler.toDomainFromPersistence(saved);
    }

    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @Override
    public boolean existsByName(LaboratoryName name) {
        return repository.existsByName(name.name());
    }

    @Override
    public boolean existsByRuc(String ruc) {
        return repository.existsByRuc(ruc);
    }

    @Override
    public void deleteById(Long id) {
        // Native JPA delete by ID
        repository.deleteById(id);
    }
}