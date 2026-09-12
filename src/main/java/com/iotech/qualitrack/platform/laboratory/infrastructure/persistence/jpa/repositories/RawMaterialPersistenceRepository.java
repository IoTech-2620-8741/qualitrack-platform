package com.iotech.qualitrack.platform.laboratory.infrastructure.persistence.jpa.repositories;

import com.iotech.qualitrack.platform.laboratory.infrastructure.persistence.jpa.entities.RawMaterialPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for {@link RawMaterialPersistenceEntity}.
 *
 * <p>Handles database operations for the raw materials table using Long as the primary key.
 * Provides custom queries for fetching materials by laboratory and validating unique tracking codes.</p>
 */
@Repository
public interface RawMaterialPersistenceRepository extends JpaRepository<RawMaterialPersistenceEntity, Long> {

    @org.springframework.data.jpa.repository.Lock(jakarta.persistence.LockModeType.PESSIMISTIC_WRITE)
    @org.springframework.data.jpa.repository.Query("select m from RawMaterialPersistenceEntity m where m.id = :id")
    Optional<RawMaterialPersistenceEntity> findByIdForUpdate(@org.springframework.data.repository.query.Param("id") Long id);

    /**
     * Finds all raw materials associated with a specific laboratory.
     * @param laboratoryId The numeric ID of the laboratory.
     */
    List<RawMaterialPersistenceEntity> findAllByLaboratoryId(Long laboratoryId);

    /**
     * Finds a raw material by its unique internal catalog code.
     * @param code The unique internal code.
     */
    Optional<RawMaterialPersistenceEntity> findByCode(String code);

    /**
     * Checks if a raw material with the given internal catalog code already exists.
     * Useful for Fail-Fast validation during raw material registration.
     */
    boolean existsByCode(String code);
}
