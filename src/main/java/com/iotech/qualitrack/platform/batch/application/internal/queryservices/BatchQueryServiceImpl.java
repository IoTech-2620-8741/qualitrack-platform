package com.iotech.qualitrack.platform.batch.application.internal.queryservices;

import com.iotech.qualitrack.platform.batch.application.queryservices.BatchQueryService;
import com.iotech.qualitrack.platform.batch.domain.model.aggregates.Batch;
import com.iotech.qualitrack.platform.batch.domain.model.queries.GetBatchByIdQuery;
import com.iotech.qualitrack.platform.batch.domain.model.queries.GetBatchesByLabIdQuery;
import com.iotech.qualitrack.platform.batch.domain.model.queries.GetBatchesByStatusQuery;
import com.iotech.qualitrack.platform.batch.domain.repositories.BatchRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Application service implementation that resolves batch read queries.
 */
@Service
public class BatchQueryServiceImpl implements BatchQueryService {

    private final BatchRepository batchRepository;

    public BatchQueryServiceImpl(BatchRepository batchRepository) {
        this.batchRepository = batchRepository;
    }

    @Override
    public Optional<Batch> handle(GetBatchByIdQuery query) {
        return batchRepository.findById(query.batchId());
    }

    @Override
    public List<Batch> handle(GetBatchesByLabIdQuery query) {
        return batchRepository.findAllByLabId(query.labId());
    }

    @Override
    public List<Batch> handle(GetBatchesByStatusQuery query) {
        return batchRepository.findAllByStatus(query.status());
    }
}