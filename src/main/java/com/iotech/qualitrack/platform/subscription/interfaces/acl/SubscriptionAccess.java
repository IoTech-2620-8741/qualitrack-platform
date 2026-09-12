package com.iotech.qualitrack.platform.subscription.interfaces.acl;

/** Subscription entitlement shared without exposing the aggregate to other contexts. */
public record SubscriptionAccess(Long subscriptionId, boolean active) {
}
