package com.iotech.qualitrack.platform.batch.interfaces.rest.transform;

import com.iotech.qualitrack.platform.batch.domain.model.commands.ReleaseBatchCommand;
import com.iotech.qualitrack.platform.batch.interfaces.rest.resources.ReleaseBatchResource;

/**
 * Assembler to convert a ReleaseBatchResource to a ReleaseBatchCommand.
 */
public class ReleaseBatchCommandFromResourceAssembler {

    /**
     * Converts a ReleaseBatchResource to a ReleaseBatchCommand.
     *
     * @param batchId The batch numeric identifier from the route.
     * @param resource The {@link ReleaseBatchResource} resource to convert.
     * @return The {@link ReleaseBatchCommand} command that results from the conversion.
     */
    public static ReleaseBatchCommand toCommandFromResource(Long batchId, ReleaseBatchResource resource) {
        return new ReleaseBatchCommand(
                batchId,
                resource.releaseDate(),
                resource.notes()
        );
    }
}