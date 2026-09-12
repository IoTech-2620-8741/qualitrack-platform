package com.iotech.qualitrack.platform.batch.interfaces.rest;

import com.iotech.qualitrack.platform.batch.application.commandservices.BatchCommandService;
import com.iotech.qualitrack.platform.batch.application.queryservices.BatchQueryService;
import com.iotech.qualitrack.platform.batch.domain.model.aggregates.Batch;
import com.iotech.qualitrack.platform.batch.domain.model.queries.GetBatchByIdQuery;
import com.iotech.qualitrack.platform.batch.domain.model.queries.GetBatchesByLabIdQuery;
import com.iotech.qualitrack.platform.batch.domain.model.queries.GetBatchesByStatusQuery;
import com.iotech.qualitrack.platform.batch.domain.model.valueobjects.BatchStatus;
import com.iotech.qualitrack.platform.batch.interfaces.rest.resources.BatchResource;
import com.iotech.qualitrack.platform.batch.interfaces.rest.resources.CreateBatchResource;
import com.iotech.qualitrack.platform.batch.interfaces.rest.resources.UpdateBatchStatusResource;
import com.iotech.qualitrack.platform.batch.interfaces.rest.transform.BatchResourceFromEntityAssembler;
import com.iotech.qualitrack.platform.batch.interfaces.rest.transform.CreateBatchCommandFromResourceAssembler;
import com.iotech.qualitrack.platform.batch.interfaces.rest.transform.UpdateBatchStatusCommandFromResourceAssembler;
import com.iotech.qualitrack.platform.shared.application.result.ApplicationError;
import com.iotech.qualitrack.platform.shared.application.result.Result;
import com.iotech.qualitrack.platform.shared.interfaces.rest.transform.ErrorResponseAssembler;
import com.iotech.qualitrack.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller that exposes production batch resources.
 */
@RestController
@RequestMapping(value = "/api/v1/batches", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Batches", description = "Batch management endpoints")
public class BatchController {

    private final BatchCommandService batchCommandService;
    private final BatchQueryService batchQueryService;

    public BatchController(BatchCommandService batchCommandService, BatchQueryService batchQueryService) {
        this.batchCommandService = batchCommandService;
        this.batchQueryService = batchQueryService;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @org.springframework.security.access.prepost.PreAuthorize("@tenantAccess.allows('labId', #resource.labId()) && @tenantAccess.allows('productId', #resource.productId())")
    @Operation(summary = "Create production batch")
    public ResponseEntity<?> createBatch(@RequestBody CreateBatchResource resource) {
        var command = CreateBatchCommandFromResourceAssembler.toCommandFromResource(resource);

        var result = batchCommandService.handle(command)
                .flatMap(batchId -> batchQueryService.handle(new GetBatchByIdQuery(batchId))
                        .<Result<Batch, ApplicationError>>map(Result::success)
                        .orElseGet(() -> Result.failure(ApplicationError.notFound("Batch", batchId))));

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                BatchResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{batchId}")
    @Operation(summary = "Get batch by ID")
    public ResponseEntity<BatchResource> getBatchById(
            @PathVariable
            @Parameter(description = "Batch numeric identifier", example = "1", required = true)
            Long batchId
    ) {
        var batch = batchQueryService.handle(new GetBatchByIdQuery(batchId));

        if (batch.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(BatchResourceFromEntityAssembler.toResourceFromEntity(batch.get()));
    }

    @GetMapping(params = "labId")
    @Operation(summary = "Get batches by laboratory")
    public ResponseEntity<List<BatchResource>> getBatchesByLabId(
            @RequestParam
            @Parameter(description = "Laboratory numeric identifier", example = "1", required = true)
            Long labId
    ) {
        var batches = batchQueryService.handle(new GetBatchesByLabIdQuery(labId));

        var resources = batches.stream()
                .map(BatchResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }

    @GetMapping(params = "status")
    @Operation(summary = "Get batches by status")
    public ResponseEntity<List<BatchResource>> getBatchesByStatus(
            @RequestParam
            @Parameter(description = "Batch lifecycle status", example = "PENDING", required = true)
            BatchStatus status,
            @org.springframework.security.core.annotation.AuthenticationPrincipal
            com.iotech.qualitrack.platform.iam.infrastructure.authorization.sfs.model.UserDetailsImpl user
    ) {
        var batches = batchQueryService.handle(new GetBatchesByLabIdQuery(user.getLaboratoryId())).stream()
                .filter(batch -> batch.getStatus() == status).toList();

        var resources = batches.stream()
                .map(BatchResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }

    @PatchMapping(value = "/{batchId}", consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Update batch status")
    public ResponseEntity<?> updateBatchStatus(
            @PathVariable
            @Parameter(description = "Batch numeric identifier", example = "1", required = true)
            Long batchId,
            @RequestBody UpdateBatchStatusResource resource
    ) {
        if (resource.status() == null) {
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(
                    ApplicationError.validationError("Batch", "status is required")
            );
        }

        var result = switch (resource.status()) {
            case RELEASED -> batchCommandService.handle(
                    UpdateBatchStatusCommandFromResourceAssembler.toReleaseCommandFromResource(batchId, resource)
            );

            case REJECTED -> batchCommandService.handle(
                    UpdateBatchStatusCommandFromResourceAssembler.toRejectCommandFromResource(batchId, resource)
            );

            default -> null;
        };

        if (result == null) {
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(
                    ApplicationError.validationError(
                            "Batch",
                            "Only status RELEASED or REJECTED is supported for batch status updates"
                    )
            );
        }

        var responseResult = result.flatMap(updatedBatchId -> batchQueryService.handle(new GetBatchByIdQuery(updatedBatchId))
                .<Result<Batch, ApplicationError>>map(Result::success)
                .orElseGet(() -> Result.failure(ApplicationError.notFound("Batch", updatedBatchId))));

        return ResponseEntityAssembler.toResponseEntityFromResult(
                responseResult,
                BatchResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }
}
