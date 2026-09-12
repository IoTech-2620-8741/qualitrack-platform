package com.iotech.qualitrack.platform.laboratory.domain.repositories;

import com.iotech.qualitrack.platform.laboratory.domain.model.aggregates.RawMaterial;

import java.util.List;
import java.util.Optional;

/**
 * Raw material repository port.
 */
public interface RawMaterialRepository {
    Optional<RawMaterial> findById(Long id);

    Optional<RawMaterial> findByIdForUpdate(Long id);

    List<RawMaterial> findAll();

    List<RawMaterial> findAllByLaboratoryId(Long laboratoryId);

    RawMaterial save(RawMaterial rawMaterial);

    boolean existsById(Long id);

    boolean existsByCode(String code);

    void deleteById(Long id);
}
