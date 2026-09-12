package com.iotech.qualitrack.platform.ra.interfaces.rest;

import com.iotech.qualitrack.platform.ra.application.commandservices.RaCommandService;
import com.iotech.qualitrack.platform.ra.application.queryservices.RaQueryService;
import com.iotech.qualitrack.platform.ra.domain.model.entities.DeviationTrend;
import com.iotech.qualitrack.platform.ra.domain.model.queries.GetDeviationTrendsByEquipmentIdQuery;
import com.iotech.qualitrack.platform.ra.interfaces.rest.resources.CalculateDeviationTrendResource;
import com.iotech.qualitrack.platform.ra.interfaces.rest.resources.DeviationTrendResource;
import com.iotech.qualitrack.platform.ra.interfaces.rest.transform.CalculateDeviationTrendCommandFromResourceAssembler;
import com.iotech.qualitrack.platform.ra.interfaces.rest.transform.DeviationTrendResourceFromEntityAssembler;
import com.iotech.qualitrack.platform.shared.application.result.ApplicationError;
import com.iotech.qualitrack.platform.shared.application.result.Result;
import com.iotech.qualitrack.platform.shared.interfaces.rest.transform.ErrorResponseAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller that exposes equipment deviation trend resources.
 */
@RestController
@RequestMapping(
        value = "/api/v1/equipments/{equipmentId}/deviation-trends",
        produces = APPLICATION_JSON_VALUE
)
@Tag(name = "Equipment", description = "Equipment deviation trend analysis endpoints")
public class DeviationTrendController {

    private final RaCommandService raCommandService;
    private final RaQueryService raQueryService;

    public DeviationTrendController(
            RaCommandService raCommandService,
            RaQueryService raQueryService
    ) {
        this.raCommandService = raCommandService;
        this.raQueryService = raQueryService;
    }

    /**
     * Creates and stores a deviation trend for an equipment parameter.
     *
     * @param equipmentId equipment numeric identifier
     * @param resource deviation trend creation request resource
     * @return the created deviation trend resource
     */
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Create equipment deviation trend")
    public ResponseEntity<?> createDeviationTrend(
            @PathVariable Long equipmentId,
            @RequestBody CalculateDeviationTrendResource resource
    ) {
        var command = CalculateDeviationTrendCommandFromResourceAssembler.toCommandFromResource(
                equipmentId,
                resource
        );
        var result = raCommandService.handle(command);

        return toDeviationTrendResponse(result);
    }

    /**
     * Retrieves deviation trends for an equipment.
     *
     * @param equipmentId equipment numeric identifier
     * @return the deviation trend resources
     */
    @GetMapping
    @Operation(summary = "Get equipment deviation trends")
    public ResponseEntity<List<DeviationTrendResource>> getDeviationTrendsByEquipmentId(
            @PathVariable Long equipmentId
    ) {
        var trends = raQueryService.handle(new GetDeviationTrendsByEquipmentIdQuery(equipmentId));

        var resources = trends.stream()
                .map(DeviationTrendResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }

    private static ResponseEntity<?> toDeviationTrendResponse(
            Result<DeviationTrend, ApplicationError> result
    ) {
        return switch (result) {
            case Result.Success<DeviationTrend, ApplicationError> success ->
                    ResponseEntity.status(201).body(
                            DeviationTrendResourceFromEntityAssembler.toResourceFromEntity(success.value())
                    );

            case Result.Failure<DeviationTrend, ApplicationError> failure ->
                    ErrorResponseAssembler.toErrorResponseFromApplicationError(failure.error());
        };
    }
}