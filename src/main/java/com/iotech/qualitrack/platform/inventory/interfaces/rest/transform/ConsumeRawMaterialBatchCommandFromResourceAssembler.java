package com.iotech.qualitrack.platform.inventory.interfaces.rest.transform;
import com.iotech.qualitrack.platform.inventory.domain.model.commands.ConsumeRawMaterialBatchCommand;
import com.iotech.qualitrack.platform.inventory.interfaces.rest.resources.ConsumeRawMaterialBatchResource;
public final class ConsumeRawMaterialBatchCommandFromResourceAssembler {
    private ConsumeRawMaterialBatchCommandFromResourceAssembler() { }
    public static ConsumeRawMaterialBatchCommand toCommandFromResource(Long lab, ConsumeRawMaterialBatchResource resource) {
        return new ConsumeRawMaterialBatchCommand(lab, resource.receiptId(), resource.productBatchId(), resource.amount(),
            resource.unit(), resource.operationId());
    }
}
