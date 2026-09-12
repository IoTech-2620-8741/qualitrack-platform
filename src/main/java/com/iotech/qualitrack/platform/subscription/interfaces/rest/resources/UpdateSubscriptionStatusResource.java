package com.iotech.qualitrack.platform.subscription.interfaces.rest.resources;

import com.iotech.qualitrack.platform.subscription.domain.model.valueobjects.SubscriptionStatus;

/**
 * Request resource used to update a subscription status.
 *
 * @param status the target subscription status
 * @param cancelledBy the user requesting cancellation when status is CANCELLED
 */
public record UpdateSubscriptionStatusResource(
        SubscriptionStatus status,
        Long cancelledBy
) {
}