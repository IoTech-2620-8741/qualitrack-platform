package com.iotech.qualitrack.platform.ca.interfaces.rest;

import com.iotech.qualitrack.platform.ca.application.queryservices.CaQueryService;
import com.iotech.qualitrack.platform.ca.domain.model.queries.GetComplianceEventsByRelatedEntityIdQuery;
import com.iotech.qualitrack.platform.ca.interfaces.rest.resources.ComplianceEventResource;
import com.iotech.qualitrack.platform.ca.interfaces.rest.transform.ComplianceEventResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller that exposes raw material compliance event resources.
 */
@RestController
@RequestMapping(value = "/api/v1/raw-materials/{rawMaterialId}/compliance-events", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Raw Materials", description = "Raw material compliance event endpoints")
public class RawMaterialComplianceEventController {

    private final CaQueryService caQueryService;

    public RawMaterialComplianceEventController(CaQueryService caQueryService) {
        this.caQueryService = caQueryService;
    }

    /**
     * Retrieves compliance events related to a raw material.
     *
     * @param rawMaterialId the raw material numeric identifier
     * @return the compliance event resources
     */
    @GetMapping
    @Operation(summary = "Get raw material compliance events")
    public ResponseEntity<List<ComplianceEventResource>> getComplianceEventsByRawMaterialId(
            @PathVariable Long rawMaterialId
    ) {
        var events = caQueryService.handle(
                new GetComplianceEventsByRelatedEntityIdQuery(rawMaterialId,
                        com.iotech.qualitrack.platform.ca.domain.model.valueobjects.ComplianceEventSubject.RAW_MATERIAL)
        );

        var resources = events.stream()
                .map(ComplianceEventResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }
}
