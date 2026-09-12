package com.iotech.qualitrack.platform.laboratory.domain.repositories;

import com.iotech.qualitrack.platform.laboratory.domain.model.aggregates.PharmaceuticalProduct;

import java.util.List;
import java.util.Optional;

/**
 * Pharmaceutical product repository port.
 */
public interface ProductRepository {
    Optional<PharmaceuticalProduct> findById(Long id);

    List<PharmaceuticalProduct> findAll();

    List<PharmaceuticalProduct> findAllByLaboratoryId(Long laboratoryId);

    Optional<PharmaceuticalProduct> findByNameAndLaboratoryId(String name, Long laboratoryId);

    PharmaceuticalProduct save(PharmaceuticalProduct product);

    boolean existsById(Long id);

    boolean existsByCode(String code);

    void deleteById(Long id);
}