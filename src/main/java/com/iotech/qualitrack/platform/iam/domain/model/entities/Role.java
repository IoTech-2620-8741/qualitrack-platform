package com.iotech.qualitrack.platform.iam.domain.model.entities;

import com.iotech.qualitrack.platform.iam.domain.model.valueobjects.Roles;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Role domain entity.
 *
 * <p>Represents an authorization role that can be assigned to IAM users.</p>
 */
@Getter
public class Role {

    /**
     * Role numeric identifier.
     */
    @Setter
    private Long id;

    /**
     * Role name.
     */
    private Roles name;

    /**
     * Required empty constructor for reconstruction.
     */
    public Role() {
    }

    /**
     * Creates a role from a supported role enum value.
     *
     * @param name role enum value
     */
    public Role(Roles name) {
        this.name = Objects.requireNonNull(name, "role name is required");
    }

    /**
     * Reconstructs a role from persistence data.
     *
     * @param id role identifier
     * @param name role enum value
     */
    public Role(Long id, Roles name) {
        this(name);
        this.id = id;
    }

    /**
     * Returns the role name as a string.
     *
     * @return role name
     */
    public String getStringName() {
        return name.name();
    }

    /**
     * Checks whether this role has the provided name.
     *
     * @param roleName role name to compare
     * @return true when names match
     */
    public boolean hasName(String roleName) {
        return name.name().equals(roleName);
    }

    /**
     * Validates and normalizes a role collection into a set.
     *
     * @param roles role collection
     * @return validated role set
     */
    public static Set<Role> validateRoleSet(Collection<Role> roles) {
        if (roles == null || roles.isEmpty()) {
            throw new IllegalArgumentException("roles cannot be null or empty");
        }

        return new HashSet<>(roles);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Role role)) return false;
        return name == role.name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}