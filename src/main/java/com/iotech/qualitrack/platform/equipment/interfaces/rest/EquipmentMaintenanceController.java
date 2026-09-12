package com.iotech.qualitrack.platform.equipment.interfaces.rest;

import com.iotech.qualitrack.platform.equipment.application.commandservices.MaintenanceCommandService;
import com.iotech.qualitrack.platform.equipment.application.queryservices.MaintenanceQueryService;
import com.iotech.qualitrack.platform.equipment.domain.model.queries.GetMaintenanceByEquipmentIdQuery;
import com.iotech.qualitrack.platform.equipment.interfaces.rest.resources.MaintenanceRecordResource;
import com.iotech.qualitrack.platform.equipment.interfaces.rest.resources.RegisterMaintenanceResource;
import com.iotech.qualitrack.platform.equipment.interfaces.rest.transform.MaintenanceResourceFromEntityAssembler;
import com.iotech.qualitrack.platform.equipment.interfaces.rest.transform.RegisterMaintenanceCommandFromResourceAssembler;
import com.iotech.qualitrack.platform.shared.interfaces.rest.resources.MessageResource;
import com.iotech.qualitrack.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/equipments/{equipmentId}/maintenance-records", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Equipment", description = "Equipment management endpoints")
public class EquipmentMaintenanceController {

    private final MaintenanceCommandService maintenanceCommandService;
    private final MaintenanceQueryService maintenanceQueryService;

    public EquipmentMaintenanceController(MaintenanceCommandService maintenanceCommandService,
                                          MaintenanceQueryService maintenanceQueryService) {
        this.maintenanceCommandService = maintenanceCommandService;
        this.maintenanceQueryService = maintenanceQueryService;
    }

    @PostMapping
    public ResponseEntity<?> registerMaintenance(
            @PathVariable Long equipmentId,
            @RequestBody RegisterMaintenanceResource resource
    ) {
        var command = RegisterMaintenanceCommandFromResourceAssembler.toCommandFromResource(equipmentId, resource);

        var result = maintenanceCommandService.handle(command)
                .map(recordId -> new MessageResource("Maintenance record registered successfully with ID: " + recordId));

        return ResponseEntityAssembler.toResponseEntityFromResult(result, message -> message, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MaintenanceRecordResource>> getMaintenanceHistory(
            @PathVariable Long equipmentId
    ) {
        var records = maintenanceQueryService.handle(new GetMaintenanceByEquipmentIdQuery(equipmentId));

        var resources = records.stream()
                .map(MaintenanceResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }
}