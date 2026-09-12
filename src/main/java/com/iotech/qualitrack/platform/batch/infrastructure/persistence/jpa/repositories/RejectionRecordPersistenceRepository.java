package com.iotech.qualitrack.platform.batch.infrastructure.persistence.jpa.repositories;

import com.iotech.qualitrack.platform.batch.infrastructure.persistence.jpa.entities.RejectionRecordPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for {@link RejectionRecordPersistenceEntity}.
 *
 * <p>Handles database operations for rejection records generated during batch rejection.</p>
 */
@Repository
public interface RejectionRecordPersistenceRepository extends JpaRepository<RejectionRecordPersistenceEntity, Long> {

    /**
     * Finds the rejection record associated with a specific batch.
     * @param batchId The numeric ID of the rejected batch.
     */
    Optional<RejectionRecordPersistenceEntity> findByBatchId(Long batchId);
}