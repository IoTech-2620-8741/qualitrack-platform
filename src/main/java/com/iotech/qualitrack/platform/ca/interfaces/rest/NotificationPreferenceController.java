package com.iotech.qualitrack.platform.ca.interfaces.rest;

import com.iotech.qualitrack.platform.ca.application.commandservices.CaCommandService;
import com.iotech.qualitrack.platform.ca.application.queryservices.CaQueryService;
import com.iotech.qualitrack.platform.ca.domain.model.entities.NotificationPreference;
import com.iotech.qualitrack.platform.ca.domain.model.queries.GetNotificationPreferenceByUserIdQuery;
import com.iotech.qualitrack.platform.ca.interfaces.rest.resources.NotificationPreferenceResource;
import com.iotech.qualitrack.platform.ca.interfaces.rest.resources.UpdateNotificationPreferenceResource;
import com.iotech.qualitrack.platform.ca.interfaces.rest.transform.NotificationPreferenceResourceFromEntityAssembler;
import com.iotech.qualitrack.platform.ca.interfaces.rest.transform.UpdateNotificationPreferenceCommandFromResourceAssembler;
import com.iotech.qualitrack.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller that exposes user notification preference resources.
 */
@RestController
@RequestMapping(
        value = "/api/v1/users/{userId}/notification-preferences",
        produces = APPLICATION_JSON_VALUE
)
@Tag(name = "Users", description = "User notification preference endpoints")
public class NotificationPreferenceController {

    private final CaCommandService caCommandService;
    private final CaQueryService caQueryService;

    public NotificationPreferenceController(
            CaCommandService caCommandService,
            CaQueryService caQueryService
    ) {
        this.caCommandService = caCommandService;
        this.caQueryService = caQueryService;
    }

    @GetMapping
    @Operation(summary = "Get user notification preferences")
    public ResponseEntity<NotificationPreferenceResource> getPreferencesByUserId(
            @PathVariable Long userId
    ) {
        var preference = caQueryService.handle(new GetNotificationPreferenceByUserIdQuery(userId))
                .orElseGet(() -> new NotificationPreference(userId));

        return ResponseEntity.ok(
                NotificationPreferenceResourceFromEntityAssembler.toResourceFromEntity(preference)
        );
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Update user notification preferences")
    public ResponseEntity<?> updatePreferences(
            @PathVariable Long userId,
            @RequestBody UpdateNotificationPreferenceResource resource
    ) {
        var command = UpdateNotificationPreferenceCommandFromResourceAssembler.toCommandFromResource(
                userId,
                resource
        );
        var result = caCommandService.handle(command);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                NotificationPreferenceResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }
}