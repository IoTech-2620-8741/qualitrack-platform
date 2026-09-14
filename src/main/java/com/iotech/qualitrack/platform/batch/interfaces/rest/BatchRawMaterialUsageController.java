package com.iotech.qualitrack.platform.batch.interfaces.rest;

import com.iotech.qualitrack.platform.batch.application.queryservices.RawMaterialUsageQueryService;
import com.iotech.qualitrack.platform.batch.domain.model.queries.GetRawMaterialUsageByBatchIdQuery;
import com.iotech.qualitrack.platform.batch.interfaces.rest.resources.LinkRawMaterialResource;
import com.iotech.qualitrack.platform.batch.interfaces.rest.resources.RawMaterialUsageResource;
import com.iotech.qualitrack.platform.batch.interfaces.rest.transform.RawMaterialUsageResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    private final RawMaterialUsageQueryService rawMaterialUsageQueryService;

    public BatchRawMaterialUsageController(RawMaterialUsageQueryService rawMaterialUsageQueryService) {
        this.rawMaterialUsageQueryService = rawMaterialUsageQueryService;
    }

    @PostMapping
    @org.springframework.security.access.prepost.PreAuthorize("@tenantAccess.allows('rawMaterialId', #resource.rawMaterialId())")
    @Operation(summary = "Legacy write disabled; consume a reviewed Inventory receipt", deprecated = true)
    @ApiResponse(responseCode = "410", description = "Use the Inventory consumptions endpoint")
    public ResponseEntity<?> linkRawMaterial(
            @PathVariable @Parameter(description = "Batch numeric identifier", example = "1", required = true) Long batchId,
            @RequestBody LinkRawMaterialResource resource
    ) {
        return ResponseEntity.status(410).body(java.util.Map.of("code", "RECEIPT_REQUIRED",
                "message", "Select an Inventory receipt and use the laboratory inventory consumptions endpoint."));
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
