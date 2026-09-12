package com.iotech.qualitrack.platform.ra.interfaces.rest.transform;

import com.iotech.qualitrack.platform.ra.domain.model.commands.RecordAuditLogEntryCommand;
import com.iotech.qualitrack.platform.ra.interfaces.rest.resources.RecordAuditLogEntryResource;

/**
 * Assembler that transforms audit log REST resources into application commands.
 */
public final class RecordAuditLogEntryCommandFromResourceAssembler {

    private RecordAuditLogEntryCommandFromResourceAssembler() {
    }

    /**
     * Converts an audit log entry resource into a command.
     *
     * @param entityType the affected entity type from the request path context
     * @param entityId the affected entity identifier from the request path
     * @param resource the audit log entry request resource
     * @return the audit log entry recording command
     */
    public static RecordAuditLogEntryCommand toCommandFromResource(
            String entityType,
            Long entityId,
            RecordAuditLogEntryResource resource
    ) {
        return new RecordAuditLogEntryCommand(
                resource.action(),
                entityType,
                entityId,
                resource.performedBy(),
                resource.details()
        );
    }
}