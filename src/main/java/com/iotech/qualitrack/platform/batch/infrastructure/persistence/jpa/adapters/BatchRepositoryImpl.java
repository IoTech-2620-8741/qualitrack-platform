package com.iotech.qualitrack.platform.batch.infrastructure.persistence.jpa.adapters;

import com.iotech.qualitrack.platform.batch.domain.model.aggregates.Batch;
import com.iotech.qualitrack.platform.batch.domain.model.valueobjects.BatchStatus;
import com.iotech.qualitrack.platform.batch.domain.repositories.BatchRepository;
import com.iotech.qualitrack.platform.batch.infrastructure.persistence.jpa.assemblers.BatchPersistenceAssembler;
import com.iotech.qualitrack.platform.batch.infrastructure.persistence.jpa.repositories.BatchPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA adapter implementation for the {@link BatchRepository} port.
 *
 * <p>Translates domain-level requests for batches into
 * Spring Data JPA database operations.</p>
 */
@Repository
public class BatchRepositoryImpl implements BatchRepository {

    private final BatchPersistenceRepository repository;

    @Override
    public Optional<Batch> findByIdForUpdate(Long id) {
        return repository.findByIdForUpdate(id).map(BatchPersistenceAssembler::toDomainFromPersistence);
    }

    public BatchRepositoryImpl(BatchPersistenceRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Batch> findById(Long id) {
        return repository.findById(id)
                .map(BatchPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Batch> findAll() {
        return repository.findAll().stream()
                .map(BatchPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<Batch> findAllByLabId(Long labId) {
        return repository.findAllByLabId(labId).stream()
                .map(BatchPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<Batch> findAllByStatus(BatchStatus status) {
        return repository.findAllByStatus(status).stream()
                .map(BatchPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public Optional<Batch> findByBatchNumber(String batchNumber) {
        return repository.findByBatchNumber(batchNumber)
                .map(BatchPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Batch save(Batch batch) {
        var entityToSave = BatchPersistenceAssembler.toPersistenceFromDomain(batch);

        var saved = repository.save(entityToSave);

        return BatchPersistenceAssembler.toDomainFromPersistence(saved);
    }

    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @Override
    public boolean existsByBatchNumber(String batchNumber) {
        return repository.existsByBatchNumber(batchNumber);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
