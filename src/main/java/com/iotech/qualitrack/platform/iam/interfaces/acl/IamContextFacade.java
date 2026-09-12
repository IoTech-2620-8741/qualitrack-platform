package com.iotech.qualitrack.platform.iam.interfaces.acl;

import com.iotech.qualitrack.platform.iam.application.queryservices.UserQueryService;
import com.iotech.qualitrack.platform.iam.domain.model.queries.GetUserByIdQuery;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ACL facade exposed by the IAM bounded context.
 *
 * <p>Provides stable user identity and authorization information to other
 * bounded contexts without exposing IAM internals.</p>
 */
@Service
public class IamContextFacade {

    private final UserQueryService userQueryService;
    private final com.iotech.qualitrack.platform.iam.domain.repositories.UserRepository userRepository;

    public IamContextFacade(UserQueryService userQueryService,
            com.iotech.qualitrack.platform.iam.domain.repositories.UserRepository userRepository) {
        this.userQueryService = userQueryService;
        this.userRepository = userRepository;
    }

    public Long lockLaboratoryAssociation(Long userId) {
        return userRepository.findByIdForUpdate(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found")).getLaboratoryId();
    }

    public void assignLaboratory(Long userId, Long laboratoryId) {
        var user = userRepository.findByIdForUpdate(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.assignLaboratory(laboratoryId);
        userRepository.save(user);
    }

    /**
     * Checks whether a user exists.
     *
     * @param userId user identifier
     * @return true if the user exists
     */
    public boolean existsUserById(Long userId) {
        return userQueryService.handle(new GetUserByIdQuery(userId)).isPresent();
    }

    /**
     * Gets the laboratory associated with a user.
     *
     * @param userId user identifier
     * @return laboratory identifier or null if user does not exist or has no laboratory
     */
    public Long getLaboratoryIdByUserId(Long userId) {
        return userQueryService.handle(new GetUserByIdQuery(userId))
                .map(user -> user.getLaboratoryId())
                .orElse(null);
    }

    /**
     * Gets the username for a user.
     *
     * @param userId user identifier
     * @return username or null if user does not exist
     */
    public String getUsernameByUserId(Long userId) {
        return userQueryService.handle(new GetUserByIdQuery(userId))
                .map(user -> user.getUsernameValue())
                .orElse(null);
    }

    /**
     * Gets assigned role names for a user.
     *
     * @param userId user identifier
     * @return role names or empty list if user does not exist
     */
    public List<String> getRoleNamesByUserId(Long userId) {
        return userQueryService.handle(new GetUserByIdQuery(userId))
                .map(user -> user.getRoles().stream()
                        .map(role -> role.getName().name())
                        .toList())
                .orElse(List.of());
    }

    /**
     * Checks whether a user has a given role.
     *
     * @param userId user identifier
     * @param roleName role name
     * @return true if the user has the role
     */
    public boolean userHasRole(Long userId, String roleName) {
        return getRoleNamesByUserId(userId).contains(roleName);
    }
}
