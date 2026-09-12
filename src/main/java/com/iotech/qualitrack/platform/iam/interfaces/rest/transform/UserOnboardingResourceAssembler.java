package com.iotech.qualitrack.platform.iam.interfaces.rest.transform;

import com.iotech.qualitrack.platform.iam.domain.model.valueobjects.UserOnboarding;
import com.iotech.qualitrack.platform.iam.interfaces.rest.resources.UserOnboardingResource;

public final class UserOnboardingResourceAssembler {
    private UserOnboardingResourceAssembler() { }

    public static UserOnboardingResource toResource(UserOnboarding state) {
        return new UserOnboardingResource(state.userId(), state.laboratoryId(), state.subscriptionId(),
                state.subscriptionStatus(), state.nextStep());
    }
}
