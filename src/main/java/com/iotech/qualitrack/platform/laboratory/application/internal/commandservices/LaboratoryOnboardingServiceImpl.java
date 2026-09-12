package com.iotech.qualitrack.platform.laboratory.application.internal.commandservices;

import com.iotech.qualitrack.platform.laboratory.application.commandservices.LaboratoryCommandService;
import com.iotech.qualitrack.platform.laboratory.application.commandservices.LaboratoryOnboardingService;

import com.iotech.qualitrack.platform.iam.interfaces.acl.IamContextFacade;
import com.iotech.qualitrack.platform.laboratory.domain.model.commands.CreateLaboratoryCommand;
import com.iotech.qualitrack.platform.shared.application.result.ApplicationError;
import com.iotech.qualitrack.platform.shared.application.result.ApplicationException;
import com.iotech.qualitrack.platform.shared.application.result.Result;
import com.iotech.qualitrack.platform.subscription.interfaces.acl.SubscriptionContextFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Creates and associates the laboratory atomically, serializing setup per user. */
@Service
public class LaboratoryOnboardingServiceImpl implements LaboratoryOnboardingService {
    private final LaboratoryCommandService commands;
    private final IamContextFacade users;
    private final SubscriptionContextFacade subscriptions;

    public LaboratoryOnboardingServiceImpl(LaboratoryCommandService commands, IamContextFacade users,
                                       SubscriptionContextFacade subscriptions) {
        this.commands = commands;
        this.users = users;
        this.subscriptions = subscriptions;
    }

    @Transactional
    @Override
    public Long create(Long userId, CreateLaboratoryCommand command) {
        if (users.lockLaboratoryAssociation(userId) != null) {
            throw new ApplicationException(ApplicationError.conflict("Laboratory", "Laboratory setup is already complete"));
        }
        if (!subscriptions.getAccess(userId, null).active()) {
            throw new ApplicationException(ApplicationError.businessRuleViolation("subscription", "An active subscription is required"));
        }
        var laboratoryId = switch (commands.handle(command)) {
            case Result.Success<Long, ApplicationError> success -> success.value();
            case Result.Failure<Long, ApplicationError> failure -> throw new ApplicationException(failure.error());
        };
        users.assignLaboratory(userId, laboratoryId);
        subscriptions.assignLaboratory(userId, laboratoryId);
        return laboratoryId;
    }
}
