package com.iotech.qualitrack.platform.inventory.interfaces.rest.transform;

import com.iotech.qualitrack.platform.laboratory.interfaces.acl.LegacyInventoryFacade.Material;
import com.iotech.qualitrack.platform.inventory.interfaces.rest.resources.LegacyMaterialResource;

public final class LegacyMaterialResourceFromEntityAssembler {
    private LegacyMaterialResourceFromEntityAssembler() { }

    public static LegacyMaterialResource toResourceFromEntity(Material value) {
        return new LegacyMaterialResource(value.id(), value.code(), value.name(), value.unit(), value.minimumStock(), value.supplier(), value.batchNumber(), value.expiresOn(), value.balance());
    }
}
