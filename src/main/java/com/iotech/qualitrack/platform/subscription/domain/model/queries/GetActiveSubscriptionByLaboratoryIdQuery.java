package com.iotech.qualitrack.platform.subscription.domain.model.queries;

/**
 * Query used to retrieve the active subscription for a laboratory.
 *
 * @param laboratoryId The laboratory identifier.
 */
public record GetActiveSubscriptionByLaboratoryIdQuery(
        Long laboratoryId
) {

    public GetActiveSubscriptionByLaboratoryIdQuery {
        if (laboratoryId == null || laboratoryId <= 0) {
            throw new IllegalArgumentException("Laboratory id must be a positive number");
        }
    }
}