package com.iotech.qualitrack.platform.tracking.interfaces.rest;

import com.iotech.qualitrack.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import com.iotech.qualitrack.platform.tracking.application.commandservices.TrackingCommandService;
import com.iotech.qualitrack.platform.tracking.application.queryservices.TrackingQueryService;
import com.iotech.qualitrack.platform.tracking.domain.model.entities.EquipmentTelemetryStatus;
import com.iotech.qualitrack.platform.tracking.domain.model.queries.GetEquipmentTelemetryStatusByEquipmentIdQuery;
import com.iotech.qualitrack.platform.tracking.domain.model.queries.GetLatestMeasurementsQuery;
import com.iotech.qualitrack.platform.tracking.domain.model.queries.GetTelemetryHistoryQuery;
import com.iotech.qualitrack.platform.tracking.domain.model.valueobjects.TelemetryStatus;
import com.iotech.qualitrack.platform.tracking.interfaces.rest.resources.EquipmentTelemetryStatusResource;
import com.iotech.qualitrack.platform.tracking.interfaces.rest.resources.MeasurementResource;
import com.iotech.qualitrack.platform.tracking.interfaces.rest.resources.RecordMeasurementResource;
import com.iotech.qualitrack.platform.tracking.interfaces.rest.resources.RecordTelemetryHistoryPointResource;
import com.iotech.qualitrack.platform.tracking.interfaces.rest.resources.TelemetryHistoryPointResource;
import com.iotech.qualitrack.platform.tracking.interfaces.rest.resources.UpdateEquipmentTelemetryStatusResource;
import com.iotech.qualitrack.platform.tracking.interfaces.rest.transform.EquipmentTelemetryStatusResourceFromEntityAssembler;
import com.iotech.qualitrack.platform.tracking.interfaces.rest.transform.MeasurementResourceFromEntityAssembler;
import com.iotech.qualitrack.platform.tracking.interfaces.rest.transform.RecordMeasurementCommandFromResourceAssembler;
import com.iotech.qualitrack.platform.tracking.interfaces.rest.transform.RecordTelemetryHistoryPointCommandFromResourceAssembler;
import com.iotech.qualitrack.platform.tracking.interfaces.rest.transform.TelemetryHistoryPointResourceFromEntityAssembler;
import com.iotech.qualitrack.platform.tracking.interfaces.rest.transform.UpdateEquipmentTelemetryStatusCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller that exposes equipment telemetry resources.
 */
@RestController
@RequestMapping(value = "/api/v1/equipments/{equipmentId}", produces = APPLICATION_JSON_VALUE)
public class TelemetryController {

    private final TrackingCommandService trackingCommandService;
    private final TrackingQueryService trackingQueryService;

    public TelemetryController(
            TrackingCommandService trackingCommandService,
            TrackingQueryService trackingQueryService
    ) {
        this.trackingCommandService = trackingCommandService;
        this.trackingQueryService = trackingQueryService;
    }

    @GetMapping("/telemetry-measurements")
    @Operation(summary = "Get equipment telemetry measurements")
    public ResponseEntity<List<MeasurementResource>> getLatestMeasurements(
            @PathVariable Long equipmentId
    ) {
        var measurements = trackingQueryService.handle(new GetLatestMeasurementsQuery(equipmentId));

        var resources = measurements.stream()
                .map(MeasurementResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }

    @PostMapping(value = "/telemetry-measurements", consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Record equipment telemetry measurement")
    public ResponseEntity<?> recordMeasurement(
            @PathVariable Long equipmentId,
            @RequestBody RecordMeasurementResource resource
    ) {
        var command = RecordMeasurementCommandFromResourceAssembler.toCommandFromResource(
                equipmentId,
                resource
        );
        var result = trackingCommandService.handle(command);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                measurementId -> measurementId,
                HttpStatus.CREATED
        );
    }

    @GetMapping("/telemetry-status")
    @Operation(summary = "Get equipment telemetry status")
    public ResponseEntity<EquipmentTelemetryStatusResource> getEquipmentTelemetryStatus(
            @PathVariable Long equipmentId
    ) {
        var status = trackingQueryService.handle(
                new GetEquipmentTelemetryStatusByEquipmentIdQuery(equipmentId)
        ).orElseGet(() -> EquipmentTelemetryStatus.update(
                equipmentId,
                false,
                TelemetryStatus.OFFLINE,
                null
        ));

        return ResponseEntity.ok(
                EquipmentTelemetryStatusResourceFromEntityAssembler.toResourceFromEntity(status)
        );
    }

    @PutMapping(value = "/telemetry-status", consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Update equipment telemetry status")
    public ResponseEntity<?> updateEquipmentTelemetryStatus(
            @PathVariable Long equipmentId,
            @RequestBody UpdateEquipmentTelemetryStatusResource resource
    ) {
        var command = UpdateEquipmentTelemetryStatusCommandFromResourceAssembler.toCommandFromResource(
                equipmentId,
                resource
        );
        var result = trackingCommandService.handle(command);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                statusId -> statusId,
                HttpStatus.OK
        );
    }

    @GetMapping("/telemetry-history")
    @Operation(summary = "Get equipment telemetry history")
    public ResponseEntity<List<TelemetryHistoryPointResource>> getTelemetryHistory(
            @PathVariable Long equipmentId,
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to
    ) {
        var historyPoints = trackingQueryService.handle(
                new GetTelemetryHistoryQuery(equipmentId, from, to)
        );

        var resources = historyPoints.stream()
                .map(TelemetryHistoryPointResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }

    @PostMapping(value = "/telemetry-history", consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Record equipment telemetry history point")
    public ResponseEntity<?> recordTelemetryHistoryPoint(
            @PathVariable Long equipmentId,
            @RequestBody RecordTelemetryHistoryPointResource resource
    ) {
        var command = RecordTelemetryHistoryPointCommandFromResourceAssembler.toCommandFromResource(
                equipmentId,
                resource
        );
        var result = trackingCommandService.handle(command);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                historyPointId -> historyPointId,
                HttpStatus.CREATED
        );
    }
}