package com.iotech.qualitrack.platform.iam.application.commandservices;

import com.iotech.qualitrack.platform.iam.domain.model.aggregates.User;
import com.iotech.qualitrack.platform.iam.domain.model.commands.AssignRoleCommand;
import com.iotech.qualitrack.platform.iam.domain.model.commands.DeactivateUserCommand;
import com.iotech.qualitrack.platform.iam.domain.model.commands.SignInCommand;
import com.iotech.qualitrack.platform.iam.domain.model.commands.SignUpCommand;
import com.iotech.qualitrack.platform.shared.application.result.ApplicationError;
import com.iotech.qualitrack.platform.shared.application.result.Result;

/**
 * Application command service contract for IAM user operations.
 */
public interface UserCommandService {

    Result<AuthenticatedUser, ApplicationError> handle(SignInCommand command);

    Result<User, ApplicationError> handle(SignUpCommand command);

    Result<Long, ApplicationError> handle(AssignRoleCommand command);

    Result<Long, ApplicationError> handle(DeactivateUserCommand command);

    /**
     * Authenticated user result.
     *
     * @param user authenticated user
     * @param token generated JWT token
     */
    record AuthenticatedUser(
            User user,
            String token
    ) {
    }
}