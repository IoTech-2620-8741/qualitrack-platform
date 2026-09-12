package com.iotech.qualitrack.platform.subscription.infrastructure.persistence.jpa.entities;

import com.iotech.qualitrack.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import com.iotech.qualitrack.platform.subscription.domain.model.valueobjects.BillingCycle;
import com.iotech.qualitrack.platform.subscription.domain.model.valueobjects.PlanCode;
import com.iotech.qualitrack.platform.subscription.infrastructure.persistence.jpa.embeddables.MoneyEmbeddable;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA entity for subscription plans.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "subscription_plans",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"code", "billing_cycle"}),
                @UniqueConstraint(columnNames = {"stripe_price_id"})
        }
)
public class SubscriptionPlanPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "code", nullable = false, length = 50)
    private PlanCode code;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", length = 500)
    private String description;

    @Embedded
    private MoneyEmbeddable price;

    @Column(name = "billing_cycle", nullable = false, length = 50)
    private BillingCycle billingCycle;

    @Column(name = "stripe_price_id", length = 150)
    private String stripePriceId;

    @Column(name = "max_users", nullable = false)
    private Integer maxUsers;

    @Column(name = "max_equipment")
    private Integer maxEquipment;

    @Column(name = "active", nullable = false)
    private Boolean active;
}
