package com.iotech.qualitrack.platform.laboratory.interfaces.rest;

import com.iotech.qualitrack.platform.laboratory.application.commandservices.StaffCommandService;
import com.iotech.qualitrack.platform.laboratory.interfaces.rest.resources.UpdateStaffStatusResource;
import com.iotech.qualitrack.platform.laboratory.interfaces.rest.transform.UpdateStaffStatusCommandFromResourceAssembler;
import com.iotech.qualitrack.platform.shared.application.result.ApplicationError;
import com.iotech.qualitrack.platform.shared.interfaces.rest.resources.MessageResource;
import com.iotech.qualitrack.platform.shared.interfaces.rest.transform.ErrorResponseAssembler;
import com.iotech.qualitrack.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller that exposes direct operations on staff member resources.
 */
@RestController
@RequestMapping(value = "/api/v1/staff", produces = APPLICATION_JSON_VALUE)
public class StaffController {

    private final StaffCommandService staffCommandService;

    public StaffController(StaffCommandService staffCommandService) {
        this.staffCommandService = staffCommandService;
    }

    /**
     * Updates a staff member status.
     *
     * @param staffId staff numeric identifier
     * @param resource staff status update resource
     * @return confirmation message when the status is updated
     */
    @PatchMapping(value = "/{staffId}", consumes = APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Update staff member status",
            description = "Updates a staff member status. Currently supports deactivation by sending active=false."
    )
    public ResponseEntity<?> updateStaffStatus(
            @PathVariable
            @Parameter(description = "Staff numeric identifier", example = "99", required = true)
            Long staffId,
            @RequestBody UpdateStaffStatusResource resource
    ) {
        if (resource.active() == null) {
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(
                    ApplicationError.validationError(
                            "StaffMember",
                            "active status is required"
                    )
            );
        }

        if (Boolean.TRUE.equals(resource.active())) {
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(
                    ApplicationError.validationError(
                            "StaffMember",
                            "Activating staff members is not supported yet"
                    )
            );
        }

        var command = UpdateStaffStatusCommandFromResourceAssembler
                .toDeactivateCommandFromResource(staffId, resource);

        var result = staffCommandService.handle(command)
                .map(id -> new MessageResource("Staff member deactivated successfully"));

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                message -> message,
                HttpStatus.OK
        );
    }
}