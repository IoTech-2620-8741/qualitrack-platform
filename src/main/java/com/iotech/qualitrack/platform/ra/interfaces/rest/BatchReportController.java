package com.iotech.qualitrack.platform.ra.interfaces.rest;

import com.iotech.qualitrack.platform.ra.application.commandservices.RaCommandService;
import com.iotech.qualitrack.platform.ra.application.queryservices.RaQueryService;
import com.iotech.qualitrack.platform.ra.domain.model.aggregates.AuditReport;
import com.iotech.qualitrack.platform.ra.domain.model.queries.GetAuditReportsByBatchIdQuery;
import com.iotech.qualitrack.platform.ra.domain.model.valueobjects.ReportFormat;
import com.iotech.qualitrack.platform.ra.interfaces.rest.resources.AuditReportResource;
import com.iotech.qualitrack.platform.ra.interfaces.rest.resources.GenerateBatchReportResource;
import com.iotech.qualitrack.platform.ra.interfaces.rest.transform.AuditReportResourceFromEntityAssembler;
import com.iotech.qualitrack.platform.ra.interfaces.rest.transform.GenerateBatchReportCommandFromResourceAssembler;
import com.iotech.qualitrack.platform.shared.application.result.ApplicationError;
import com.iotech.qualitrack.platform.shared.application.result.Result;
import com.iotech.qualitrack.platform.shared.interfaces.rest.transform.ErrorResponseAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller that exposes batch report endpoints.
 */
@RestController
@RequestMapping(value = "/api/v1/batches/{batchId}/reports")
@Tag(name = "Batches", description = "Batch report endpoints")
public class BatchReportController {

    private final RaCommandService raCommandService;
    private final RaQueryService raQueryService;

    public BatchReportController(
            RaCommandService raCommandService,
            RaQueryService raQueryService
    ) {
        this.raCommandService = raCommandService;
        this.raQueryService = raQueryService;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Generate batch report")
    public ResponseEntity<?> generateBatchReport(
            @PathVariable Long batchId,
            @RequestBody GenerateBatchReportResource resource
    ) {
        var command = GenerateBatchReportCommandFromResourceAssembler.toCommandFromResource(
                batchId,
                resource
        );

        var result = raCommandService.handle(command);

        return toFileResponse(
                result,
                "batch-report-%d.%s".formatted(batchId, toExtensionFromFormat(resource.format())),
                resource.format()
        );
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Get audit reports by batch")
    public ResponseEntity<List<AuditReportResource>> getAuditReportsByBatchId(
            @PathVariable Long batchId
    ) {
        var reports = raQueryService.handle(new GetAuditReportsByBatchIdQuery(batchId));

        return ResponseEntity.ok(toAuditReportResources(reports));
    }

    private static List<AuditReportResource> toAuditReportResources(List<AuditReport> reports) {
        return reports.stream()
                .map(AuditReportResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
    }

    private static ResponseEntity<?> toFileResponse(
            Result<byte[], ApplicationError> result,
            String filename,
            ReportFormat format
    ) {
        return switch (result) {
            case Result.Success<byte[], ApplicationError> success -> ResponseEntity.ok()
                    .contentType(toMediaTypeFromFormat(format))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"%s\"".formatted(filename))
                    .body(success.value());

            case Result.Failure<byte[], ApplicationError> failure ->
                    ErrorResponseAssembler.toErrorResponseFromApplicationError(failure.error());
        };
    }

    private static MediaType toMediaTypeFromFormat(ReportFormat format) {
        if (format == ReportFormat.CSV) return MediaType.parseMediaType("text/csv");
        if (format == ReportFormat.PDF) return MediaType.APPLICATION_PDF;
        return MediaType.APPLICATION_OCTET_STREAM;
    }

    private static String toExtensionFromFormat(ReportFormat format) {
        return format.name().toLowerCase(Locale.ROOT);
    }
}