package com.iotech.qualitrack.platform.equipment.interfaces.rest;

import com.iotech.qualitrack.platform.equipment.application.commandservices.BpmConfigCommandService;
import com.iotech.qualitrack.platform.equipment.application.queryservices.BpmConfigQueryService;
import com.iotech.qualitrack.platform.equipment.domain.model.queries.GetBpmParameterConfigsByEquipmentIdQuery;
import com.iotech.qualitrack.platform.equipment.interfaces.rest.resources.BpmParameterConfigResource;
import com.iotech.qualitrack.platform.equipment.interfaces.rest.resources.ConfigureBpmResource;
import com.iotech.qualitrack.platform.equipment.interfaces.rest.transform.BpmConfigResourceFromEntityAssembler;
import com.iotech.qualitrack.platform.equipment.interfaces.rest.transform.ConfigureBpmCommandFromResourceAssembler;
import com.iotech.qualitrack.platform.shared.interfaces.rest.resources.MessageResource;
import com.iotech.qualitrack.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/equipments/{equipmentId}/bpm-configs", produces = APPLICATION_JSON_VALUE)
public class EquipmentBpmConfigController {

    private final BpmConfigCommandService bpmConfigCommandService;
    private final BpmConfigQueryService bpmConfigQueryService;

    public EquipmentBpmConfigController(BpmConfigCommandService bpmConfigCommandService,
                                        BpmConfigQueryService bpmConfigQueryService) {
        this.bpmConfigCommandService = bpmConfigCommandService;
        this.bpmConfigQueryService = bpmConfigQueryService;
    }

    @PostMapping
    public ResponseEntity<?> configureBpmParameter(
            @PathVariable Long equipmentId,
            @RequestBody ConfigureBpmResource resource
    ) {
        var command = ConfigureBpmCommandFromResourceAssembler.toCommandFromResource(equipmentId, resource);

        var result = bpmConfigCommandService.handle(command)
                .map(configId -> new MessageResource("BPM Parameter configuration saved successfully with ID: " + configId));

        return ResponseEntityAssembler.toResponseEntityFromResult(result, message -> message, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BpmParameterConfigResource>> getBpmConfigsByEquipmentId(
            @PathVariable Long equipmentId
    ) {
        var configs = bpmConfigQueryService.handle(new GetBpmParameterConfigsByEquipmentIdQuery(equipmentId));

        var resources = configs.stream()
                .map(BpmConfigResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }
}