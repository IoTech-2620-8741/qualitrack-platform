package com.iotech.qualitrack.platform.iam.application.internal.commandservices;

import com.iotech.qualitrack.platform.iam.application.commandservices.UserCommandService;
import com.iotech.qualitrack.platform.iam.application.internal.outboundservices.hashing.HashingService;
import com.iotech.qualitrack.platform.iam.application.internal.outboundservices.tokens.TokenService;
import com.iotech.qualitrack.platform.iam.domain.model.aggregates.User;
import com.iotech.qualitrack.platform.iam.domain.model.commands.AssignRoleCommand;
import com.iotech.qualitrack.platform.iam.domain.model.commands.DeactivateUserCommand;
import com.iotech.qualitrack.platform.iam.domain.model.commands.SignInCommand;
import com.iotech.qualitrack.platform.iam.domain.model.commands.SignUpCommand;
import com.iotech.qualitrack.platform.iam.domain.model.entities.Role;
import com.iotech.qualitrack.platform.iam.domain.model.valueobjects.Roles;
import com.iotech.qualitrack.platform.iam.domain.repositories.RoleRepository;
import com.iotech.qualitrack.platform.iam.domain.repositories.UserRepository;
import com.iotech.qualitrack.platform.shared.application.result.ApplicationError;
import com.iotech.qualitrack.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Application command service implementation for IAM users.
 */
@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final HashingService hashingService;
    private final TokenService tokenService;

    public UserCommandServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository,
            HashingService hashingService,
            TokenService tokenService
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.hashingService = hashingService;
        this.tokenService = tokenService;
    }

    @Override
    public Result<AuthenticatedUser, ApplicationError> handle(SignInCommand command) {
        var userResult = userRepository.findByUsername(command.username());

        if (userResult.isEmpty()) {
            return Result.failure(ApplicationError.notFound("User", command.username()));
        }

        var user = userResult.get();

        if (!user.isActive()) {
            return Result.failure(ApplicationError.conflict("User", "User account is not active"));
        }

        if (!hashingService.matches(command.password(), user.getPasswordValue())) {
            return Result.failure(ApplicationError.validationError("Credentials", "Invalid username or password"));
        }

        var token = tokenService.generateToken(user);

        return Result.success(new AuthenticatedUser(user, token));
    }

    @Override
    public Result<User, ApplicationError> handle(SignUpCommand command) {
        if (command.roles().contains("ROLE_ADMIN")) {
            return Result.failure(ApplicationError.validationError("roles", "Administrator accounts cannot self-register"));
        }
        if (command.laboratoryId() != null) {
            return Result.failure(ApplicationError.validationError("laboratoryId",
                    "Public registration cannot join an existing laboratory"));
        }
        if (userRepository.existsByUsername(command.username())) {
            return Result.failure(ApplicationError.conflict(
                    "User",
                    "Username '%s' is already registered".formatted(command.username())
            ));
        }

        try {
            var roles = new ArrayList<Role>();

            for (var roleName : command.roles()) {
                var role = roleRepository.findByName(Roles.valueOf(roleName))
                        .orElseGet(() -> roleRepository.save(new Role(Roles.valueOf(roleName))));

                roles.add(role);
            }

            var encodedPassword = hashingService.encode(command.password());

            var user = new User(
                    command.username(),
                    encodedPassword,
                    roles,
                    command.laboratoryId()
            );

            var savedUser = userRepository.save(user);

            return Result.success(savedUser);

        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("User", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("sign-up", e.getMessage()));
        }
    }

    @Override
    public Result<Long, ApplicationError> handle(AssignRoleCommand command) {
        var userResult = userRepository.findById(command.userId());

        if (userResult.isEmpty()) {
            return Result.failure(ApplicationError.notFound("User", command.userId()));
        }

        try {
            var roleName = Roles.valueOf(command.roleName());

            var role = roleRepository.findByName(roleName)
                    .orElseGet(() -> roleRepository.save(new Role(roleName)));

            var user = userResult.get();
            user.addRole(role);

            var savedUser = userRepository.save(user);

            return Result.success(savedUser.getId());

        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("Role", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("assign-role", e.getMessage()));
        }
    }

    @Override
    public Result<Long, ApplicationError> handle(DeactivateUserCommand command) {
        var userResult = userRepository.findById(command.userId());

        if (userResult.isEmpty()) {
            return Result.failure(ApplicationError.notFound("User", command.userId()));
        }

        try {
            var user = userResult.get();
            user.deactivate();

            var savedUser = userRepository.save(user);

            return Result.success(savedUser.getId());

        } catch (IllegalStateException e) {
            return Result.failure(ApplicationError.conflict("User", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("deactivate-user", e.getMessage()));
        }
    }
}
