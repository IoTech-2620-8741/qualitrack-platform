package com.iotech.qualitrack.platform.equipment.application.internal.outboundservices.acl;

import com.iotech.qualitrack.platform.subscription.interfaces.acl.SubscriptionContextFacade;
import org.springframework.stereotype.Service;

import java.util.OptionalInt;

/**
 * ACL service used by the Equipment bounded context to read, through Subscription, how many IoT devices the plan of
 * a laboratory allows.
 */
@Service
public class ExternalSubscriptionService {

    private final SubscriptionContextFacade subscriptionContextFacade;

    /**
     * Creates the service with the Subscription ACL facade dependency.
     *
     * @param subscriptionContextFacade subscription bounded-context facade
     */
    public ExternalSubscriptionService(SubscriptionContextFacade subscriptionContextFacade) {
        this.subscriptionContextFacade = subscriptionContextFacade;
    }

    /**
     * How many IoT devices (environmental devices and container monitors) the active plan of a laboratory allows.
     *
     * @param laboratoryId the laboratory
     * @return the allowance, or empty when the plan has no limit or the laboratory has no active subscription
     */
    public OptionalInt iotDeviceLimit(Long laboratoryId) {
        var limit = subscriptionContextFacade.getActiveEquipmentLimit(laboratoryId);
        return limit == null ? OptionalInt.empty() : OptionalInt.of(limit);
    }
}
