package com.iotech.qualitrack.platform.inventory.interfaces.rest;

import com.iotech.qualitrack.platform.inventory.application.commandservices.InventoryImportService;
import com.iotech.qualitrack.platform.inventory.application.commandservices.InventoryCommandService;
import com.iotech.qualitrack.platform.inventory.application.queryservices.InventoryQueryService;
import com.iotech.qualitrack.platform.inventory.domain.model.queries.*;
import com.iotech.qualitrack.platform.inventory.domain.model.valueobjects.MaterialStockSummary;
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

@RestController
@RequestMapping("/api/v1/laboratories/{laboratoryId}/inventory")
@PreAuthorize("@tenantAccess.allows('laboratoryId', #laboratoryId)")
@Tag(name = "Inventory", description = "Material catalogue, reviewed supplier receipts and immutable stock movements")
public class InventoryController {
    private final InventoryCommandService commands;
    private final InventoryQueryService queries;
    private final InventoryContextFacade facade;
    private final InventoryImportService imports;
    private final Clock clock;
    public InventoryController(InventoryCommandService commands, InventoryQueryService queries,
            InventoryContextFacade facade, InventoryImportService imports, Clock inventoryClock) {
        this.commands = commands;
        this.queries = queries;
        this.facade = facade;
        this.imports = imports;
        this.clock = inventoryClock;
    }

    @GetMapping("/materials") @Operation(summary = "List material catalogue with derived usable and physical stock")
    public List<RawMaterialResource> materials(@PathVariable Long laboratoryId) {
        return queries.handle(new GetInventoryMaterialsQuery(laboratoryId)).stream()
            .map(RawMaterialResourceFromEntityAssembler::toResourceFromEntity).toList();
    }

    @PostMapping("/materials") @Operation(summary = "Create catalogue entry without independent stock")
    public ResponseEntity<?> create(@PathVariable Long laboratoryId, @Valid @RequestBody SaveRawMaterialResource resource) {
        var result = commands.handle(SaveRawMaterialCommandFromResourceAssembler.toCommandFromResource(laboratoryId, null, resource))
            .map(material -> summary(laboratoryId, material.getId()));
        return ResponseEntityAssembler.toResponseEntityFromResult(result, RawMaterialResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.CREATED);
    }

    @PutMapping("/materials/{materialId}") @Operation(summary = "Update catalogue metadata; unit remains immutable")
    public ResponseEntity<?> update(@PathVariable Long laboratoryId, @PathVariable Long materialId, @Valid @RequestBody SaveRawMaterialResource resource) {
        var result = commands.handle(SaveRawMaterialCommandFromResourceAssembler.toCommandFromResource(laboratoryId, materialId, resource))
            .map(material -> summary(laboratoryId, material.getId()));
        return ResponseEntityAssembler.toResponseEntityFromResult(result, RawMaterialResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.OK);
    }

    @GetMapping("/materials/{materialId}/receipts") @Operation(summary = "List receipts, including blocked and expired receipts")
    public List<ReceiptResource> receipts(@PathVariable Long laboratoryId, @PathVariable Long materialId) {
        return queries.handle(new GetMaterialReceiptsQuery(laboratoryId, materialId)).stream()
            .map(receipt -> ReceiptResourceFromEntityAssembler.toResourceFromEntity(receipt, today())).toList();
    }

    @GetMapping("/materials/{materialId}/usable-receipts") @Operation(summary = "List RELEASED, non-expired receipts with available stock")
    public List<AvailableReceiptResource> usable(@PathVariable Long laboratoryId, @PathVariable Long materialId) {
        return facade.findUsableReceipts(laboratoryId, materialId, today()).stream()
            .map(AvailableReceiptResourceFromEntityAssembler::toResourceFromEntity).toList();
    }

    @PostMapping("/materials/{materialId}/receipts") @Operation(summary = "Register a supplier receipt in QUARANTINED status")
    public ResponseEntity<?> receive(@PathVariable Long laboratoryId, @PathVariable Long materialId, @Valid @RequestBody ReceiveRawMaterialBatchResource resource) {
        var result = commands.handle(ReceiveRawMaterialBatchCommandFromResourceAssembler.toCommandFromResource(laboratoryId, materialId, resource));
        return ResponseEntityAssembler.toResponseEntityFromResult(result, receipt -> ReceiptResourceFromEntityAssembler.toResourceFromEntity(receipt, today()), HttpStatus.CREATED);
    }

    @PostMapping("/receipts/{receiptId}/reviews")
    @PreAuthorize("@tenantAccess.allows('laboratoryId', #laboratoryId) and hasAnyAuthority('ROLE_QA_MANAGER', 'ROLE_ADMIN')")
    @Operation(summary = "Review receipt with a mandatory reason and authenticated reviewer")
    public ResponseEntity<?> review(@PathVariable Long laboratoryId, @PathVariable Long receiptId, @Valid @RequestBody ReviewRawMaterialBatchResource resource) {
        var result = commands.handle(ReviewRawMaterialBatchCommandFromResourceAssembler.toCommandFromResource(laboratoryId, receiptId, resource));
        return ResponseEntityAssembler.toResponseEntityFromResult(result, receipt -> ReceiptResourceFromEntityAssembler.toResourceFromEntity(receipt, today()), HttpStatus.OK);
    }

    @PostMapping("/consumptions") @Operation(summary = "Consume receipt atomically; retry the same operationId to avoid double consumption. Invalid units: 400; blocked or insufficient stock: 409.")
    public ResponseEntity<?> consume(@PathVariable Long laboratoryId, @Valid @RequestBody ConsumeRawMaterialBatchResource resource) {
        var result = commands.handle(ConsumeRawMaterialBatchCommandFromResourceAssembler.toCommandFromResource(laboratoryId, resource));
        return ResponseEntityAssembler.toResponseEntityFromResult(result, ReceiptConsumptionResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.CREATED);
    }

    @GetMapping("/materials/{materialId}/movements") @Operation(summary = "Read stock/review audit and affected product batch IDs")
    public List<InventoryMovementResource> history(@PathVariable Long laboratoryId, @PathVariable Long materialId) {
        return queries.handle(new GetMaterialMovementsQuery(laboratoryId, materialId)).stream()
            .map(InventoryMovementResourceFromEntityAssembler::toResourceFromEntity).toList();
    }

    @GetMapping("/legacy-materials") @Operation(summary = "Preview pre-Inventory records pending explicit import")
    public List<LegacyMaterialResource> pending(@PathVariable Long laboratoryId) {
        return queries.handle(new GetPendingLegacyMaterialsQuery(laboratoryId)).stream().map(LegacyMaterialResourceFromEntityAssembler::toResourceFromEntity).toList();
    }

    @PostMapping("/legacy-materials/{legacyId}/import")
    @PreAuthorize("@tenantAccess.allows('laboratoryId', #laboratoryId) and hasAnyAuthority('ROLE_QA_MANAGER', 'ROLE_ADMIN')")
    @Operation(summary = "Import opening balance once, preserving legacy records and requiring quality review")
    public ImportedMaterialResource importMaterial(@PathVariable Long laboratoryId, @PathVariable Long legacyId) {
        return new ImportedMaterialResource(imports.importMaterial(laboratoryId, legacyId));
    }

    private LocalDate today() { return LocalDate.now(clock); }

    private MaterialStockSummary summary(Long lab, Long id) {
        return queries.handle(new GetInventoryMaterialsQuery(lab)).stream().filter(material -> id.equals(material.id())).findFirst().orElseThrow();
    }
}
