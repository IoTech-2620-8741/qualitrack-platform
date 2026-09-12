package com.iotech.qualitrack.platform.batch.interfaces.rest.transform;

import com.iotech.qualitrack.platform.batch.domain.model.commands.RejectBatchCommand;
import com.iotech.qualitrack.platform.batch.interfaces.rest.resources.RejectBatchResource;

/**
 * Assembler to convert a RejectBatchResource to a RejectBatchCommand.
 */
public class RejectBatchCommandFromResourceAssembler {

    /**
     * Converts a RejectBatchResource to a RejectBatchCommand.
     *
     * @param batchId The batch numeric identifier from the route.
     * @param resource The {@link RejectBatchResource} resource to convert.
     * @return The {@link RejectBatchCommand} command that results from the conversion.
     */
    public static RejectBatchCommand toCommandFromResource(Long batchId, RejectBatchResource resource) {
        return new RejectBatchCommand(
                batchId,
                resource.rejectionDate(),
                resource.reason()
        );
    }
}