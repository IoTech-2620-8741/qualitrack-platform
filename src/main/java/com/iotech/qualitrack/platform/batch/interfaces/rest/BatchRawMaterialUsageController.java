package com.iotech.qualitrack.platform.batch.interfaces.rest;

import com.iotech.qualitrack.platform.batch.application.commandservices.RawMaterialUsageCommandService;
import com.iotech.qualitrack.platform.batch.application.queryservices.RawMaterialUsageQueryService;
import com.iotech.qualitrack.platform.batch.domain.model.entities.RawMaterialUsage;
import com.iotech.qualitrack.platform.batch.domain.model.queries.GetRawMaterialUsageByBatchIdQuery;
import com.iotech.qualitrack.platform.batch.interfaces.rest.resources.LinkRawMaterialResource;
import com.iotech.qualitrack.platform.batch.interfaces.rest.resources.RawMaterialUsageResource;
import com.iotech.qualitrack.platform.batch.interfaces.rest.transform.LinkRawMaterialCommandFromResourceAssembler;
import com.iotech.qualitrack.platform.batch.interfaces.rest.transform.RawMaterialUsageResourceFromEntityAssembler;
import com.iotech.qualitrack.platform.shared.application.result.ApplicationError;
import com.iotech.qualitrack.platform.shared.application.result.Result;
import com.iotech.qualitrack.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller that exposes raw material usage endpoints for production batches.
 */
@RestController
@RequestMapping(value = "/api/v1/batches/{batchId}/raw-materials", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Batches", description = "Batch management endpoints")
public class BatchRawMaterialUsageController {

    private final RawMaterialUsageCommandService rawMaterialUsageCommandService;
    private final RawMaterialUsageQueryService rawMaterialUsageQueryService;

    public BatchRawMaterialUsageController(RawMaterialUsageCommandService rawMaterialUsageCommandService,
                                           RawMaterialUsageQueryService rawMaterialUsageQueryService) {
        this.rawMaterialUsageCommandService = rawMaterialUsageCommandService;
        this.rawMaterialUsageQueryService = rawMaterialUsageQueryService;
    }

    @PostMapping
    @org.springframework.security.access.prepost.PreAuthorize("@tenantAccess.allows('rawMaterialId', #resource.rawMaterialId())")
    @Operation(summary = "Consume raw material for batch", description = "Atomically deducts stock and records consumption. Requires the stock unit; incompatible units return 400 and insufficient stock returns 409. Concurrent requests cannot overdraw stock.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Raw material usage linked successfully", content = @Content(schema = @Schema(implementation = RawMaterialUsageResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Insufficient raw material stock"),
            @ApiResponse(responseCode = "404", description = "Batch or raw material not found")
    })
    public ResponseEntity<?> linkRawMaterial(
            @PathVariable @Parameter(description = "Batch numeric identifier", example = "1", required = true) Long batchId,
            @RequestBody LinkRawMaterialResource resource
    ) {
        var command = LinkRawMaterialCommandFromResourceAssembler.toCommandFromResource(batchId, resource);

        var result = rawMaterialUsageCommandService.handle(command)
                .flatMap(usageId -> rawMaterialUsageQueryService.handle(new GetRawMaterialUsageByBatchIdQuery(batchId))
                        .stream()
                        .filter(usage -> usage.getId().equals(usageId))
                        .findFirst()
                        .<Result<RawMaterialUsage, ApplicationError>>map(Result::success)
                        .orElseGet(() -> Result.failure(ApplicationError.notFound("RawMaterialUsage", usageId))));

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                RawMaterialUsageResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @GetMapping
    @Operation(summary = "Get batch raw material usage", description = "Retrieves all raw materials consumed by a specific production batch.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Raw material usage records retrieved successfully")
    })
    public ResponseEntity<List<RawMaterialUsageResource>> getRawMaterialUsageByBatchId(
            @PathVariable @Parameter(description = "Batch numeric identifier", example = "1", required = true) Long batchId
    ) {
        var usages = rawMaterialUsageQueryService.handle(new GetRawMaterialUsageByBatchIdQuery(batchId));

        var resources = usages.stream()
                .map(RawMaterialUsageResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }
}
