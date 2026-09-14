package com.iotech.qualitrack.platform.inventory.interfaces.rest.transform;

import com.iotech.qualitrack.platform.inventory.interfaces.acl.InventoryContextFacade.AvailableReceipt;
import com.iotech.qualitrack.platform.inventory.interfaces.rest.resources.AvailableReceiptResource;

public final class AvailableReceiptResourceFromEntityAssembler {
    private AvailableReceiptResourceFromEntityAssembler() { }

    public static AvailableReceiptResource toResourceFromEntity(AvailableReceipt value) {
        return new AvailableReceiptResource(value.id(), value.rawMaterialId(), value.batchNumber(), value.unit(), value.availableAmount(), value.expiresOn());
    }
}
