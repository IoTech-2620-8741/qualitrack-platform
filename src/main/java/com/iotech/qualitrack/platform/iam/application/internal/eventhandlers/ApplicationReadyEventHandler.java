package com.iotech.qualitrack.platform.iam.application.internal.eventhandlers;

import com.iotech.qualitrack.platform.iam.application.commandservices.RoleCommandService;
import com.iotech.qualitrack.platform.iam.domain.model.commands.SeedRolesCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Handles application startup events for IAM initialization.
 *
 * <p>Seeds default IAM roles when the application is ready.</p>
 */
@Service
@Slf4j
public class ApplicationReadyEventHandler {

    private final RoleCommandService roleCommandService;

    public ApplicationReadyEventHandler(RoleCommandService roleCommandService) {
        this.roleCommandService = roleCommandService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        var result = roleCommandService.handle(new SeedRolesCommand());

        result.toOptional().ifPresent(createdRoles ->
                log.info("IAM role seed completed. Created roles: {}.", createdRoles)
        );

        if (result.isFailure()) {
            log.warn("IAM role seed failed.");
        }
    }
}