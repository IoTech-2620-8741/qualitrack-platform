package com.iotech.qualitrack.platform.tracking.interfaces.rest.transform;

import com.iotech.qualitrack.platform.tracking.domain.model.commands.RecordMeasurementCommand;
import com.iotech.qualitrack.platform.tracking.interfaces.rest.resources.RecordMeasurementResource;

/**
 * Assembler that transforms measurement REST resources into application commands.
 */
public final class RecordMeasurementCommandFromResourceAssembler {

    private RecordMeasurementCommandFromResourceAssembler() {
    }

    /**
     * Converts a record measurement resource into a command.
     *
     * @param equipmentId the equipment numeric identifier from the route path
     * @param resource the record measurement resource
     * @return the record measurement command
     */
    public static RecordMeasurementCommand toCommandFromResource(
            Long equipmentId,
            RecordMeasurementResource resource
    ) {
        return new RecordMeasurementCommand(
                equipmentId,
                resource.parameterName(),
                resource.value(),
                resource.unit(),
                resource.timestamp()
        );
    }
}