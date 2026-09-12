package com.iotech.qualitrack.platform.laboratory.infrastructure.persistence.jpa.adapters;

import com.iotech.qualitrack.platform.laboratory.domain.model.aggregates.RawMaterial;
import com.iotech.qualitrack.platform.laboratory.domain.repositories.RawMaterialRepository;
import com.iotech.qualitrack.platform.laboratory.infrastructure.persistence.jpa.assemblers.RawMaterialPersistenceAssembler;
import com.iotech.qualitrack.platform.laboratory.infrastructure.persistence.jpa.repositories.RawMaterialPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA adapter implementation for the {@link RawMaterialRepository} port.
 *
 * <p>Translates domain-level requests for raw materials into
 * Spring Data JPA database operations.</p>
 */
@Repository
public class RawMaterialRepositoryImpl implements RawMaterialRepository {

    private final RawMaterialPersistenceRepository repository;

    public RawMaterialRepositoryImpl(RawMaterialPersistenceRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<RawMaterial> findById(Long id) {
        return repository.findById(id)
                .map(RawMaterialPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Optional<RawMaterial> findByIdForUpdate(Long id) {
        return repository.findByIdForUpdate(id).map(RawMaterialPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<RawMaterial> findAll() {
        return repository.findAll().stream()
                .map(RawMaterialPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<RawMaterial> findAllByLaboratoryId(Long laboratoryId) {
        return repository.findAllByLaboratoryId(laboratoryId).stream()
                .map(RawMaterialPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public RawMaterial save(RawMaterial rawMaterial) {

        var entityToSave = RawMaterialPersistenceAssembler.toPersistenceFromDomain(rawMaterial);

        var saved = repository.save(entityToSave);

        return RawMaterialPersistenceAssembler.toDomainFromPersistence(saved);
    }

    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    // --- NUEVO MÉTODO AÑADIDO PARA CUMPLIR CON LA INTERFAZ ---
    @Override
    public boolean existsByCode(String code) {
        return repository.existsByCode(code);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
