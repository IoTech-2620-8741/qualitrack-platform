package com.iotech.qualitrack.platform.subscription.infrastructure.persistence.jpa.assemblers;

import com.iotech.qualitrack.platform.subscription.domain.model.entities.SubscriptionPlan;
import com.iotech.qualitrack.platform.subscription.infrastructure.persistence.jpa.entities.SubscriptionPlanPersistenceEntity;

/**
 * Assembler for subscription plan persistence mapping.
 */
public class SubscriptionPlanPersistenceAssembler {

    private SubscriptionPlanPersistenceAssembler() {
    }

    public static SubscriptionPlan toDomainFromPersistence(SubscriptionPlanPersistenceEntity entity) {
        if (entity == null) return null;

        return new SubscriptionPlan(
                entity.getId(),
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                MoneyPersistenceAssembler.toDomainFromPersistence(entity.getPrice()),
                entity.getBillingCycle(),
                entity.getStripePriceId(),
                entity.getMaxUsers(),
                entity.getMaxEquipment(),
                entity.getActive()
        );
    }

    public static SubscriptionPlanPersistenceEntity toPersistenceFromDomain(SubscriptionPlan plan) {
        if (plan == null) return null;

        var entity = new SubscriptionPlanPersistenceEntity();
        entity.setId(plan.getId());
        entity.setCode(plan.getCode());
        entity.setName(plan.getName());
        entity.setDescription(plan.getDescription());
        entity.setPrice(MoneyPersistenceAssembler.toPersistenceFromDomain(plan.getPrice()));
        entity.setBillingCycle(plan.getBillingCycle());
        entity.setStripePriceId(plan.getStripePriceId());
        entity.setMaxUsers(plan.getMaxUsers());
        entity.setMaxEquipment(plan.getMaxEquipment());
        entity.setActive(plan.getActive());

        return entity;
    }
}