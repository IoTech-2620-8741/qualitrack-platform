package com.iotech.qualitrack.platform.ra.interfaces.rest.transform;

import com.iotech.qualitrack.platform.ra.domain.model.commands.CalculateKpiDashboardCommand;
import com.iotech.qualitrack.platform.ra.interfaces.rest.resources.CalculateKpiDashboardResource;

/**
 * Assembler that transforms KPI dashboard calculation resources into commands.
 */
public final class CalculateKpiDashboardCommandFromResourceAssembler {

    /**
     * Private constructor to prevent instantiation.
     */
    private CalculateKpiDashboardCommandFromResourceAssembler() {
    }

    /**
     * Converts a KPI dashboard calculation resource into a command.
     *
     * @param resource The request resource.
     * @return The calculation command.
     */
    public static CalculateKpiDashboardCommand toCommandFromResource(
            CalculateKpiDashboardResource resource
    ) {
        return new CalculateKpiDashboardCommand(resource.laboratoryId());
    }
}