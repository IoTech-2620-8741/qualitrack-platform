package com.iotech.qualitrack.platform.subscription.infrastructure.persistence.jpa.entities;

import com.iotech.qualitrack.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import com.iotech.qualitrack.platform.subscription.domain.model.valueobjects.PaymentProvider;
import com.iotech.qualitrack.platform.subscription.domain.model.valueobjects.PaymentStatus;
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
 * JPA entity for subscription payments.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "subscription_payments",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"provider_payment_id"}),
                @UniqueConstraint(columnNames = {"stripe_checkout_session_id"})
        }
)
public class SubscriptionPaymentPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "subscription_id", nullable = false)
    private Long subscriptionId;

    @Column(name = "provider", nullable = false, length = 50)
    private PaymentProvider provider;

    @Column(name = "provider_payment_id", length = 150)
    private String providerPaymentId;

    @Column(name = "stripe_checkout_session_id", length = 150)
    private String stripeCheckoutSessionId;

    @Embedded
    private MoneyEmbeddable amount;

    @Column(name = "status", nullable = false, length = 50)
    private PaymentStatus status;

    @Column(name = "paid_at", length = 50)
    private String paidAt;
}