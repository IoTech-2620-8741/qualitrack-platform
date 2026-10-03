package com.iotech.qualitrack.platform.batch.application.internal.queryservices;

import com.iotech.qualitrack.platform.batch.application.queryservices.BatchQueryService;
import com.iotech.qualitrack.platform.batch.domain.model.aggregates.Batch;
import com.iotech.qualitrack.platform.batch.domain.model.queries.GetBatchByIdQuery;
import com.iotech.qualitrack.platform.batch.domain.model.queries.GetBatchesByLabIdQuery;
import com.iotech.qualitrack.platform.batch.domain.model.queries.GetBatchesByProductQuery;
import com.iotech.qualitrack.platform.batch.domain.model.queries.GetProductBatchQuery;
import com.iotech.qualitrack.platform.batch.domain.repositories.BatchRepository;
import com.iotech.qualitrack.platform.batch.domain.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Reads product batches, always checking that they belong to the requested product hierarchy.
 */
@Service
public class BatchQueryServiceImpl implements BatchQueryService {

    private final BatchRepository batchRepository;
    private final ProductRepository productRepository;

    public BatchQueryServiceImpl(BatchRepository batchRepository, ProductRepository productRepository) {
        this.batchRepository = batchRepository;
        this.productRepository = productRepository;
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
    public Optional<List<Batch>> handle(GetBatchesByProductQuery query) {
        return productRepository.findById(query.productId())
                .filter(product -> product.belongsTo(query.laboratoryId(), query.environmentId()))
                .map(product -> batchRepository.findAllByProductId(product.getId()).stream()
                        .filter(batch -> batch.belongsTo(query.laboratoryId(), query.environmentId(), query.productId()))
                        .toList());
    }

    @Override
    public Optional<Batch> handle(GetProductBatchQuery query) {
        return batchRepository.findById(query.batchId())
                .filter(batch -> batch.belongsTo(query.laboratoryId(), query.environmentId(), query.productId()));
    }
}
