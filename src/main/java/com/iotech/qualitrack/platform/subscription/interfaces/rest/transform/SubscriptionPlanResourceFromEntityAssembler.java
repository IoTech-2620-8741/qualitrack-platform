package com.iotech.qualitrack.platform.subscription.interfaces.rest.transform;

import com.iotech.qualitrack.platform.subscription.domain.model.entities.SubscriptionPlan;
import com.iotech.qualitrack.platform.subscription.interfaces.rest.resources.SubscriptionPlanResource;

/**
 * Assembler that maps SubscriptionPlan domain entities to REST resources.
 */
public class SubscriptionPlanResourceFromEntityAssembler {

    public static SubscriptionPlanResource toResourceFromEntity(SubscriptionPlan entity) {
        return new SubscriptionPlanResource(
                entity.getId(),
                entity.getCode().name(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice().amount(),
                entity.getPrice().currency(),
                entity.getBillingCycle().name(),
                entity.getStripePriceId(),
                entity.getMaxUsers(),
                entity.getMaxEquipment(),
                entity.getActive()
        );
    }
}