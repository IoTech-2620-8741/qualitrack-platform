package com.iotech.qualitrack.platform.ra.interfaces.rest;

import com.iotech.qualitrack.platform.ra.application.queryservices.RaQueryService;
import com.iotech.qualitrack.platform.ra.domain.model.queries.GetDeviationTrendsByEquipmentIdQuery;
import com.iotech.qualitrack.platform.ra.interfaces.rest.resources.DeviationTrendResource;
import com.iotech.qualitrack.platform.ra.interfaces.rest.transform.DeviationTrendResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Deviation trends of an equipment of the laboratory.
 */
@RestController
@RequestMapping(value = "/api/v1/laboratories/{laboratoryId}/equipments/{equipmentId}/deviation-trends",
        produces = APPLICATION_JSON_VALUE)
public class DeviationTrendController {

    private final RaQueryService raQueryService;

    public DeviationTrendController(RaQueryService raQueryService) {
        this.raQueryService = raQueryService;
    }

    @GetMapping
    @Operation(summary = "Get the deviation trends of an equipment")
    public ResponseEntity<List<DeviationTrendResource>> getDeviationTrendsByEquipmentId(@PathVariable Long laboratoryId,
                                                                                        @PathVariable Long equipmentId) {
        var resources = raQueryService.handle(new GetDeviationTrendsByEquipmentIdQuery(equipmentId)).stream()
                .map(DeviationTrendResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }
}
