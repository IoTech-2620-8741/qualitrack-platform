package com.iotech.qualitrack.platform.iam.interfaces.rest.resources;

public record UserOnboardingResource(Long userId, Long laboratoryId, Long subscriptionId,
                                     String subscriptionStatus, String nextStep) {
}
