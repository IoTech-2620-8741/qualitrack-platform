package com.iotech.qualitrack.platform.subscription.interfaces.rest.resources;

import java.math.BigDecimal;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Resource representing a subscription plan exposed through the REST API.
 */
public record SubscriptionPlanResource(
        Long id,
        String code,
        String name,
        String description,
        BigDecimal amount,
        String currency,
        String billingCycle,
        String stripePriceId,
        Integer maxUsers,
        @Schema(types = {"integer", "null"}, description = "Maximum connected equipment; null means unlimited.")
        Integer maxEquipment,
        Boolean active
) {
}
