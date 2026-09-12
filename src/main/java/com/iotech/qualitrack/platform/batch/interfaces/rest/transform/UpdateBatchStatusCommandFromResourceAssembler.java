package com.iotech.qualitrack.platform.batch.interfaces.rest.transform;

import com.iotech.qualitrack.platform.batch.domain.model.commands.RejectBatchCommand;
import com.iotech.qualitrack.platform.batch.domain.model.commands.ReleaseBatchCommand;
import com.iotech.qualitrack.platform.batch.interfaces.rest.resources.UpdateBatchStatusResource;

/**
 * Assembler that transforms batch status update REST resources into application commands.
 */
public final class UpdateBatchStatusCommandFromResourceAssembler {

    private UpdateBatchStatusCommandFromResourceAssembler() {
    }

    /**
     * Converts a batch status update resource into a release batch command.
     *
     * @param batchId batch numeric identifier from the route path
     * @param resource batch status update resource
     * @return release batch command
     */
    public static ReleaseBatchCommand toReleaseCommandFromResource(
            Long batchId,
            UpdateBatchStatusResource resource
    ) {
        return new ReleaseBatchCommand(
                batchId,
                resource.releaseDate(),
                resource.notes()
        );
    }

    /**
     * Converts a batch status update resource into a reject batch command.
     *
     * @param batchId batch numeric identifier from the route path
     * @param resource batch status update resource
     * @return reject batch command
     */
    public static RejectBatchCommand toRejectCommandFromResource(
            Long batchId,
            UpdateBatchStatusResource resource
    ) {
        return new RejectBatchCommand(
                batchId,
                resource.rejectionDate(),
                resource.reason()
        );
    }
}