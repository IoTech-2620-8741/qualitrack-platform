package com.iotech.qualitrack.platform.laboratory.infrastructure.persistence.jpa.adapters;

import com.iotech.qualitrack.platform.laboratory.domain.model.aggregates.PharmaceuticalProduct;
import com.iotech.qualitrack.platform.laboratory.domain.repositories.ProductRepository;
import com.iotech.qualitrack.platform.laboratory.infrastructure.persistence.jpa.assemblers.ProductPersistenceAssembler;
import com.iotech.qualitrack.platform.laboratory.infrastructure.persistence.jpa.repositories.ProductPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA adapter implementation for the {@link ProductRepository} port.
 *
 * <p>Translates domain-level requests for pharmaceutical products into
 * Spring Data JPA database operations.</p>
 */
@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductPersistenceRepository repository;

    public ProductRepositoryImpl(ProductPersistenceRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<PharmaceuticalProduct> findById(Long id) {
        return repository.findById(id)
                .map(ProductPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<PharmaceuticalProduct> findAll() {
        return repository.findAll().stream()
                .map(ProductPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<PharmaceuticalProduct> findAllByLaboratoryId(Long laboratoryId) {
        return repository.findAllByLaboratoryId(laboratoryId).stream()
                .map(ProductPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public Optional<PharmaceuticalProduct> findByNameAndLaboratoryId(String name, Long laboratoryId) {
        return repository.findByNameAndLaboratoryId(name, laboratoryId)
                .map(ProductPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public PharmaceuticalProduct save(PharmaceuticalProduct product) {
        var entityToSave = ProductPersistenceAssembler.toPersistenceFromDomain(product);

        var saved = repository.save(entityToSave);

        return ProductPersistenceAssembler.toDomainFromPersistence(saved);
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