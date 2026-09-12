package com.iotech.qualitrack.platform.ca.interfaces.rest.transform;

import com.iotech.qualitrack.platform.ca.domain.model.commands.AcknowledgeAlertCommand;
import com.iotech.qualitrack.platform.ca.interfaces.rest.resources.AcknowledgeAlertResource;

/**
 * Assembler to convert AcknowledgeAlertResource into AcknowledgeAlertCommand.
 */
public class AcknowledgeAlertCommandFromResourceAssembler {

    public static AcknowledgeAlertCommand toCommandFromResource(Long alertId, AcknowledgeAlertResource resource) {
        return new AcknowledgeAlertCommand(
                alertId,
                resource.acknowledgedBy()
        );
    }
}