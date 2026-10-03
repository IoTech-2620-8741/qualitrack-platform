package com.iotech.qualitrack.platform.iam.interfaces.rest;

import com.iotech.qualitrack.platform.iam.application.commandservices.UserCommandService;
import com.iotech.qualitrack.platform.iam.application.queryservices.UserQueryService;
import com.iotech.qualitrack.platform.iam.domain.model.commands.AssignRoleCommand;
import com.iotech.qualitrack.platform.iam.domain.model.queries.GetAllUsersQuery;
import com.iotech.qualitrack.platform.iam.domain.model.queries.GetUserByIdQuery;
import com.iotech.qualitrack.platform.iam.interfaces.rest.resources.UpdateUserStatusResource;
import com.iotech.qualitrack.platform.iam.interfaces.rest.resources.UserResource;
import com.iotech.qualitrack.platform.iam.interfaces.rest.transform.UpdateUserStatusCommandFromResourceAssembler;
import com.iotech.qualitrack.platform.iam.interfaces.rest.transform.UserResourceFromEntityAssembler;
import com.iotech.qualitrack.platform.shared.application.result.ApplicationError;
import com.iotech.qualitrack.platform.shared.interfaces.rest.transform.ErrorResponseAssembler;
import com.iotech.qualitrack.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller for IAM user resources.
 */
@RestController
@RequestMapping(value = "/api/v1/users", produces = APPLICATION_JSON_VALUE)
public class UsersController {

    private final UserQueryService userQueryService;
    private final UserCommandService userCommandService;

    public UsersController(
            UserQueryService userQueryService,
            UserCommandService userCommandService
    ) {
        this.userQueryService = userQueryService;
        this.userCommandService = userCommandService;
    }

    @GetMapping
    @Operation(summary = "Get users", description = "Retrieves all IAM users.")
    public ResponseEntity<List<UserResource>> getUsers(
            @org.springframework.security.core.annotation.AuthenticationPrincipal
            com.iotech.qualitrack.platform.iam.infrastructure.authorization.sfs.model.UserDetailsImpl principal) {
        var users = userQueryService.handle(new GetAllUsersQuery());

        var resources = users.stream()
                .filter(user -> principal.getLaboratoryId().equals(user.getLaboratoryId()))
                .map(UserResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get user by ID", description = "Retrieves an IAM user by identifier.")
    public ResponseEntity<UserResource> getUserById(@PathVariable Long userId) {
        var user = userQueryService.handle(new GetUserByIdQuery(userId));

        if (user.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(UserResourceFromEntityAssembler.toResourceFromEntity(user.get()));
    }

    @PutMapping("/{userId}/roles/{roleName}")
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Assign user role", description = "Assigns a role to an IAM user.")
    public ResponseEntity<?> assignRole(
            @PathVariable Long userId,
            @PathVariable String roleName
    ) {
        var result = userCommandService.handle(new AssignRoleCommand(userId, roleName));

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                id -> id,
                HttpStatus.OK
        );
    }

    @PatchMapping(value = "/{userId}", consumes = APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Update user status",
            description = "Updates a user account status. Currently supports deactivation by sending active=false."
    )
    public ResponseEntity<?> updateUserStatus(
            @PathVariable Long userId,
            @RequestBody UpdateUserStatusResource resource
    ) {
        if (resource.active() == null) {
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(
                    ApplicationError.validationError(
                            "User",
                            "active status is required"
                    )
            );
        }

        if (Boolean.TRUE.equals(resource.active())) {
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(
                    ApplicationError.validationError(
                            "User",
                            "Activating users is not supported yet"
                    )
            );
        }

        var command = UpdateUserStatusCommandFromResourceAssembler
                .toDeactivateCommandFromResource(userId, resource);

        var result = userCommandService.handle(command);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                id -> id,
                HttpStatus.OK
        );
    }
}
