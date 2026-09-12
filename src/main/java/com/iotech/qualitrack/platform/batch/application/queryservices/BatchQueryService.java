package com.iotech.qualitrack.platform.batch.application.queryservices;

import com.iotech.qualitrack.platform.batch.domain.model.aggregates.Batch;
import com.iotech.qualitrack.platform.batch.domain.model.queries.GetBatchByIdQuery;
import com.iotech.qualitrack.platform.batch.domain.model.queries.GetBatchesByLabIdQuery;
import com.iotech.qualitrack.platform.batch.domain.model.queries.GetBatchesByStatusQuery;

import java.util.List;
import java.util.Optional;

/**
 * Application service contract for batch read queries.
 */
public interface BatchQueryService {

    /**
     * Handles retrieval of a batch by its unique numeric ID.
     *
     * @param query batch-id query
     * @return matching batch, if found
     * @see GetBatchByIdQuery
     */
    Optional<Batch> handle(GetBatchByIdQuery query);

    /**
     * Handles retrieval of all production batches associated with a specific laboratory.
     *
     * @param query laboratory-id query
     * @return list of batches for the given laboratory
     * @see GetBatchesByLabIdQuery
     */
    List<Batch> handle(GetBatchesByLabIdQuery query);

    /**
     * Handles retrieval of all production batches with a specific lifecycle status.
     *
     * @param query batch-status query
     * @return list of batches with the given status
     * @see GetBatchesByStatusQuery
     */
    List<Batch> handle(GetBatchesByStatusQuery query);
}