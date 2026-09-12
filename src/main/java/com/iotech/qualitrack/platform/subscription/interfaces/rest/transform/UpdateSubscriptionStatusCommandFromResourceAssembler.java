package com.iotech.qualitrack.platform.subscription.interfaces.rest.transform;

import com.iotech.qualitrack.platform.subscription.domain.model.commands.CancelSubscriptionCommand;
import com.iotech.qualitrack.platform.subscription.interfaces.rest.resources.UpdateSubscriptionStatusResource;

/**
 * Assembler that maps subscription status update REST resources to domain commands.
 */
public final class UpdateSubscriptionStatusCommandFromResourceAssembler {

    private UpdateSubscriptionStatusCommandFromResourceAssembler() {
    }

    /**
     * Converts a subscription status update resource into a cancellation command.
     *
     * @param subscriptionId the subscription numeric identifier from the route path
     * @param resource the subscription status update resource
     * @return the cancellation command
     */
    public static CancelSubscriptionCommand toCancelCommandFromResource(
            Long subscriptionId,
            UpdateSubscriptionStatusResource resource
    ) {
        return new CancelSubscriptionCommand(
                subscriptionId,
                resource.cancelledBy()
        );
    }
}