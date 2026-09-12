package com.iotech.qualitrack.platform.batch.domain.repositories;

import com.iotech.qualitrack.platform.batch.domain.model.aggregates.Batch;
import com.iotech.qualitrack.platform.batch.domain.model.valueobjects.BatchStatus;

import java.util.List;
import java.util.Optional;

/**
 * Batch repository port.
 *
 * <p>Handles the persistence contract for the Batch aggregate.</p>
 */
public interface BatchRepository {

    Optional<Batch> findById(Long id);

    List<Batch> findAll();

    List<Batch> findAllByLabId(Long labId);

    List<Batch> findAllByStatus(BatchStatus status);

    Optional<Batch> findByBatchNumber(String batchNumber);

    Batch save(Batch batch);

    boolean existsById(Long id);

    boolean existsByBatchNumber(String batchNumber);

    void deleteById(Long id);
}