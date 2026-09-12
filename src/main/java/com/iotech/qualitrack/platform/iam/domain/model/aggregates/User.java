package com.iotech.qualitrack.platform.iam.domain.model.aggregates;

import com.iotech.qualitrack.platform.iam.domain.model.entities.Role;
import com.iotech.qualitrack.platform.iam.domain.model.valueobjects.PasswordHash;
import com.iotech.qualitrack.platform.iam.domain.model.valueobjects.UserStatus;
import com.iotech.qualitrack.platform.iam.domain.model.valueobjects.Username;
import com.iotech.qualitrack.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * User aggregate root.
 *
 * <p>Represents an authenticated platform account with assigned authorization
 * roles and an optional laboratory association.</p>
 */
@Getter
public class User extends AbstractDomainAggregateRoot<User> {

    /**
     * User numeric identifier.
     */
    @Setter
    private Long id;

    /**
     * Account username.
     */
    private Username username;

    /**
     * Hashed account password.
     */
    private PasswordHash password;

    /**
     * Assigned authorization roles.
     */
    private Set<Role> roles;

    /**
     * Laboratory associated with the account.
     */
    private Long laboratoryId;

    /**
     * User lifecycle status.
     */
    private UserStatus status;

    /**
     * Required empty constructor for reconstruction.
     */
    public User() {
        this.roles = new HashSet<>();
        this.status = UserStatus.ACTIVE;
    }

    /**
     * Creates a new active user.
     *
     * @param username account username
     * @param password hashed account password
     * @param roles assigned role collection
     * @param laboratoryId associated laboratory identifier
     */
    public User(
            String username,
            String password,
            Collection<Role> roles,
            Long laboratoryId
    ) {
        this.username = new Username(username);
        this.password = new PasswordHash(password);
        this.roles = Role.validateRoleSet(roles);
        setLaboratoryId(laboratoryId);
        this.status = UserStatus.ACTIVE;
    }

    /**
     * Reconstructs a user from persistence data.
     *
     * @param id user identifier
     * @param username account username
     * @param password hashed account password
     * @param roles assigned roles
     * @param laboratoryId associated laboratory identifier
     * @param status user status
     */
    public User(
            Long id,
            String username,
            String password,
            Collection<Role> roles,
            Long laboratoryId,
            UserStatus status
    ) {
        this(username, password, roles, laboratoryId);
        this.id = id;
        this.status = status;
    }

    /**
     * Adds a role to this user.
     *
     * @param role role to assign
     */
    public void addRole(Role role) {
        if (role == null) {
            throw new IllegalArgumentException("role cannot be null");
        }

        this.roles.add(role);
    }

    /**
     * Adds multiple roles to this user.
     *
     * @param roles roles to assign
     */
    public void addRoles(Collection<Role> roles) {
        this.roles.addAll(Role.validateRoleSet(roles));
    }

    /**
     * Deactivates the user account.
     */
    public void deactivate() {
        if (status == UserStatus.INACTIVE) {
            throw new IllegalStateException("User is already inactive");
        }

        this.status = UserStatus.INACTIVE;
    }

    /**
     * Checks whether this user is active.
     *
     * @return true when user status is active
     */
    public boolean isActive() {
        return status == UserStatus.ACTIVE;
    }

    /**
     * Returns username raw value.
     *
     * @return username string
     */
    public String getUsernameValue() {
        return username.value();
    }

    /**
     * Returns password hash raw value.
     *
     * @return password hash string
     */
    public String getPasswordValue() {
        return password.value();
    }

    /**
     * Assigns a laboratory identifier to this user.
     *
     * @param laboratoryId laboratory identifier
     */
    private void setLaboratoryId(Long laboratoryId) {
        if (laboratoryId != null && laboratoryId <= 0) {
            throw new IllegalArgumentException("laboratoryId cannot be less than 1");
        }

        this.laboratoryId = laboratoryId;
    }

    public void assignLaboratory(Long laboratoryId) {
        if (laboratoryId == null || laboratoryId <= 0) {
            throw new IllegalArgumentException("A positive laboratory identifier is required");
        }
        if (this.laboratoryId != null && !this.laboratoryId.equals(laboratoryId)) {
            throw new IllegalStateException("The user already belongs to a laboratory");
        }
        this.laboratoryId = laboratoryId;
    }
}
