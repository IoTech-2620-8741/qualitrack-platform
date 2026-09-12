package com.iotech.qualitrack.platform.laboratory.infrastructure.persistence.jpa.repositories;

import com.iotech.qualitrack.platform.laboratory.infrastructure.persistence.jpa.entities.LaboratoryPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for {@link LaboratoryPersistenceEntity}.
 *
 * <p>Handles database operations for the laboratory table using Long as the primary key type.
 * Custom query methods are provided for unique business identifiers like name and RUC.</p>
 */
@Repository
public interface LaboratoryPersistenceRepository extends JpaRepository<LaboratoryPersistenceEntity, Long> {

    /**
     * Finds a laboratory by its official name.
     */
    Optional<LaboratoryPersistenceEntity> findByName(String name);

    /**
     * Finds a laboratory by its unique tax identification number (RUC).
     */
    Optional<LaboratoryPersistenceEntity> findByRuc(String ruc);

    /**
     * Checks if a laboratory with the given name already exists.
     */
    boolean existsByName(String name);

    /**
     * Checks if a laboratory with the given RUC already exists.
     */
    boolean existsByRuc(String ruc);
}