package com.iotech.qualitrack.platform.batch.interfaces.rest;

import com.iotech.qualitrack.platform.batch.application.queryservices.RawMaterialUsageQueryService;
import com.iotech.qualitrack.platform.batch.domain.model.queries.GetRawMaterialHistoryQuery;
import com.iotech.qualitrack.platform.batch.interfaces.rest.resources.RawMaterialUsageResource;
import com.iotech.qualitrack.platform.batch.interfaces.rest.transform.RawMaterialUsageResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/raw-materials/{rawMaterialId}/usages", produces = "application/json")
@Tag(name = "Raw Material Traceability")
public class RawMaterialHistoryController {
    private final RawMaterialUsageQueryService queries;

    public RawMaterialHistoryController(RawMaterialUsageQueryService queries) {
        this.queries = queries;
    }

    @GetMapping
    @PreAuthorize("@tenantAccess.allows('rawMaterialId', #rawMaterialId)")
    @Operation(summary = "Get material consumption history", description = "Newest first. Each record identifies its production batch. Stock before/after are null for legacy records that did not deduct inventory.")
    public List<RawMaterialUsageResource> history(@PathVariable Long rawMaterialId) {
        return queries.handle(new GetRawMaterialHistoryQuery(rawMaterialId)).stream()
                .map(RawMaterialUsageResourceFromEntityAssembler::toResourceFromEntity).toList();
    }
}
