package com.iotech.qualitrack.platform.ra.interfaces.rest;

import com.iotech.qualitrack.platform.ra.application.commandservices.AuditLogCommandService;
import com.iotech.qualitrack.platform.ra.application.queryservices.RaQueryService;
import com.iotech.qualitrack.platform.ra.domain.model.entities.AuditLogEntry;
import com.iotech.qualitrack.platform.ra.domain.model.queries.GetAuditLogQuery;
import com.iotech.qualitrack.platform.ra.interfaces.rest.resources.AuditLogEntryResource;
import com.iotech.qualitrack.platform.ra.interfaces.rest.resources.RecordAuditLogEntryResource;
import com.iotech.qualitrack.platform.ra.interfaces.rest.transform.AuditLogEntryResourceFromEntityAssembler;
import com.iotech.qualitrack.platform.ra.interfaces.rest.transform.RecordAuditLogEntryCommandFromResourceAssembler;
import com.iotech.qualitrack.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller that exposes batch audit log resources.
 */
@RestController
@RequestMapping(
        value = "/api/v1/batches/{batchId}/audit-logs",
        produces = APPLICATION_JSON_VALUE
)
public class BatchAuditLogController {

    private static final String ENTITY_TYPE = "BATCH";

    private final RaQueryService raQueryService;
    private final AuditLogCommandService auditLogCommandService;

    public BatchAuditLogController(
            RaQueryService raQueryService,
            AuditLogCommandService auditLogCommandService
    ) {
        this.raQueryService = raQueryService;
        this.auditLogCommandService = auditLogCommandService;
    }

    /**
     * Retrieves audit log entries for a batch using optional date filters.
     *
     * @param batchId batch numeric identifier
     * @param dateFrom optional start date
     * @param dateTo optional end date
     * @return the audit log entry resources
     */
    @GetMapping
    @Operation(summary = "Get batch audit log entries")
    public ResponseEntity<List<AuditLogEntryResource>> getBatchAuditLogs(
            @PathVariable Long batchId,
            @RequestParam(required = false) String dateFrom,
            @RequestParam(required = false) String dateTo
    ) {
        var entries = raQueryService.handle(new GetAuditLogQuery(
                null,
                batchId,
                dateFrom,
                dateTo
        ));

        return ResponseEntity.ok(toAuditLogEntryResources(entries));
    }

    /**
     * Records a new audit log entry for a batch.
     *
     * @param batchId batch numeric identifier
     * @param resource the audit log entry request resource
     * @return the recorded audit log entry resource
     */
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @org.springframework.security.access.prepost.PreAuthorize("@tenantAccess.allows('userId', #resource.performedBy())")
    @Operation(summary = "Record batch audit log entry")
    public ResponseEntity<?> recordBatchAuditLogEntry(
            @PathVariable Long batchId,
            @RequestBody RecordAuditLogEntryResource resource
    ) {
        var command = RecordAuditLogEntryCommandFromResourceAssembler.toCommandFromResource(
                ENTITY_TYPE,
                batchId,
                resource
        );

        var result = auditLogCommandService.handle(command);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                AuditLogEntryResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    private static List<AuditLogEntryResource> toAuditLogEntryResources(List<AuditLogEntry> entries) {
        return entries.stream()
                .map(AuditLogEntryResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
    }
}
