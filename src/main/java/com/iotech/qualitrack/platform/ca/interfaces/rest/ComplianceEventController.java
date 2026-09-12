package com.iotech.qualitrack.platform.ca.interfaces.rest;

import com.iotech.qualitrack.platform.ca.application.queryservices.CaQueryService;
import com.iotech.qualitrack.platform.ca.domain.model.queries.GetComplianceEventsByRelatedEntityIdQuery;
import com.iotech.qualitrack.platform.ca.domain.model.valueobjects.ComplianceEventSubject;
import com.iotech.qualitrack.platform.ca.interfaces.rest.resources.ComplianceEventResource;
import com.iotech.qualitrack.platform.ca.interfaces.rest.transform.ComplianceEventResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller that exposes compliance audit event resources.
 */
@RestController
@RequestMapping(value = "/api/v1", produces = APPLICATION_JSON_VALUE)
public class ComplianceEventController {

    private final CaQueryService caQueryService;

    public ComplianceEventController(CaQueryService caQueryService) {
        this.caQueryService = caQueryService;
    }

    @GetMapping(value = "/equipments/{equipmentId}/compliance-events")
    @Operation(
            summary = "Get equipment compliance events",
            tags = {"Equipment"}
    )
    public ResponseEntity<List<ComplianceEventResource>> getEquipmentComplianceEvents(
            @PathVariable Long equipmentId
    ) {
        return ResponseEntity.ok(getComplianceEventResources(equipmentId, ComplianceEventSubject.EQUIPMENT));
    }

    @GetMapping(value = "/batches/{batchId}/compliance-events")
    @Operation(
            summary = "Get batch compliance events",
            tags = {"Batches"}
    )
    public ResponseEntity<List<ComplianceEventResource>> getBatchComplianceEvents(
            @PathVariable Long batchId
    ) {
        return ResponseEntity.ok(getComplianceEventResources(batchId, ComplianceEventSubject.BATCH));
    }

    private List<ComplianceEventResource> getComplianceEventResources(Long relatedEntityId, ComplianceEventSubject subject) {
        var events = caQueryService.handle(
                new GetComplianceEventsByRelatedEntityIdQuery(relatedEntityId, subject)
        );

        return events.stream()
                .map(ComplianceEventResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
    }
}
