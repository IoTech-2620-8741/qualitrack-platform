package com.iotech.qualitrack.platform.batch.interfaces.rest.transform;

import com.iotech.qualitrack.platform.batch.domain.model.entities.RawMaterialUsage;
import com.iotech.qualitrack.platform.batch.interfaces.rest.resources.RawMaterialUsageResource;

/**
 * Assembler to convert a RawMaterialUsage entity to a RawMaterialUsageResource.
 */
public class RawMaterialUsageResourceFromEntityAssembler {

    /**
     * Converts a RawMaterialUsage entity to a RawMaterialUsageResource.
     *
     * @param entity The {@link RawMaterialUsage} entity to convert.
     * @return The {@link RawMaterialUsageResource} resource that results from the conversion.
     */
    public static RawMaterialUsageResource toResourceFromEntity(RawMaterialUsage entity) {
        return new RawMaterialUsageResource(
                entity.getId(),
                entity.getBatchId(),
                entity.getRawMaterialId(),
                entity.getRawMaterialName(),
                entity.getQuantityUsed(),
                entity.getUnit(),
                entity.getUsageDate(),
                entity.getStockBefore(),
                entity.getStockAfter(),
                entity.getInventoryReceiptId()
        );
    }
}
