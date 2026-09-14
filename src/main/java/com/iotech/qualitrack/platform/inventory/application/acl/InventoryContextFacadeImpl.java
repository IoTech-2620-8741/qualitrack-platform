package com.iotech.qualitrack.platform.inventory.application.acl;
import com.iotech.qualitrack.platform.inventory.interfaces.acl.InventoryContextFacade;
import com.iotech.qualitrack.platform.inventory.application.commandservices.InventoryCommandService;
import com.iotech.qualitrack.platform.inventory.application.queryservices.InventoryQueryService;
import com.iotech.qualitrack.platform.inventory.domain.model.commands.ConsumeRawMaterialBatchCommand;
import com.iotech.qualitrack.platform.inventory.domain.model.queries.GetMaterialReceiptsQuery;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class InventoryContextFacadeImpl implements InventoryContextFacade {
    private final InventoryCommandService commands;
    private final InventoryQueryService queries;
    public InventoryContextFacadeImpl(InventoryCommandService commands, InventoryQueryService queries) {
        this.commands = commands; this.queries = queries;
    }
    public List<AvailableReceipt> findUsableReceipts(Long lab, Long materialId, LocalDate onDate) {
        return queries.handle(new GetMaterialReceiptsQuery(lab, materialId)).stream().filter(receipt -> receipt.isUsableOn(onDate))
            .map(receipt -> new AvailableReceipt(receipt.getId(), materialId, receipt.getBatchNumber(), receipt.getUnit(),
                receipt.getAvailableAmount(), receipt.getExpiresOn())).toList();
    }
    public Consumption consume(ConsumptionRequest request) {
        return commands.handle(new ConsumeRawMaterialBatchCommand(request.laboratoryId(), request.rawMaterialBatchId(),
            request.productBatchId(), request.amountUsed(), request.unit(), request.operationId()))
            .map(value -> new Consumption(value.rawMaterialBatchId(), value.productBatchId(), value.amountUsed(), value.unit(),
                value.stockBefore(), value.stockAfter(), value.operationId())).toOptional().orElseThrow();
    }
}
