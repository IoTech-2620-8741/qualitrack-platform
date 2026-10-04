package com.iotech.qualitrack.platform.ra.interfaces.rest;

import com.iotech.qualitrack.platform.ra.application.queryservices.RaQueryService;
import com.iotech.qualitrack.platform.ra.domain.model.queries.GetKpiDashboardByLaboratoryIdQuery;
import com.iotech.qualitrack.platform.ra.interfaces.rest.resources.KpiDashboardResource;
import com.iotech.qualitrack.platform.ra.interfaces.rest.transform.KpiDashboardResourceFromEntityAssembler;
import com.iotech.qualitrack.platform.shared.application.result.ApplicationError;
import com.iotech.qualitrack.platform.shared.interfaces.rest.resources.ErrorResource;
import com.iotech.qualitrack.platform.shared.interfaces.rest.transform.ErrorResponseAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Indicators of a laboratory calculated from its persisted records (TS81).
 */
@RestController
@RequestMapping(value = "/api/v1/laboratories/{laboratoryId}/kpi-dashboards", produces = APPLICATION_JSON_VALUE)
public class KpiDashboardController {

    private final RaQueryService raQueryService;

    public KpiDashboardController(RaQueryService raQueryService) {
        this.raQueryService = raQueryService;
    }

    @GetMapping
    @Operation(summary = "Get the current indicators of the laboratory",
            description = "Calculated from persisted records. Empty metrics mean no source data. Live results have no "
                    + "snapshot id; health score and targets are null when no assessment is configured.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Indicators of the laboratory",
                    content = @Content(schema = @Schema(implementation = KpiDashboardResource.class))),
            @ApiResponse(responseCode = "403", description = "Laboratory not available to the account"),
            @ApiResponse(responseCode = "404", description = "Laboratory without indicators",
                    content = @Content(schema = @Schema(implementation = ErrorResource.class)))
    })
    public ResponseEntity<?> getKpiDashboardByLaboratoryId(@PathVariable Long laboratoryId) {
        return raQueryService.handle(new GetKpiDashboardByLaboratoryIdQuery(laboratoryId))
                .<ResponseEntity<?>>map(dashboard -> ResponseEntity.ok(KpiDashboardResourceFromEntityAssembler.toResourceFromEntity(dashboard)))
                .orElseGet(() -> ErrorResponseAssembler.toErrorResponseFromApplicationError(
                        ApplicationError.notFound("KpiDashboard", laboratoryId)));
    }
}
