package com.iotech.qualitrack.platform.ca.interfaces.rest;

import com.iotech.qualitrack.platform.ca.application.commandservices.CaCommandService;
import com.iotech.qualitrack.platform.ca.application.queryservices.CaQueryService;
import com.iotech.qualitrack.platform.ca.domain.model.aggregates.DeviationAlert;
import com.iotech.qualitrack.platform.ca.domain.model.commands.AcknowledgeAlertCommand;
import com.iotech.qualitrack.platform.ca.domain.model.commands.ResolveAlertCommand;
import com.iotech.qualitrack.platform.ca.domain.model.queries.GetAlertByIdQuery;
import com.iotech.qualitrack.platform.ca.domain.model.queries.GetAlertsQuery;
import com.iotech.qualitrack.platform.ca.domain.model.valueobjects.AlertSeverity;
import com.iotech.qualitrack.platform.ca.domain.model.valueobjects.AlertStatus;
import com.iotech.qualitrack.platform.ca.interfaces.rest.resources.CreateDeviationAlertResource;
import com.iotech.qualitrack.platform.ca.interfaces.rest.resources.DeviationAlertResource;
import com.iotech.qualitrack.platform.ca.interfaces.rest.resources.UpdateDeviationAlertStatusResource;
import com.iotech.qualitrack.platform.ca.interfaces.rest.transform.CreateDeviationAlertCommandFromResourceAssembler;
import com.iotech.qualitrack.platform.ca.interfaces.rest.transform.DeviationAlertResourceFromEntityAssembler;
import com.iotech.qualitrack.platform.shared.application.result.ApplicationError;
import com.iotech.qualitrack.platform.shared.application.result.Result;
import com.iotech.qualitrack.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller that exposes deviation alert resources and lifecycle endpoints.
 */
@RestController
@RequestMapping(value = "/api/v1", produces = APPLICATION_JSON_VALUE)
public class DeviationAlertController {

    private final CaCommandService caCommandService;
    private final CaQueryService caQueryService;

    public DeviationAlertController(
            CaCommandService caCommandService,
            CaQueryService caQueryService
    ) {
        this.caCommandService = caCommandService;
        this.caQueryService = caQueryService;
    }

    @PostMapping(value = "/equipments/{equipmentId}/deviation-alerts", consumes = APPLICATION_JSON_VALUE)
    @org.springframework.security.access.prepost.PreAuthorize("#resource.batchId() == null || @tenantAccess.allows('batchId', #resource.batchId())")
    @Operation(
            summary = "Create equipment deviation alert",
            tags = {"Equipment"}
    )
    public ResponseEntity<?> createEquipmentAlert(
            @PathVariable Long equipmentId,
            @RequestBody CreateDeviationAlertResource resource
    ) {
        var command = CreateDeviationAlertCommandFromResourceAssembler.toCommandFromResource(
                equipmentId,
                resource
        );

        var result = caCommandService.handle(command)
                .flatMap(alertId -> caQueryService.handle(new GetAlertByIdQuery(alertId))
                        .<Result<DeviationAlert, ApplicationError>>map(Result::success)
                        .orElseGet(() -> Result.failure(ApplicationError.notFound("DeviationAlert", alertId))));

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                DeviationAlertResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @GetMapping(value = "/deviation-alerts/{alertId}")
    @Operation(
            summary = "Get deviation alert by ID",
            tags = {"Deviation Alerts"}
    )
    public ResponseEntity<DeviationAlertResource> getAlertById(
            @PathVariable Long alertId
    ) {
        var alert = caQueryService.handle(new GetAlertByIdQuery(alertId));

        if (alert.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(
                DeviationAlertResourceFromEntityAssembler.toResourceFromEntity(alert.get())
        );
    }

    @GetMapping(value = "/equipments/{equipmentId}/deviation-alerts")
    @Operation(
            summary = "Get equipment deviation alerts",
            tags = {"Equipment"}
    )
    public ResponseEntity<List<DeviationAlertResource>> getEquipmentAlerts(
            @PathVariable Long equipmentId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String severity
    ) {
        var filters = toAlertFilters(status, severity);
        if (filters == null) return ResponseEntity.badRequest().build();

        var alerts = caQueryService.handle(new GetAlertsQuery(
                equipmentId,
                null,
                filters.status(),
                filters.severity()
        ));

        return ResponseEntity.ok(toDeviationAlertResources(alerts));
    }

    @GetMapping(value = "/batches/{batchId}/deviation-alerts")
    @Operation(
            summary = "Get batch deviation alerts",
            tags = {"Batches"}
    )
    public ResponseEntity<List<DeviationAlertResource>> getBatchAlerts(
            @PathVariable Long batchId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String severity
    ) {
        var filters = toAlertFilters(status, severity);
        if (filters == null) return ResponseEntity.badRequest().build();

        var alerts = caQueryService.handle(new GetAlertsQuery(
                null,
                batchId,
                filters.status(),
                filters.severity()
        ));

        return ResponseEntity.ok(toDeviationAlertResources(alerts));
    }

    @PatchMapping(value = "/deviation-alerts/{alertId}", consumes = APPLICATION_JSON_VALUE)
    @org.springframework.security.access.prepost.PreAuthorize("@tenantAccess.allows('userId', #resource.performedBy())")
    @Operation(
            summary = "Update deviation alert status",
            tags = {"Deviation Alerts"}
    )
    public ResponseEntity<?> updateAlertStatus(
            @PathVariable Long alertId,
            @RequestBody UpdateDeviationAlertStatusResource resource
    ) {
        Result<Long, ApplicationError> commandResult;

        if (resource.status() == AlertStatus.ACKNOWLEDGED) {
            commandResult = caCommandService.handle(new AcknowledgeAlertCommand(
                    alertId,
                    resource.performedBy()
            ));
        } else if (resource.status() == AlertStatus.RESOLVED) {
            commandResult = caCommandService.handle(new ResolveAlertCommand(
                    alertId,
                    resource.performedBy(),
                    resource.resolutionNotes()
            ));
        } else {
            return ResponseEntity.badRequest().build();
        }

        var result = commandResult
                .flatMap(updatedAlertId -> caQueryService.handle(new GetAlertByIdQuery(updatedAlertId))
                        .<Result<DeviationAlert, ApplicationError>>map(Result::success)
                        .orElseGet(() -> Result.failure(ApplicationError.notFound("DeviationAlert", updatedAlertId))));

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                DeviationAlertResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }

    private static List<DeviationAlertResource> toDeviationAlertResources(List<DeviationAlert> alerts) {
        return alerts.stream()
                .map(DeviationAlertResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
    }

    private static AlertFilters toAlertFilters(String status, String severity) {
        try {
            var alertStatus = status == null || status.isBlank()
                    ? null
                    : AlertStatus.valueOf(status.toUpperCase());

            var alertSeverity = severity == null || severity.isBlank()
                    ? null
                    : AlertSeverity.valueOf(severity.toUpperCase());

            return new AlertFilters(alertStatus, alertSeverity);
        } catch (IllegalArgumentException exception) {
            return null;
        }
    }

    private record AlertFilters(AlertStatus status, AlertSeverity severity) {
    }
}
