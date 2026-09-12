package com.iotech.qualitrack.platform.ca.infrastructure.persistence.jpa.adapters;

import com.iotech.qualitrack.platform.ca.domain.model.entities.ComplianceEvent;
import com.iotech.qualitrack.platform.ca.domain.model.valueobjects.ComplianceEventType;
import com.iotech.qualitrack.platform.ca.domain.repositories.ComplianceEventRepository;
import com.iotech.qualitrack.platform.ca.infrastructure.persistence.jpa.assemblers.ComplianceEventPersistenceAssembler;
import com.iotech.qualitrack.platform.ca.infrastructure.persistence.jpa.repositories.ComplianceEventPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA adapter implementation for the {@link ComplianceEventRepository} port.
 *
 * <p>Translates domain-level requests for compliance events into
 * Spring Data JPA database operations.</p>
 */
@Repository
public class ComplianceEventRepositoryImpl implements ComplianceEventRepository {

    private final ComplianceEventPersistenceRepository repository;

    public ComplianceEventRepositoryImpl(ComplianceEventPersistenceRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<ComplianceEvent> findById(Long id) {
        return repository.findById(id)
                .map(ComplianceEventPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<ComplianceEvent> findAll() {
        return repository.findAll().stream()
                .map(ComplianceEventPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<ComplianceEvent> findAllByRelatedEntityId(Long relatedEntityId) {
        return repository.findAllByRelatedEntityId(relatedEntityId).stream()
                .map(ComplianceEventPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<ComplianceEvent> findAllByEventType(ComplianceEventType eventType) {
        return repository.findAllByEventType(eventType).stream()
                .map(ComplianceEventPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public ComplianceEvent save(ComplianceEvent complianceEvent) {
        var entityToSave = ComplianceEventPersistenceAssembler.toPersistenceFromDomain(complianceEvent);

        var saved = repository.save(entityToSave);

        return ComplianceEventPersistenceAssembler.toDomainFromPersistence(saved);
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