package com.iotech.qualitrack.platform.batch.application.internal.outboundservices.acl;

import com.iotech.qualitrack.platform.inventory.interfaces.acl.InventoryContextFacade;
import org.springframework.stereotype.Service;

/**
 * Outbound ACL used by Product Batch Management to validate raw material references owned by Inventory.
 */
@Service
public class BatchExternalInventoryService {

    private final InventoryContextFacade inventoryContextFacade;

    public BatchExternalInventoryService(InventoryContextFacade inventoryContextFacade) {
        this.inventoryContextFacade = inventoryContextFacade;
    }

    /**
     * Checks that the raw material belongs to the laboratory and is kept in the environment.
     */
    public boolean isRawMaterialInEnvironment(Long laboratoryId, Long environmentId, Long rawMaterialId) {
        return inventoryContextFacade.isRawMaterialInEnvironment(laboratoryId, environmentId, rawMaterialId);
    }
}
