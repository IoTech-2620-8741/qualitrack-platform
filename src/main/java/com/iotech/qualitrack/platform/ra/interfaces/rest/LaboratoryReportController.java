package com.iotech.qualitrack.platform.ra.interfaces.rest;

import com.iotech.qualitrack.platform.ra.application.commandservices.RaCommandService;
import com.iotech.qualitrack.platform.ra.application.queryservices.RaQueryService;
import com.iotech.qualitrack.platform.ra.domain.model.aggregates.AuditReport;
import com.iotech.qualitrack.platform.ra.domain.model.queries.GetAuditReportsByLaboratoryIdQuery;
import com.iotech.qualitrack.platform.ra.domain.model.valueobjects.ReportFormat;
import com.iotech.qualitrack.platform.ra.interfaces.rest.resources.AuditReportResource;
import com.iotech.qualitrack.platform.ra.interfaces.rest.resources.GenerateComplianceReportResource;
import com.iotech.qualitrack.platform.ra.interfaces.rest.transform.AuditReportResourceFromEntityAssembler;
import com.iotech.qualitrack.platform.ra.interfaces.rest.transform.GenerateComplianceReportCommandFromResourceAssembler;
import com.iotech.qualitrack.platform.shared.application.result.ApplicationError;
import com.iotech.qualitrack.platform.shared.application.result.Result;
import com.iotech.qualitrack.platform.shared.interfaces.rest.transform.ErrorResponseAssembler;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller that exposes laboratory report endpoints.
 */
@RestController
@RequestMapping(value = "/api/v1/laboratories/{laboratoryId}")
public class LaboratoryReportController {

    private final RaCommandService raCommandService;
    private final RaQueryService raQueryService;

    public LaboratoryReportController(
            RaCommandService raCommandService,
            RaQueryService raQueryService
    ) {
        this.raCommandService = raCommandService;
        this.raQueryService = raQueryService;
    }

    @PostMapping(value = "/compliance-reports", consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Generate laboratory compliance report")
    public ResponseEntity<?> generateComplianceReport(
            @PathVariable Long laboratoryId,
            @RequestBody GenerateComplianceReportResource resource
    ) {
        var command = GenerateComplianceReportCommandFromResourceAssembler.toCommandFromResource(
                laboratoryId,
                resource
        );

        var result = raCommandService.handle(command);

        return toFileResponse(
                result,
                "compliance-report-%d.%s".formatted(
                        laboratoryId,
                        toExtensionFromFormat(resource.format())
                ),
                resource.format()
        );
    }

    @GetMapping(value = "/reports", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Get audit reports by laboratory")
    public ResponseEntity<List<AuditReportResource>> getAuditReportsByLaboratoryId(
            @PathVariable Long laboratoryId
    ) {
        var reports = raQueryService.handle(new GetAuditReportsByLaboratoryIdQuery(laboratoryId));

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