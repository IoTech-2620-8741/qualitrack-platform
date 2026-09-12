package com.iotech.qualitrack.platform.ca.interfaces.rest.transform;

import com.iotech.qualitrack.platform.ca.domain.model.commands.CreateDeviationAlertCommand;
import com.iotech.qualitrack.platform.ca.interfaces.rest.resources.CreateDeviationAlertResource;

/**
 * Assembler to convert CreateDeviationAlertResource into CreateDeviationAlertCommand.
 */
public final class CreateDeviationAlertCommandFromResourceAssembler {

    private CreateDeviationAlertCommandFromResourceAssembler() {
    }

    public static CreateDeviationAlertCommand toCommandFromResource(
            Long equipmentId,
            CreateDeviationAlertResource resource
    ) {
        return new CreateDeviationAlertCommand(
                equipmentId,
                resource.batchId(),
                resource.parameterName(),
                resource.recordedValue(),
                resource.thresholdValue(),
                resource.unit(),
                resource.timestamp(),
                resource.severity()
        );
    }
}