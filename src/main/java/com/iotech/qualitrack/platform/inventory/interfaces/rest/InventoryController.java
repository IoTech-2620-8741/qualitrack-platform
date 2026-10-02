package com.iotech.qualitrack.platform.inventory.interfaces.rest;

import com.iotech.qualitrack.platform.inventory.application.commandservices.InventoryCommandService;
import com.iotech.qualitrack.platform.inventory.application.queryservices.InventoryQueryService;
import com.iotech.qualitrack.platform.inventory.domain.model.queries.*;
import com.iotech.qualitrack.platform.inventory.domain.model.valueobjects.NearExpiryPeriod;
import com.iotech.qualitrack.platform.inventory.interfaces.acl.InventoryContextFacade;
import com.iotech.qualitrack.platform.inventory.interfaces.rest.resources.*;
import com.iotech.qualitrack.platform.inventory.interfaces.rest.transform.*;
import com.iotech.qualitrack.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.*;
import java.util.List;

/**
 * Laboratory-wide inventory endpoints kept for existing clients.
 *
 * <p>Raw materials, lots and reviews are managed per environment through
 * {@link EnvironmentInventoryController}. The read endpoints below are deprecated and remain for the
 * mobile application; consumption stays here until Product Batch Management exposes raw material usages.</p>
 */
@RestController
@RequestMapping("/api/v1/laboratories/{laboratoryId}/inventory")
@PreAuthorize("@tenantAccess.allows('laboratoryId', #laboratoryId)")
@Tag(name = "Inventory", description = "Raw materials, lots, stock and expiration within an environment")
public class InventoryController {
    private final InventoryCommandService commands;
    private final InventoryQueryService queries;
    private final InventoryContextFacade facade;
    private final Clock clock;
    private final NearExpiryPeriod nearExpiryPeriod;
    public InventoryController(InventoryCommandService commands, InventoryQueryService queries,
            InventoryContextFacade facade, Clock inventoryClock, NearExpiryPeriod inventoryNearExpiryPeriod) {
        this.commands = commands;
        this.queries = queries;
        this.facade = facade;
        this.clock = inventoryClock;
        this.nearExpiryPeriod = inventoryNearExpiryPeriod;
    }

    @GetMapping("/materials")
    @Operation(summary = "List all raw materials of the laboratory", deprecated = true,
        description = "Deprecated: use GET /api/v1/laboratories/{laboratoryId}/environments/{environmentId}/raw-materials.")
    public List<RawMaterialResource> materials(@PathVariable Long laboratoryId) {
        return queries.handle(new GetInventoryMaterialsQuery(laboratoryId)).stream()
            .map(RawMaterialResourceFromEntityAssembler::toResourceFromEntity).toList();
    }

    @GetMapping("/materials/{materialId}/receipts")
    @Operation(summary = "List the lots of a raw material", deprecated = true,
        description = "Deprecated: use GET .../environments/{environmentId}/raw-materials/{rawMaterialId}/batches.")
    public List<ReceiptResource> receipts(@PathVariable Long laboratoryId, @PathVariable Long materialId) {
        return queries.handle(new GetMaterialReceiptsQuery(laboratoryId, materialId)).stream()
            .map(receipt -> ReceiptResourceFromEntityAssembler.toResourceFromEntity(receipt, today(), nearExpiryPeriod)).toList();
    }

    @GetMapping("/materials/{materialId}/usable-receipts")
    @Operation(summary = "List the lots available for consumption", deprecated = true,
        description = "Deprecated: use GET .../environments/{environmentId}/raw-materials/{rawMaterialId}/batches?usable=true.")
    public List<AvailableReceiptResource> usable(@PathVariable Long laboratoryId, @PathVariable Long materialId) {
        return facade.findUsableReceipts(laboratoryId, materialId, today()).stream()
            .map(AvailableReceiptResourceFromEntityAssembler::toResourceFromEntity).toList();
    }

    @PostMapping("/consumptions") @Operation(summary = "Consume receipt atomically; retry the same operationId to avoid double consumption. Invalid units: 400; blocked or insufficient stock: 409.")
    public ResponseEntity<?> consume(@PathVariable Long laboratoryId, @Valid @RequestBody ConsumeRawMaterialBatchResource resource) {
        var result = commands.handle(ConsumeRawMaterialBatchCommandFromResourceAssembler.toCommandFromResource(laboratoryId, resource));
        return ResponseEntityAssembler.toResponseEntityFromResult(result, ReceiptConsumptionResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.CREATED);
    }

    @GetMapping("/materials/{materialId}/movements")
    @Operation(summary = "List the stock and review movements of a raw material", deprecated = true,
        description = "Deprecated: use GET .../environments/{environmentId}/raw-materials/{rawMaterialId}/movements.")
    public List<InventoryMovementResource> history(@PathVariable Long laboratoryId, @PathVariable Long materialId) {
        return queries.handle(new GetMaterialMovementsQuery(laboratoryId, materialId)).stream()
            .map(InventoryMovementResourceFromEntityAssembler::toResourceFromEntity).toList();
    }

    @GetMapping("/legacy-materials") @Operation(summary = "Preview pre-Inventory records pending explicit import into an environment")
    public List<LegacyMaterialResource> pending(@PathVariable Long laboratoryId) {
        return queries.handle(new GetPendingLegacyMaterialsQuery(laboratoryId)).stream().map(LegacyMaterialResourceFromEntityAssembler::toResourceFromEntity).toList();
    }

    private LocalDate today() { return LocalDate.now(clock); }
}
