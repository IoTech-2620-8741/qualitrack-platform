package com.iotech.qualitrack.platform.laboratory.domain.repositories;

import com.iotech.qualitrack.platform.laboratory.domain.model.aggregates.Laboratory;
import com.iotech.qualitrack.platform.laboratory.domain.model.valueobjects.LaboratoryName;

import java.util.List;
import java.util.Optional;

/**
 * Repository port for the Laboratory aggregate.
 */
public interface LaboratoryRepository {

    Optional<Laboratory> findById(Long id);

    Optional<Laboratory> findByName(LaboratoryName name);

    List<Laboratory> findAll();

    Laboratory save(Laboratory laboratory);

    boolean existsById(Long id);

    boolean existsByName(LaboratoryName name);

    boolean existsByRuc(String ruc);

    void deleteById(Long id);
}