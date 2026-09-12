package com.iotech.qualitrack.platform.subscription.interfaces.acl;

import com.iotech.qualitrack.platform.subscription.domain.model.queries.GetActiveSubscriptionByLaboratoryIdQuery;
import com.iotech.qualitrack.platform.subscription.domain.model.valueobjects.PlanCode;
import com.iotech.qualitrack.platform.subscription.application.queryservices.SubscriptionQueryService;
import org.springframework.stereotype.Service;

/**
 * ACL facade exposed by the Subscription bounded context.
 *
 * <p>Provides a stable interface for other bounded contexts to verify
 * subscription capabilities without depending on subscription internals.</p>
 */
@Service
public class SubscriptionContextFacade {

    private final SubscriptionQueryService subscriptionQueryService;
    private final com.iotech.qualitrack.platform.subscription.domain.repositories.SubscriptionRepository repository;

    public SubscriptionContextFacade(SubscriptionQueryService subscriptionQueryService,
            com.iotech.qualitrack.platform.subscription.domain.repositories.SubscriptionRepository repository) {
        this.subscriptionQueryService = subscriptionQueryService;
        this.repository = repository;
    }

    public SubscriptionAccess getAccess(Long userId, Long laboratoryId) {
        var subscription = laboratoryId == null
                ? repository.findActiveByUserId(userId)
                : repository.findActiveByLaboratoryId(laboratoryId);
        return subscription.filter(value -> laboratoryId != null || value.getLaboratoryId() == null)
                .map(value -> new SubscriptionAccess(value.getId(), value.grantsAccess()))
                .orElse(new SubscriptionAccess(null, false));
    }

    public void assignLaboratory(Long userId, Long laboratoryId) {
        var subscription = repository.findActiveByUserId(userId)
                .filter(value -> value.grantsAccess())
                .orElseThrow(() -> new IllegalStateException("An active subscription is required"));
        subscription.assignLaboratory(laboratoryId);
        repository.save(subscription);
    }

    /**
     * Checks whether a laboratory currently has an active subscription.
     *
     * @param laboratoryId laboratory identifier
     * @return true if the laboratory has an active subscription, otherwise false
     */
    public boolean hasActiveSubscription(Long laboratoryId) {
        return subscriptionQueryService
                .handle(new GetActiveSubscriptionByLaboratoryIdQuery(laboratoryId))
                .filter(subscription -> subscription.grantsAccess()).isPresent();
    }

    /**
     * Checks whether a laboratory has an active subscription with the given plan.
     *
     * @param laboratoryId laboratory identifier
     * @param planCode expected plan code
     * @return true if the active subscription matches the requested plan
     */
    public boolean hasActivePlan(Long laboratoryId, PlanCode planCode) {
        return subscriptionQueryService
                .handle(new GetActiveSubscriptionByLaboratoryIdQuery(laboratoryId))
                .map(subscription -> subscription.getPlanCode() == planCode)
                .orElse(false);
    }

    /**
     * Gets the current active plan code for a laboratory.
     *
     * @param laboratoryId laboratory identifier
     * @return active plan name, or null if no active subscription exists
     */
    public String getActivePlanCode(Long laboratoryId) {
        return subscriptionQueryService
                .handle(new GetActiveSubscriptionByLaboratoryIdQuery(laboratoryId))
                .map(subscription -> subscription.getPlanCode().name())
                .orElse(null);
    }
}
