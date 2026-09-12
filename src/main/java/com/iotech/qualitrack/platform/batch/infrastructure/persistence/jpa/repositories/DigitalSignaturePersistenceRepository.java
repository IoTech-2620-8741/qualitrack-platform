package com.iotech.qualitrack.platform.batch.infrastructure.persistence.jpa.repositories;

import com.iotech.qualitrack.platform.batch.infrastructure.persistence.jpa.entities.DigitalSignaturePersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for {@link DigitalSignaturePersistenceEntity}.
 *
 * <p>Handles database operations for digital signatures generated during batch release.</p>
 */
@Repository
public interface DigitalSignaturePersistenceRepository extends JpaRepository<DigitalSignaturePersistenceEntity, Long> {

    /**
     * Finds the digital signature associated with a specific batch.
     * @param batchId The numeric ID of the released batch.
     */
    Optional<DigitalSignaturePersistenceEntity> findByBatchId(Long batchId);

    /**
     * Finds all signatures created by a specific user.
     * @param signedByUserId The numeric ID of the signing user.
     */
    List<DigitalSignaturePersistenceEntity> findAllBySignedByUserId(Long signedByUserId);
}