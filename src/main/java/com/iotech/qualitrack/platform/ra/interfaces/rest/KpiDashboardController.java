package com.iotech.qualitrack.platform.ra.interfaces.rest;

import com.iotech.qualitrack.platform.ra.application.commandservices.RaCommandService;
import com.iotech.qualitrack.platform.ra.application.queryservices.RaQueryService;
import com.iotech.qualitrack.platform.ra.domain.model.aggregates.KpiDashboard;
import com.iotech.qualitrack.platform.ra.domain.model.commands.CalculateKpiDashboardCommand;
import com.iotech.qualitrack.platform.ra.domain.model.queries.GetKpiDashboardByLaboratoryIdQuery;
import com.iotech.qualitrack.platform.ra.interfaces.rest.resources.KpiDashboardResource;
import com.iotech.qualitrack.platform.ra.interfaces.rest.transform.KpiDashboardResourceFromEntityAssembler;
import com.iotech.qualitrack.platform.shared.application.result.ApplicationError;
import com.iotech.qualitrack.platform.shared.application.result.Result;
import com.iotech.qualitrack.platform.shared.interfaces.rest.transform.ErrorResponseAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller that exposes laboratory KPI dashboard resources.
 */
@RestController
@RequestMapping(
        value = "/api/v1/laboratories/{laboratoryId}/kpi-dashboards",
        produces = APPLICATION_JSON_VALUE
)
public class KpiDashboardController {

    private final RaCommandService raCommandService;
    private final RaQueryService raQueryService;

    public KpiDashboardController(
            RaCommandService raCommandService,
            RaQueryService raQueryService
    ) {
        this.raCommandService = raCommandService;
        this.raQueryService = raQueryService;
    }

    /**
     * Creates a KPI dashboard snapshot for a laboratory.
     *
     * @param laboratoryId laboratory numeric identifier
     * @return the created KPI dashboard resource
     */
    @PostMapping
    @Operation(summary = "Create laboratory KPI dashboard snapshot")
    public ResponseEntity<?> createKpiDashboard(
            @PathVariable Long laboratoryId
    ) {
        var result = raCommandService.handle(new CalculateKpiDashboardCommand(laboratoryId));

        return toKpiDashboardResponse(result);
    }

    /**
     * Retrieves KPI dashboard snapshots for a laboratory.
     *
     * @param laboratoryId laboratory numeric identifier
     * @param limit maximum number of dashboard snapshots to return
     * @return the KPI dashboard resource when found
     */
    @GetMapping
    @Operation(summary = "Get current laboratory operational metrics",
            description = "Calculated from persisted records. Empty metrics mean no source data. Live results have no snapshot id; health score and targets are null when no assessment is configured.")
    public ResponseEntity<KpiDashboardResource> getKpiDashboardByLaboratoryId(
            @PathVariable Long laboratoryId,
            @Parameter(description = "Maximum number of dashboard snapshots to return")
            @RequestParam(defaultValue = "1") Integer limit
    ) {
        var dashboard = raQueryService.handle(new GetKpiDashboardByLaboratoryIdQuery(laboratoryId));

        if (dashboard.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(
                KpiDashboardResourceFromEntityAssembler.toResourceFromEntity(dashboard.get())
        );
    }

    private static ResponseEntity<?> toKpiDashboardResponse(
            Result<KpiDashboard, ApplicationError> result
    ) {
        return switch (result) {
            case Result.Success<KpiDashboard, ApplicationError> success ->
                    ResponseEntity.status(201).body(
                            KpiDashboardResourceFromEntityAssembler.toResourceFromEntity(success.value())
                    );

            case Result.Failure<KpiDashboard, ApplicationError> failure ->
                    ErrorResponseAssembler.toErrorResponseFromApplicationError(failure.error());
        };
    }
}
