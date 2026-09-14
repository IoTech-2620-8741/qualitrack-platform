package com.iotech.qualitrack.platform.batch.application.acl;

import com.iotech.qualitrack.platform.batch.domain.repositories.BatchRepository;
import com.iotech.qualitrack.platform.shared.application.result.ApplicationException;
import com.iotech.qualitrack.platform.shared.application.result.ApplicationError;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

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
    private final BatchRepository repository;

    public BatchContextFacadeImpl(BatchQueryService batchQueryService,
            BatchRepository repository) {
        this.batchQueryService = batchQueryService;
        this.repository = repository;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void requireConsumable(Long batchId, Long laboratoryId) {
        var batch = repository.findByIdForUpdate(batchId).filter(value -> laboratoryId.equals(value.getLabId()))
            .orElseThrow(() -> new ApplicationException(
                ApplicationError.notFound("Batch", batchId)));
        if (batch.getStatus() == BatchStatus.RELEASED || batch.getStatus() == BatchStatus.REJECTED)
            throw new ApplicationException(
                ApplicationError.conflict("Batch", "Closed product batches cannot consume materials"));
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
