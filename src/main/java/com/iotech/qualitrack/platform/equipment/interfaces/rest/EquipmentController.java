package com.iotech.qualitrack.platform.equipment.interfaces.rest;

import com.iotech.qualitrack.platform.equipment.application.commandservices.EquipmentCommandService;
import com.iotech.qualitrack.platform.equipment.application.queryservices.EquipmentQueryService;
import com.iotech.qualitrack.platform.equipment.domain.model.aggregates.Equipment;
import com.iotech.qualitrack.platform.equipment.domain.model.queries.GetEquipmentByIdQuery;
import com.iotech.qualitrack.platform.equipment.domain.model.queries.GetEquipmentByLabIdQuery;
import com.iotech.qualitrack.platform.equipment.interfaces.rest.resources.EquipmentResource;
import com.iotech.qualitrack.platform.equipment.interfaces.rest.resources.RegisterEquipmentResource;
import com.iotech.qualitrack.platform.equipment.interfaces.rest.transform.EquipmentResourceFromEntityAssembler;
import com.iotech.qualitrack.platform.equipment.interfaces.rest.transform.RegisterEquipmentCommandFromResourceAssembler;
import com.iotech.qualitrack.platform.shared.application.result.ApplicationError;
import com.iotech.qualitrack.platform.shared.application.result.Result;
import com.iotech.qualitrack.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/equipments", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Equipment", description = "Equipment management endpoints")
public class EquipmentController {

    private final EquipmentCommandService equipmentCommandService;
    private final EquipmentQueryService equipmentQueryService;

    public EquipmentController(EquipmentCommandService equipmentCommandService,
                               EquipmentQueryService equipmentQueryService) {
        this.equipmentCommandService = equipmentCommandService;
        this.equipmentQueryService = equipmentQueryService;
    }

    @PostMapping
    @org.springframework.security.access.prepost.PreAuthorize("@tenantAccess.allows('laboratoryId', #resource.laboratoryId())")
    public ResponseEntity<?> registerEquipment(@RequestBody RegisterEquipmentResource resource) {
        var command = RegisterEquipmentCommandFromResourceAssembler.toCommandFromResource(resource);

        var result = equipmentCommandService.handle(command)
                .flatMap(equipmentId -> equipmentQueryService.handle(new GetEquipmentByIdQuery(equipmentId))
                        .<Result<Equipment, ApplicationError>>map(Result::success)
                        .orElseGet(() -> Result.failure(ApplicationError.notFound("Equipment", equipmentId))));

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                EquipmentResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{equipmentId}")
    public ResponseEntity<EquipmentResource> getEquipmentById(@PathVariable Long equipmentId) {
        var equipment = equipmentQueryService.handle(new GetEquipmentByIdQuery(equipmentId));

        if (equipment.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(EquipmentResourceFromEntityAssembler.toResourceFromEntity(equipment.get()));
    }

    @GetMapping
    public ResponseEntity<List<EquipmentResource>> getEquipmentByLabId(@RequestParam Long labId) {
        var equipment = equipmentQueryService.handle(new GetEquipmentByLabIdQuery(labId));

        var resources = equipment.stream()
                .map(EquipmentResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }
}
