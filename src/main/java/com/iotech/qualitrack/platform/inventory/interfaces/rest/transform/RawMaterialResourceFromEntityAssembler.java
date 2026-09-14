package com.iotech.qualitrack.platform.inventory.interfaces.rest.transform;
import com.iotech.qualitrack.platform.inventory.domain.model.valueobjects.MaterialStockSummary;
import com.iotech.qualitrack.platform.inventory.interfaces.rest.resources.RawMaterialResource;
public final class RawMaterialResourceFromEntityAssembler {
    private RawMaterialResourceFromEntityAssembler() { }
    public static RawMaterialResource toResourceFromEntity(MaterialStockSummary material) {
        return new RawMaterialResource(material.id(), material.laboratoryId(), material.code(), material.name(), material.unit(),
            material.minimumStock(), material.usableStock(), material.physicalStock(), material.legacyId());
    }
}
