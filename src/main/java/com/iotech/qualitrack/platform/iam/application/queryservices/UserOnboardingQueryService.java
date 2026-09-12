package com.iotech.qualitrack.platform.iam.application.queryservices;

import com.iotech.qualitrack.platform.iam.domain.model.queries.GetUserOnboardingQuery;
import com.iotech.qualitrack.platform.iam.domain.model.valueobjects.UserOnboarding;

public interface UserOnboardingQueryService {
    UserOnboarding handle(GetUserOnboardingQuery query);
}
