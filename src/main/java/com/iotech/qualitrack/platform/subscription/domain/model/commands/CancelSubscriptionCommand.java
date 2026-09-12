package com.iotech.qualitrack.platform.subscription.domain.model.commands;

/**
 * Command used to cancel an active subscription.
 *
 * @param subscriptionId The internal subscription identifier.
 * @param cancelledBy The user requesting cancellation.
 */
public record CancelSubscriptionCommand(
        Long subscriptionId,
        Long cancelledBy
) {

    public CancelSubscriptionCommand {
        if (subscriptionId == null || subscriptionId <= 0) {
            throw new IllegalArgumentException("Subscription id must be a positive number");
        }
        if (cancelledBy == null || cancelledBy <= 0) {
            throw new IllegalArgumentException("Cancelled by user id must be a positive number");
        }
    }
}