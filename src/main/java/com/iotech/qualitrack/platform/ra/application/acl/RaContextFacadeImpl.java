package com.iotech.qualitrack.platform.ra.application.acl;

import com.iotech.qualitrack.platform.ra.application.commandservices.AuditLogCommandService;
import com.iotech.qualitrack.platform.ra.application.queryservices.RaQueryService;
import com.iotech.qualitrack.platform.ra.domain.model.commands.RecordAuditLogEntryCommand;
import com.iotech.qualitrack.platform.ra.domain.model.queries.GetAuditReportByIdQuery;
import com.iotech.qualitrack.platform.ra.domain.model.queries.GetKpiDashboardByLaboratoryIdQuery;
import com.iotech.qualitrack.platform.ra.domain.model.valueobjects.AuditAction;
import com.iotech.qualitrack.platform.ra.interfaces.acl.RaContextFacade;
import org.springframework.stereotype.Service;

/**
 * Application-layer implementation of the RA ACL facade.
 */
@Service
public class RaContextFacadeImpl implements RaContextFacade {

    private final AuditLogCommandService auditLogCommandService;
    private final RaQueryService raQueryService;

    /**
     * Creates the facade implementation.
     *
     * @param auditLogCommandService audit log command service
     * @param raQueryService reporting and analysis query service
     */
    public RaContextFacadeImpl(
            AuditLogCommandService auditLogCommandService,
            RaQueryService raQueryService
    ) {
        this.auditLogCommandService = auditLogCommandService;
        this.raQueryService = raQueryService;
    }

    /**
     * Records an audit log entry through the RA application layer.
     *
     * @param action The performed audit action.
     * @param entityType The affected entity type.
     * @param entityId The affected entity identifier.
     * @param performedBy The actor identifier.
     * @param details Optional action details.
     * @return true if the entry was recorded successfully.
     */
    @Override
    public boolean recordAuditLog(
            AuditAction action,
            String entityType,
            Long entityId,
            Long performedBy,
            String details
    ) {
        try {
            var command = new RecordAuditLogEntryCommand(
                    action,
                    entityType,
                    entityId,
                    performedBy,
                    details
            );

            return auditLogCommandService.handle(command).isSuccess();

        } catch (IllegalArgumentException exception) {
            return false;
        }
    }

    /**
     * Verifies whether an audit report exists by its ID.
     *
     * @param reportId The numeric report identifier.
     * @return true if the report exists.
     */
    @Override
    public boolean existsAuditReportById(Long reportId) {
        try {
            var query = new GetAuditReportByIdQuery(reportId);

            return raQueryService.handle(query).isPresent();

        } catch (IllegalArgumentException exception) {
            return false;
        }
    }

    /**
     * Verifies whether a KPI dashboard exists for a laboratory.
     *
     * @param laboratoryId The numeric laboratory identifier.
     * @return true if a KPI dashboard exists for the laboratory.
     */
    @Override
    public boolean existsKpiDashboardByLaboratoryId(Long laboratoryId) {
        try {
            var query = new GetKpiDashboardByLaboratoryIdQuery(laboratoryId);

            return raQueryService.handle(query).isPresent();

        } catch (IllegalArgumentException exception) {
            return false;
        }
    }
}