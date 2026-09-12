package com.iotech.qualitrack.platform.laboratory.interfaces.rest;

import com.iotech.qualitrack.platform.laboratory.application.commandservices.RawMaterialCommandService;
import com.iotech.qualitrack.platform.laboratory.application.queryservices.RawMaterialQueryService;
import com.iotech.qualitrack.platform.laboratory.domain.model.queries.GetLowStockMaterialsByLabIdQuery;
import com.iotech.qualitrack.platform.laboratory.domain.model.queries.GetRawMaterialsByLabIdQuery;
import com.iotech.qualitrack.platform.laboratory.interfaces.rest.resources.CreateRawMaterialResource;
import com.iotech.qualitrack.platform.laboratory.interfaces.rest.resources.RawMaterialResource;
import com.iotech.qualitrack.platform.laboratory.interfaces.rest.transform.CreateRawMaterialCommandFromResourceAssembler;
import com.iotech.qualitrack.platform.laboratory.interfaces.rest.transform.RawMaterialResourceFromEntityAssembler;
import com.iotech.qualitrack.platform.shared.interfaces.rest.resources.MessageResource;
import com.iotech.qualitrack.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/laboratories/{laboratoryId}/raw-materials", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Laboratories", description = "Laboratory management endpoints")
public class LaboratoryRawMaterialsController {

    private final RawMaterialCommandService rawMaterialCommandService;
    private final RawMaterialQueryService rawMaterialQueryService;

    public LaboratoryRawMaterialsController(RawMaterialCommandService rawMaterialCommandService,
                                            RawMaterialQueryService rawMaterialQueryService) {
        this.rawMaterialCommandService = rawMaterialCommandService;
        this.rawMaterialQueryService = rawMaterialQueryService;
    }

    @PostMapping
    public ResponseEntity<?> createRawMaterial(
            @PathVariable Long laboratoryId,
            @RequestBody CreateRawMaterialResource resource
    ) {
        var command = CreateRawMaterialCommandFromResourceAssembler.toCommandFromResource(laboratoryId, resource);

        var result = rawMaterialCommandService.handle(command)
                .map(materialId -> new MessageResource("Raw material registered successfully with ID: " + materialId));

        return ResponseEntityAssembler.toResponseEntityFromResult(result, message -> message, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RawMaterialResource>> getRawMaterials(
            @PathVariable Long laboratoryId,
            @RequestParam(required = false, defaultValue = "false") boolean lowStock
    ) {
        var materials = lowStock
                ? rawMaterialQueryService.handle(new GetLowStockMaterialsByLabIdQuery(laboratoryId))
                : rawMaterialQueryService.handle(new GetRawMaterialsByLabIdQuery(laboratoryId));

        var resources = materials.stream()
                .map(RawMaterialResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }
}