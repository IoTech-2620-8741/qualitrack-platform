package com.iotech.qualitrack.platform.ra.interfaces.rest.transform;

import com.iotech.qualitrack.platform.ra.domain.model.commands.CalculateDeviationTrendCommand;
import com.iotech.qualitrack.platform.ra.interfaces.rest.resources.CalculateDeviationTrendResource;

/**
 * Assembler that transforms deviation trend calculation resources into commands.
 */
public final class CalculateDeviationTrendCommandFromResourceAssembler {

    /**
     * Private constructor to prevent instantiation.
     */
    private CalculateDeviationTrendCommandFromResourceAssembler() {
    }

    /**
     * Converts a deviation trend calculation resource into a command.
     *
     * @param resource The request resource.
     * @return The calculation command.
     */
    public static CalculateDeviationTrendCommand toCommandFromResource(
            Long equipmentId,
            CalculateDeviationTrendResource resource
    ) {
        return new CalculateDeviationTrendCommand(
                equipmentId,
                resource.parameterName()
        );
    }
}