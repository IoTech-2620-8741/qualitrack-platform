package com.iotech.qualitrack.platform.subscription.interfaces.rest.transform;

import com.iotech.qualitrack.platform.subscription.domain.model.commands.CreateCheckoutSessionCommand;
import com.iotech.qualitrack.platform.subscription.domain.model.valueobjects.BillingCycle;
import com.iotech.qualitrack.platform.subscription.domain.model.valueobjects.PlanCode;
import com.iotech.qualitrack.platform.subscription.interfaces.rest.resources.CreateCheckoutSessionResource;

/**
 * Assembler that maps REST checkout session resources to domain commands.
 */
public class CreateCheckoutSessionCommandFromResourceAssembler {

    public static CreateCheckoutSessionCommand toCommandFromResource(CreateCheckoutSessionResource resource) {
        return new CreateCheckoutSessionCommand(
                resource.userId(),
                resource.laboratoryId(),
                PlanCode.valueOf(resource.planCode()),
                BillingCycle.valueOf(resource.billingCycle()),
                resource.successUrl(),
                resource.cancelUrl()
        );
    }
}