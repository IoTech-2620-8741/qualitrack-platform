package com.iotech.qualitrack.platform.inventory.interfaces.rest.transform;

import com.iotech.qualitrack.platform.inventory.domain.model.valueobjects.ReceiptConsumption;
import com.iotech.qualitrack.platform.inventory.interfaces.rest.resources.ReceiptConsumptionResource;

public final class ReceiptConsumptionResourceFromEntityAssembler {
    private ReceiptConsumptionResourceFromEntityAssembler() { }

    public static ReceiptConsumptionResource toResourceFromEntity(ReceiptConsumption value) {
        return new ReceiptConsumptionResource(value.rawMaterialBatchId(), value.productBatchId(), value.amountUsed(), value.unit(), value.stockBefore(), value.stockAfter(), value.operationId());
    }
}
