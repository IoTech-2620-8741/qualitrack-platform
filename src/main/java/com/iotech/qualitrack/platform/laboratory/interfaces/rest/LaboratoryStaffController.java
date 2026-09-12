package com.iotech.qualitrack.platform.laboratory.interfaces.rest;

import com.iotech.qualitrack.platform.laboratory.application.commandservices.StaffCommandService;
import com.iotech.qualitrack.platform.laboratory.application.queryservices.StaffQueryService;
import com.iotech.qualitrack.platform.laboratory.domain.model.queries.GetStaffByLabIdQuery;
import com.iotech.qualitrack.platform.laboratory.interfaces.rest.resources.RegisterStaffResource;
import com.iotech.qualitrack.platform.laboratory.interfaces.rest.resources.StaffMemberResource;
import com.iotech.qualitrack.platform.laboratory.interfaces.rest.transform.RegisterStaffCommandFromResourceAssembler;
import com.iotech.qualitrack.platform.laboratory.interfaces.rest.transform.StaffResourceFromEntityAssembler;
import com.iotech.qualitrack.platform.shared.interfaces.rest.resources.MessageResource;
import com.iotech.qualitrack.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/laboratories/{laboratoryId}/staff", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Laboratories", description = "Laboratory management endpoints")
public class LaboratoryStaffController {

    private final StaffCommandService staffCommandService;
    private final StaffQueryService staffQueryService;

    public LaboratoryStaffController(StaffCommandService staffCommandService, StaffQueryService staffQueryService) {
        this.staffCommandService = staffCommandService;
        this.staffQueryService = staffQueryService;
    }

    @PostMapping
    public ResponseEntity<?> registerStaff(
            @PathVariable Long laboratoryId,
            @RequestBody RegisterStaffResource resource
    ) {
        var command = RegisterStaffCommandFromResourceAssembler.toCommandFromResource(resource, laboratoryId);

        var result = staffCommandService.handle(command)
                .map(staffId -> new MessageResource("Staff member registered successfully with ID: " + staffId));

        return ResponseEntityAssembler.toResponseEntityFromResult(result, message -> message, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<StaffMemberResource>> getStaffByLaboratoryId(
            @PathVariable Long laboratoryId
    ) {
        var staff = staffQueryService.handle(new GetStaffByLabIdQuery(laboratoryId));

        var resources = staff.stream()
                .map(StaffResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }
}