package com.iotech.qualitrack.platform.ra.application.internal.commandservices;

import com.iotech.qualitrack.platform.ra.application.commandservices.AuditLogCommandService;
import com.iotech.qualitrack.platform.ra.domain.model.commands.RecordAuditLogEntryCommand;
import com.iotech.qualitrack.platform.ra.domain.model.entities.AuditLogEntry;
import com.iotech.qualitrack.platform.ra.domain.repositories.AuditLogRepository;
import com.iotech.qualitrack.platform.shared.application.result.ApplicationError;
import com.iotech.qualitrack.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

/**
 * Application service implementation that records audit log entries.
 *
 * <p>This service is used to append traceability records for user or system
 * actions performed across QualiTrack bounded contexts.</p>
 */
@Service
public class AuditLogCommandServiceImpl implements AuditLogCommandService {

    private final AuditLogRepository auditLogRepository;

    public AuditLogCommandServiceImpl(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    /**
     * Handles the recording of a new audit log entry.
     *
     * @param command The command containing action, affected entity, and actor context.
     * @return The recorded audit log entry or an application error.
     */
    @Override
    public Result<AuditLogEntry, ApplicationError> handle(RecordAuditLogEntryCommand command) {
        try {
            var auditLogEntry = new AuditLogEntry(
                    command.action(),
                    command.entityType(),
                    command.entityId(),
                    command.performedBy(),
                    command.details()
            );

            var savedEntry = auditLogRepository.save(auditLogEntry);

            return Result.success(savedEntry);

        } catch (IllegalArgumentException exception) {
            return Result.failure(ApplicationError.validationError("AuditLogEntry", exception.getMessage()));
        } catch (Exception exception) {
            return Result.failure(ApplicationError.unexpected("record-audit-log-entry", exception.getMessage()));
        }
    }
}