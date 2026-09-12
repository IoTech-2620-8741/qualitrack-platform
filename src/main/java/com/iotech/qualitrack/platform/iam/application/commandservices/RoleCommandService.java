package com.iotech.qualitrack.platform.iam.application.commandservices;

import com.iotech.qualitrack.platform.iam.domain.model.commands.SeedRolesCommand;
import com.iotech.qualitrack.platform.shared.application.result.ApplicationError;
import com.iotech.qualitrack.platform.shared.application.result.Result;

/**
 * Application command service contract for IAM role operations.
 */
public interface RoleCommandService {

    Result<Integer, ApplicationError> handle(SeedRolesCommand command);
}