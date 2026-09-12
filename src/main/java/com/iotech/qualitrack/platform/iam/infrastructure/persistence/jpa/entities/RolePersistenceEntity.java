package com.iotech.qualitrack.platform.iam.infrastructure.persistence.jpa.entities;

import com.iotech.qualitrack.platform.iam.domain.model.valueobjects.Roles;
import com.iotech.qualitrack.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * JPA persistence entity for IAM roles.
 */
@Getter
@Setter
@Entity
@Table(
        name = "iam_roles",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"name"})
        }
)
public class RolePersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 80)
    private Roles name;
}