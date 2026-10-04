package com.iotech.qualitrack.platform.ca.interfaces.rest;

import com.iotech.qualitrack.platform.ca.application.commandservices.CaCommandService;
import com.iotech.qualitrack.platform.ca.application.queryservices.CaQueryService;
import com.iotech.qualitrack.platform.ca.domain.model.aggregates.DeviationAlert;
import com.iotech.qualitrack.platform.ca.domain.model.commands.AcknowledgeAlertCommand;
import com.iotech.qualitrack.platform.ca.domain.model.queries.GetAlertByIdQuery;
import com.iotech.qualitrack.platform.ca.domain.model.queries.GetAlertsQuery;
import com.iotech.qualitrack.platform.ca.domain.model.valueobjects.AlertSeverity;
import com.iotech.qualitrack.platform.ca.domain.model.valueobjects.AlertStatus;
import com.iotech.qualitrack.platform.ca.interfaces.rest.resources.DeviationAlertResource;
import com.iotech.qualitrack.platform.ca.interfaces.rest.resources.ResolveAlertResource;
import com.iotech.qualitrack.platform.ca.interfaces.rest.transform.DeviationAlertResourceFromEntityAssembler;
import com.iotech.qualitrack.platform.ca.interfaces.rest.transform.ResolveAlertCommandFromResourceAssembler;
import com.iotech.qualitrack.platform.shared.application.result.ApplicationError;
import com.iotech.qualitrack.platform.shared.application.result.Result;
import com.iotech.qualitrack.platform.shared.application.security.CurrentUser;
import com.iotech.qualitrack.platform.shared.interfaces.rest.resources.ErrorResource;
import com.iotech.qualitrack.platform.shared.interfaces.rest.transform.ErrorResponseAssembler;
import com.iotech.qualitrack.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.Function;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Deviation alerts of the equipment and batches of a laboratory and their lifecycle (TS75, TS76): an alert is
 * acknowledged and resolved by registering an acknowledgement or a resolution, with the authenticated user as actor.
 */
@RestController
@RequestMapping(value = "/api/v1", produces = APPLICATION_JSON_VALUE)
public class DeviationAlertController {

    private final CaCommandService caCommandService;
    private final CaQueryService caQueryService;
    private final CurrentUser currentUser;

    public DeviationAlertController(CaCommandService caCommandService, CaQueryService caQueryService,
                                    CurrentUser currentUser) {
        this.caCommandService = caCommandService;
        this.caQueryService = caQueryService;
        this.currentUser = currentUser;
    }

    @GetMapping("/deviation-alerts/{alertId}")
    @Operation(summary = "Get a deviation alert")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Deviation alert",
                    content = @Content(schema = @Schema(implementation = DeviationAlertResource.class))),
            @ApiResponse(responseCode = "403", description = "Alert not available to the account"),
            @ApiResponse(responseCode = "404", description = "Alert not found",
                    content = @Content(schema = @Schema(implementation = ErrorResource.class)))
    })
    public ResponseEntity<?> getAlertById(@PathVariable Long alertId) {
        return caQueryService.handle(new GetAlertByIdQuery(alertId))
                .<ResponseEntity<?>>map(alert -> ResponseEntity.ok(DeviationAlertResourceFromEntityAssembler.toResourceFromEntity(alert)))
                .orElseGet(() -> ErrorResponseAssembler.toErrorResponseFromApplicationError(
                        ApplicationError.notFound("DeviationAlert", alertId)));
    }

    @GetMapping("/laboratories/{laboratoryId}/equipments/{equipmentId}/deviation-alerts")
    @Operation(summary = "Get the deviation alerts of an equipment",
            description = "Optional filters: status (UNRESOLVED, ACKNOWLEDGED, RESOLVED) and severity.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Alerts of the equipment",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = DeviationAlertResource.class)))),
            @ApiResponse(responseCode = "400", description = "Unknown status or severity",
                    content = @Content(schema = @Schema(implementation = ErrorResource.class))),
            @ApiResponse(responseCode = "403", description = "Equipment not available to the account")
    })
    public ResponseEntity<?> getEquipmentAlerts(@PathVariable Long laboratoryId, @PathVariable Long equipmentId,
                                                @RequestParam(required = false) String status,
                                                @RequestParam(required = false) String severity) {
        return alerts(status, severity, filters -> new GetAlertsQuery(equipmentId, null, filters.status(), filters.severity()));
    }

    @GetMapping("/batches/{batchId}/deviation-alerts")
    @Operation(summary = "Get the deviation alerts of a batch",
            description = "Optional filters: status (UNRESOLVED, ACKNOWLEDGED, RESOLVED) and severity.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Alerts of the batch",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = DeviationAlertResource.class)))),
            @ApiResponse(responseCode = "400", description = "Unknown status or severity",
                    content = @Content(schema = @Schema(implementation = ErrorResource.class))),
            @ApiResponse(responseCode = "403", description = "Batch not available to the account")
    })
    public ResponseEntity<?> getBatchAlerts(@PathVariable Long batchId,
                                            @RequestParam(required = false) String status,
                                            @RequestParam(required = false) String severity) {
        return alerts(status, severity, filters -> new GetAlertsQuery(null, batchId, filters.status(), filters.severity()));
    }

    @PostMapping("/deviation-alerts/{alertId}/acknowledgements")
    @Operation(summary = "Acknowledge a deviation alert",
            description = "Registers that the authenticated user is attending the alert (TS75).")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Alert acknowledged",
                    content = @Content(schema = @Schema(implementation = DeviationAlertResource.class))),
            @ApiResponse(responseCode = "403", description = "Alert not available to the account"),
            @ApiResponse(responseCode = "404", description = "Alert not found",
                    content = @Content(schema = @Schema(implementation = ErrorResource.class))),
            @ApiResponse(responseCode = "409", description = "The alert is already acknowledged or resolved",
                    content = @Content(schema = @Schema(implementation = ErrorResource.class)))
    })
    public ResponseEntity<?> acknowledgeAlert(@PathVariable Long alertId) {
        return toAlertResponse(caCommandService.handle(new AcknowledgeAlertCommand(alertId, currentUser.userId())));
    }

    @PostMapping(value = "/deviation-alerts/{alertId}/resolutions", consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Resolve a deviation alert",
            description = "Registers the resolution of the alert by the authenticated user with its notes (TS76).")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Alert resolved",
                    content = @Content(schema = @Schema(implementation = DeviationAlertResource.class))),
            @ApiResponse(responseCode = "400", description = "Resolution notes missing",
                    content = @Content(schema = @Schema(implementation = ErrorResource.class))),
            @ApiResponse(responseCode = "403", description = "Alert not available to the account"),
            @ApiResponse(responseCode = "404", description = "Alert not found",
                    content = @Content(schema = @Schema(implementation = ErrorResource.class))),
            @ApiResponse(responseCode = "409", description = "The alert is already resolved",
                    content = @Content(schema = @Schema(implementation = ErrorResource.class)))
    })
    public ResponseEntity<?> resolveAlert(@PathVariable Long alertId, @RequestBody ResolveAlertResource resource) {
        var command = ResolveAlertCommandFromResourceAssembler.toCommandFromResource(alertId, currentUser.userId(), resource);
        return toAlertResponse(caCommandService.handle(command));
    }

    private ResponseEntity<?> toAlertResponse(Result<Long, ApplicationError> commandResult) {
        var result = commandResult.flatMap(alertId -> caQueryService.handle(new GetAlertByIdQuery(alertId))
                .<Result<DeviationAlert, ApplicationError>>map(Result::success)
                .orElseGet(() -> Result.failure(ApplicationError.notFound("DeviationAlert", alertId))));
        return ResponseEntityAssembler.toResponseEntityFromResult(result,
                DeviationAlertResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.CREATED);
    }

    private ResponseEntity<?> alerts(String status, String severity, Function<AlertFilters, GetAlertsQuery> query) {
        AlertFilters filters;
        try {
            filters = new AlertFilters(
                    status == null || status.isBlank() ? null : AlertStatus.valueOf(status.toUpperCase()),
                    severity == null || severity.isBlank() ? null : AlertSeverity.valueOf(severity.toUpperCase()));
        } catch (IllegalArgumentException exception) {
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(
                    ApplicationError.validationError("filters", "Unknown alert status or severity"));
        }
        List<DeviationAlertResource> resources = caQueryService.handle(query.apply(filters)).stream()
                .map(DeviationAlertResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    private record AlertFilters(AlertStatus status, AlertSeverity severity) {
    }
}
