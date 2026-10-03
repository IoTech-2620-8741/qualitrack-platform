package com.iotech.qualitrack.platform.batch.interfaces.rest;

import com.iotech.qualitrack.platform.batch.application.commandservices.BatchParticipationCommandService;
import com.iotech.qualitrack.platform.batch.application.commandservices.RawMaterialUsageCommandService;
import com.iotech.qualitrack.platform.batch.domain.model.commands.RegisterEquipmentUsageCommand;
import com.iotech.qualitrack.platform.batch.domain.model.commands.RegisterRawMaterialUsageCommand;
import com.iotech.qualitrack.platform.batch.domain.model.commands.RegisterStaffParticipationCommand;
import com.iotech.qualitrack.platform.batch.interfaces.rest.resources.*;
import com.iotech.qualitrack.platform.batch.interfaces.rest.transform.BatchParticipationResourceFromEntityAssembler;
import com.iotech.qualitrack.platform.batch.interfaces.rest.transform.RawMaterialUsageResourceFromEntityAssembler;
import com.iotech.qualitrack.platform.shared.interfaces.rest.resources.ErrorResource;
import com.iotech.qualitrack.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller for the resources that take part in the manufacturing of a product batch (TS65-TS67).
 *
 * <p>Tenant isolation of every path identifier is enforced by the IAM tenant interceptor and the batch
 * must belong to the product of the environment in the path. Authorized laboratory staff register the
 * manufacturing records (US75-US77).</p>
 */
@RestController
@RequestMapping(value = "/api/v1/laboratories/{laboratoryId}/environments/{environmentId}/products/{productId}/batches/{batchId}",
        produces = APPLICATION_JSON_VALUE)
@PreAuthorize("@tenantAccess.allows('laboratoryId', #laboratoryId)")
public class BatchManufacturingController {

    private final RawMaterialUsageCommandService rawMaterialUsageCommandService;
    private final BatchParticipationCommandService participationCommandService;

    public BatchManufacturingController(RawMaterialUsageCommandService rawMaterialUsageCommandService,
                                        BatchParticipationCommandService participationCommandService) {
        this.rawMaterialUsageCommandService = rawMaterialUsageCommandService;
        this.participationCommandService = participationCommandService;
    }

    @PostMapping(value = "/raw-material-usages", consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Register a raw material usage",
            description = "Consumes the amount from a released Inventory lot and keeps which lot the batch used. "
                    + "Retrying with the same operationId returns the original usage.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Raw material consumed and usage recorded",
                    content = @Content(schema = @Schema(implementation = RawMaterialUsageResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid amount or unit", content = @Content(schema = @Schema(implementation = ErrorResource.class))),
            @ApiResponse(responseCode = "403", description = "Batch not available to the account"),
            @ApiResponse(responseCode = "404", description = "Batch or raw material lot not found", content = @Content(schema = @Schema(implementation = ErrorResource.class))),
            @ApiResponse(responseCode = "409", description = "Closed batch, unusable lot, insufficient stock or operationId reused with other values",
                    content = @Content(schema = @Schema(implementation = ErrorResource.class)))
    })
    public ResponseEntity<?> registerRawMaterialUsage(@PathVariable Long laboratoryId, @PathVariable Long environmentId,
                                                      @PathVariable Long productId, @PathVariable Long batchId,
                                                      @Valid @RequestBody RegisterRawMaterialUsageResource resource) {
        var command = new RegisterRawMaterialUsageCommand(laboratoryId, environmentId, productId, batchId,
                resource.rawMaterialBatchId(), resource.amountUsed(), resource.unit(), resource.operationId());
        return ResponseEntityAssembler.toResponseEntityFromResult(rawMaterialUsageCommandService.handle(command),
                RawMaterialUsageResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.CREATED);
    }

    @PostMapping(value = "/equipment-usages", consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Register an equipment usage", description = "Associates an operational equipment of the laboratory with the batch.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Equipment associated",
                    content = @Content(schema = @Schema(implementation = EquipmentUsageResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content(schema = @Schema(implementation = ErrorResource.class))),
            @ApiResponse(responseCode = "403", description = "Batch not available to the account"),
            @ApiResponse(responseCode = "404", description = "Batch or equipment not found in the laboratory", content = @Content(schema = @Schema(implementation = ErrorResource.class))),
            @ApiResponse(responseCode = "409", description = "Closed batch, equipment in maintenance or out of service, or already associated",
                    content = @Content(schema = @Schema(implementation = ErrorResource.class)))
    })
    public ResponseEntity<?> registerEquipmentUsage(@PathVariable Long laboratoryId, @PathVariable Long environmentId,
                                                    @PathVariable Long productId, @PathVariable Long batchId,
                                                    @Valid @RequestBody RegisterEquipmentUsageResource resource) {
        var command = new RegisterEquipmentUsageCommand(laboratoryId, environmentId, productId, batchId, resource.equipmentId());
        return ResponseEntityAssembler.toResponseEntityFromResult(participationCommandService.handle(command),
                BatchParticipationResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.CREATED);
    }

    @PostMapping(value = "/staff-participations", consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Register a staff participation", description = "Associates a staff member of the laboratory with the batch.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Staff member associated",
                    content = @Content(schema = @Schema(implementation = StaffParticipationResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content(schema = @Schema(implementation = ErrorResource.class))),
            @ApiResponse(responseCode = "403", description = "Batch not available to the account"),
            @ApiResponse(responseCode = "404", description = "Batch or staff member not found in the laboratory", content = @Content(schema = @Schema(implementation = ErrorResource.class))),
            @ApiResponse(responseCode = "409", description = "Closed batch or staff member already associated",
                    content = @Content(schema = @Schema(implementation = ErrorResource.class)))
    })
    public ResponseEntity<?> registerStaffParticipation(@PathVariable Long laboratoryId, @PathVariable Long environmentId,
                                                        @PathVariable Long productId, @PathVariable Long batchId,
                                                        @Valid @RequestBody RegisterStaffParticipationResource resource) {
        var command = new RegisterStaffParticipationCommand(laboratoryId, environmentId, productId, batchId, resource.staffId());
        return ResponseEntityAssembler.toResponseEntityFromResult(participationCommandService.handle(command),
                BatchParticipationResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.CREATED);
    }
}
