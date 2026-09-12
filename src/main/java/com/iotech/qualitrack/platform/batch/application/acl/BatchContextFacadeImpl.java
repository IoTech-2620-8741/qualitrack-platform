package com.iotech.qualitrack.platform.batch.application.acl;

import com.iotech.qualitrack.platform.batch.application.queryservices.BatchQueryService;
import com.iotech.qualitrack.platform.batch.domain.model.queries.GetBatchByIdQuery;
import com.iotech.qualitrack.platform.batch.domain.model.valueobjects.BatchStatus;
import com.iotech.qualitrack.platform.batch.interfaces.acl.BatchContextFacade;
import org.springframework.stereotype.Service;

/**
 * Application-layer implementation of the Batch ACL facade.
 */
@Service
public class BatchContextFacadeImpl implements BatchContextFacade {

    private final BatchQueryService batchQueryService;

    public BatchContextFacadeImpl(BatchQueryService batchQueryService) {
        this.batchQueryService = batchQueryService;
    }

    @Override
    public boolean existsBatchById(Long batchId) {
        var query = new GetBatchByIdQuery(batchId);

        var result = batchQueryService.handle(query);

        return result.isPresent();
    }

    @Override
    public boolean belongsToLaboratory(Long batchId, Long laboratoryId) {
        var query = new GetBatchByIdQuery(batchId);

        var result = batchQueryService.handle(query);

        return result
                .map(batch -> batch.getLabId().equals(laboratoryId))
                .orElse(false);
    }

    @Override
    public boolean isBatchReleased(Long batchId) {
        var query = new GetBatchByIdQuery(batchId);

        var result = batchQueryService.handle(query);

        return result
                .map(batch -> batch.getStatus() == BatchStatus.RELEASED)
                .orElse(false);
    }

    @Override
    public boolean isBatchRejected(Long batchId) {
        var query = new GetBatchByIdQuery(batchId);

        var result = batchQueryService.handle(query);

        return result
                .map(batch -> batch.getStatus() == BatchStatus.REJECTED)
                .orElse(false);
    }
}