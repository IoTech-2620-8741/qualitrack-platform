package com.iotech.qualitrack.platform.batch.application.internal.commandservices;

import com.iotech.qualitrack.platform.batch.application.commandservices.BatchCommandService;
import com.iotech.qualitrack.platform.batch.domain.model.aggregates.Batch;
import com.iotech.qualitrack.platform.batch.domain.model.commands.CreateBatchCommand;
import com.iotech.qualitrack.platform.batch.domain.model.commands.RejectBatchCommand;
import com.iotech.qualitrack.platform.batch.domain.model.commands.ReleaseBatchCommand;
import com.iotech.qualitrack.platform.batch.domain.model.entities.DigitalSignature;
import com.iotech.qualitrack.platform.batch.domain.model.entities.RejectionRecord;
import com.iotech.qualitrack.platform.batch.domain.model.events.BatchCreatedEvent;
import com.iotech.qualitrack.platform.batch.domain.model.events.BatchRejectedEvent;
import com.iotech.qualitrack.platform.batch.domain.model.events.BatchReleasedEvent;
import com.iotech.qualitrack.platform.batch.domain.model.valueobjects.BatchRejection;
import com.iotech.qualitrack.platform.batch.domain.model.valueobjects.BatchRelease;
import com.iotech.qualitrack.platform.batch.domain.repositories.BatchEvidenceRepository;
import com.iotech.qualitrack.platform.batch.domain.repositories.BatchRepository;
import com.iotech.qualitrack.platform.batch.domain.repositories.ProductRepository;
import com.iotech.qualitrack.platform.shared.application.result.ApplicationError;
import com.iotech.qualitrack.platform.shared.application.result.Result;
import com.iotech.qualitrack.platform.shared.application.security.CurrentUser;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

/**
 * Registers product batches and records their release or rejection.
 */
@Service
public class BatchCommandServiceImpl implements BatchCommandService {

    private final BatchRepository batchRepository;
    private final ProductRepository productRepository;
    private final BatchEvidenceRepository evidenceRepository;
    private final CurrentUser currentUser;
    private final ApplicationEventPublisher eventPublisher;

    public BatchCommandServiceImpl(BatchRepository batchRepository, ProductRepository productRepository,
                                   BatchEvidenceRepository evidenceRepository, CurrentUser currentUser,
                                   ApplicationEventPublisher eventPublisher) {
        this.batchRepository = batchRepository;
        this.productRepository = productRepository;
        this.evidenceRepository = evidenceRepository;
        this.currentUser = currentUser;
        this.eventPublisher = eventPublisher;
    }

    @Override
    @Transactional
    public Result<Batch, ApplicationError> handle(CreateBatchCommand command) {
        var product = productRepository.findById(command.productId())
                .filter(value -> value.belongsTo(command.laboratoryId(), command.environmentId()));
        if (product.isEmpty()) {
            return Result.failure(ApplicationError.notFound("PharmaceuticalProduct", command.productId()));
        }
        if (batchRepository.existsByLabIdAndBatchNumber(command.laboratoryId(), command.batchNumber())) {
            return Result.failure(ApplicationError.conflict("Batch",
                    "Batch with number '%s' already exists in this laboratory".formatted(command.batchNumber())));
        }
        var batch = batchRepository.save(new Batch(command, product.get()));
        eventPublisher.publishEvent(BatchCreatedEvent.from(batch));
        return Result.success(batch);
    }

    @Override
    @Transactional
    public Result<BatchRelease, ApplicationError> handle(ReleaseBatchCommand command) {
        var batch = lockedBatch(command.laboratoryId(), command.environmentId(), command.productId(), command.batchId());
        if (batch.isEmpty()) return Result.failure(ApplicationError.notFound("Batch", command.batchId()));
        try {
            batch.get().release(command);
        } catch (IllegalStateException exception) {
            return Result.failure(ApplicationError.conflict("Batch", exception.getMessage()));
        }
        var released = batchRepository.save(batch.get());
        var signature = evidenceRepository.saveSignature(
                DigitalSignature.sign(released, currentUser.userId(), Instant.now().truncatedTo(ChronoUnit.SECONDS)));
        eventPublisher.publishEvent(BatchReleasedEvent.from(released));
        return Result.success(new BatchRelease(released, signature));
    }

    @Override
    @Transactional
    public Result<BatchRejection, ApplicationError> handle(RejectBatchCommand command) {
        var batch = lockedBatch(command.laboratoryId(), command.environmentId(), command.productId(), command.batchId());
        if (batch.isEmpty()) return Result.failure(ApplicationError.notFound("Batch", command.batchId()));
        try {
            batch.get().reject(command);
        } catch (IllegalStateException exception) {
            return Result.failure(ApplicationError.conflict("Batch", exception.getMessage()));
        }
        var rejected = batchRepository.save(batch.get());
        var record = evidenceRepository.saveRejection(new RejectionRecord(command));
        eventPublisher.publishEvent(BatchRejectedEvent.from(rejected));
        return Result.success(new BatchRejection(rejected, record));
    }

    private Optional<Batch> lockedBatch(Long laboratoryId, Long environmentId, Long productId, Long batchId) {
        return batchRepository.findByIdForUpdate(batchId)
                .filter(batch -> batch.belongsTo(laboratoryId, environmentId, productId));
    }
}
