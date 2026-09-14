package com.iotech.qualitrack.platform.batch.infrastructure.persistence.jpa.repositories;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import com.iotech.qualitrack.platform.batch.domain.model.valueobjects.BatchStatus;
import com.iotech.qualitrack.platform.batch.infrastructure.persistence.jpa.entities.BatchPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for {@link BatchPersistenceEntity}.
 *
 * <p>Handles database operations for the batches table using Long as the primary key.
 * Provides custom queries for fetching batches by laboratory, status, and batch number.</p>
 */
@Repository
public interface BatchPersistenceRepository extends JpaRepository<BatchPersistenceEntity, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select b from BatchPersistenceEntity b where b.id = :id")
    Optional<BatchPersistenceEntity> findByIdForUpdate(Long id);

    /**
     * Finds all batches associated with a specific laboratory.
     * @param labId The numeric ID of the laboratory.
     */
    List<BatchPersistenceEntity> findAllByLabId(Long labId);

    /**
     * Finds all batches with a specific lifecycle status.
     * @param status The batch lifecycle status.
     */
    List<BatchPersistenceEntity> findAllByStatus(BatchStatus status);

    /**
     * Finds a batch by its unique traceability code.
     * @param batchNumber The unique batch number.
     */
    Optional<BatchPersistenceEntity> findByBatchNumber(String batchNumber);

    /**
     * Checks if a batch with the given batch number already exists.
     * Useful for Fail-Fast validation during batch creation.
     */
    boolean existsByBatchNumber(String batchNumber);
}
