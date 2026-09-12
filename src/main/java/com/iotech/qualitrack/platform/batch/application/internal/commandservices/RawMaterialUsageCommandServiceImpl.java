package com.iotech.qualitrack.platform.batch.application.internal.commandservices;

import com.iotech.qualitrack.platform.batch.application.commandservices.RawMaterialUsageCommandService;
import com.iotech.qualitrack.platform.batch.domain.model.commands.LinkRawMaterialCommand;
import com.iotech.qualitrack.platform.batch.domain.model.entities.RawMaterialUsage;
import com.iotech.qualitrack.platform.batch.domain.model.events.RawMaterialLinkedToBatchEvent;
import com.iotech.qualitrack.platform.batch.domain.repositories.BatchRepository;
import com.iotech.qualitrack.platform.batch.domain.repositories.RawMaterialUsageRepository;
import com.iotech.qualitrack.platform.laboratory.interfaces.acl.RawMaterialStockFacade;
import com.iotech.qualitrack.platform.shared.application.result.ApplicationError;
import com.iotech.qualitrack.platform.shared.application.result.ApplicationException;
import com.iotech.qualitrack.platform.shared.application.result.Result;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;

/** The inventory decrement, usage record and audit events commit or roll back together. */
@Service
public class RawMaterialUsageCommandServiceImpl implements RawMaterialUsageCommandService {
    private final RawMaterialUsageRepository usages;
    private final BatchRepository batches;
    private final RawMaterialStockFacade inventory;
    private final ApplicationEventPublisher events;

    public RawMaterialUsageCommandServiceImpl(RawMaterialUsageRepository usages, BatchRepository batches,
                                              RawMaterialStockFacade inventory, ApplicationEventPublisher events) {
        this.usages = usages;
        this.batches = batches;
        this.inventory = inventory;
        this.events = events;
    }

    @Override
    @Transactional
    public Result<Long, ApplicationError> handle(LinkRawMaterialCommand command) {
        var batch = batches.findById(command.batchId()).orElseThrow(() ->
                new ApplicationException(ApplicationError.notFound("Batch", command.batchId())));
        var consumed = inventory.consume(command.rawMaterialId(), batch.getLabId(),
                BigDecimal.valueOf(command.quantityUsed()), command.unit());
        var usage = new RawMaterialUsage(command, consumed.materialName(), consumed.unit(), Instant.now().toString());
        usage.recordStockChange(consumed.stockBefore(), consumed.stockAfter());
        var saved = usages.save(usage);
        events.publishEvent(RawMaterialLinkedToBatchEvent.from(saved));
        return Result.success(saved.getId());
    }
}
