package com.iotech.qualitrack.platform.inventory.application.acl;

import com.iotech.qualitrack.platform.inventory.application.commandservices.InventoryCommandService;
import com.iotech.qualitrack.platform.inventory.application.queryservices.InventoryQueryService;
import com.iotech.qualitrack.platform.inventory.domain.model.commands.ConsumeRawMaterialBatchCommand;
import com.iotech.qualitrack.platform.inventory.domain.model.queries.GetEnvironmentRawMaterialByIdQuery;
import com.iotech.qualitrack.platform.inventory.domain.model.valueobjects.ReceiptConsumption;
import com.iotech.qualitrack.platform.inventory.interfaces.acl.InventoryContextFacade;
import com.iotech.qualitrack.platform.shared.application.result.ApplicationError;
import com.iotech.qualitrack.platform.shared.application.result.ApplicationException;
import com.iotech.qualitrack.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class InventoryContextFacadeImpl implements InventoryContextFacade {
    private final InventoryCommandService commands;
    private final InventoryQueryService queries;

    public InventoryContextFacadeImpl(InventoryCommandService commands, InventoryQueryService queries) {
        this.commands = commands;
        this.queries = queries;
    }

    public Consumption consume(ConsumptionRequest request) {
        var result = commands.handle(new ConsumeRawMaterialBatchCommand(request.laboratoryId(), request.rawMaterialBatchId(),
                request.productBatchId(), request.amountUsed(), request.unit(), request.operationId()));
        return switch (result) {
            case Result.Success<ReceiptConsumption, ApplicationError> success -> {
                var value = success.value();
                yield new Consumption(value.rawMaterialBatchId(), value.productBatchId(), value.amountUsed(), value.unit(),
                        value.stockBefore(), value.stockAfter(), value.operationId());
            }
            case Result.Failure<ReceiptConsumption, ApplicationError> failure -> throw new ApplicationException(failure.error());
        };
    }

    public boolean isRawMaterialInEnvironment(Long laboratoryId, Long environmentId, Long rawMaterialId) {
        return queries.handle(new GetEnvironmentRawMaterialByIdQuery(laboratoryId, environmentId, rawMaterialId)).isPresent();
    }
}
