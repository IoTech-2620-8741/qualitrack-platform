package com.iotech.qualitrack.platform.batch.interfaces.rest.transform;

import com.iotech.qualitrack.platform.batch.domain.model.commands.LinkRawMaterialCommand;
import com.iotech.qualitrack.platform.batch.interfaces.rest.resources.LinkRawMaterialResource;

/**
 * Assembler to convert a LinkRawMaterialResource to a LinkRawMaterialCommand.
 */
public class LinkRawMaterialCommandFromResourceAssembler {

    /**
     * Converts a LinkRawMaterialResource to a LinkRawMaterialCommand.
     *
     * @param batchId The batch numeric identifier from the route.
     * @param resource The {@link LinkRawMaterialResource} resource to convert.
     * @return The {@link LinkRawMaterialCommand} command that results from the conversion.
     */
    public static LinkRawMaterialCommand toCommandFromResource(Long batchId, LinkRawMaterialResource resource) {
        return new LinkRawMaterialCommand(
                batchId,
                resource.rawMaterialId(),
                resource.quantityUsed(),
                resource.unit()
        );
    }
}
