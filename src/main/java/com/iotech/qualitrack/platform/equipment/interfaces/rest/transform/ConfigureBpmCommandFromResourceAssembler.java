package com.iotech.qualitrack.platform.equipment.interfaces.rest.transform;

import com.iotech.qualitrack.platform.equipment.domain.model.commands.ConfigureBpmParametersCommand;
import com.iotech.qualitrack.platform.equipment.interfaces.rest.resources.ConfigureBpmResource;

/**
 * Assembler to convert a ConfigureBpmResource to a ConfigureBpmParametersCommand.
 */
public class ConfigureBpmCommandFromResourceAssembler {

    /**
     * Converts a ConfigureBpmResource to a ConfigureBpmParametersCommand.
     *
     * @param equipmentId The equipment numeric ID extracted from the path.
     * @param resource The {@link ConfigureBpmResource} resource to convert.
     * @return The {@link ConfigureBpmParametersCommand} command that results from the conversion.
     */
    public static ConfigureBpmParametersCommand toCommandFromResource(Long equipmentId, ConfigureBpmResource resource) {
        return new ConfigureBpmParametersCommand(
                equipmentId,
                resource.parameterName(),
                resource.minValue(),
                resource.maxValue(),
                resource.unit()
        );
    }
}