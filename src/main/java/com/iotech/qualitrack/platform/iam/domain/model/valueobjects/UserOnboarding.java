package com.iotech.qualitrack.platform.iam.domain.model.valueobjects;

/** Current prerequisites for operational access, resolved from persisted state. */
public record UserOnboarding(Long userId, Long laboratoryId, Long subscriptionId,
                             String subscriptionStatus) {
    public String nextStep() {
        if (!"ACTIVE".equals(subscriptionStatus)) return "SUBSCRIPTION";
        return laboratoryId == null ? "LABORATORY" : "READY";
    }
}
