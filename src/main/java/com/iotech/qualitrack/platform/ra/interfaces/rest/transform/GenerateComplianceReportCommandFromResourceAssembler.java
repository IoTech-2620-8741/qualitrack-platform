package com.iotech.qualitrack.platform.ra.interfaces.rest.transform;

import com.iotech.qualitrack.platform.ra.domain.model.commands.GenerateComplianceReportCommand;
import com.iotech.qualitrack.platform.ra.interfaces.rest.resources.GenerateComplianceReportResource;

/**
 * Assembler that transforms compliance report REST resources into application commands.
 */
public final class GenerateComplianceReportCommandFromResourceAssembler {

    private GenerateComplianceReportCommandFromResourceAssembler() {
    }

    /**
     * Converts a compliance report resource into a command.
     *
     * @param laboratoryId the laboratory numeric identifier from the request path
     * @param resource the compliance report request resource
     * @return the compliance report generation command
     */
    public static GenerateComplianceReportCommand toCommandFromResource(
            Long laboratoryId,
            GenerateComplianceReportResource resource
    ) {
        return new GenerateComplianceReportCommand(
                laboratoryId,
                resource.startDate(),
                resource.endDate(),
                resource.format(),
                resource.requestedBy()
        );
    }
}