package com.iotech.qualitrack.platform.subscription.domain.model.queries;

/**
 * Query used to retrieve billing information for a laboratory.
 *
 * @param laboratoryId The laboratory identifier.
 */
public record GetBillingSummaryByLaboratoryIdQuery(
        Long laboratoryId
) {

    public GetBillingSummaryByLaboratoryIdQuery {
        if (laboratoryId == null || laboratoryId <= 0) {
            throw new IllegalArgumentException("Laboratory id must be a positive number");
        }
    }
}