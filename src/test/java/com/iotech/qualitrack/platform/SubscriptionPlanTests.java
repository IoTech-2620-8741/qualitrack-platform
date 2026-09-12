package com.iotech.qualitrack.platform;

import com.iotech.qualitrack.platform.subscription.domain.model.entities.SubscriptionPlan;
import com.iotech.qualitrack.platform.subscription.domain.model.valueobjects.BillingCycle;
import com.iotech.qualitrack.platform.subscription.domain.model.valueobjects.Money;
import com.iotech.qualitrack.platform.subscription.domain.model.valueobjects.PlanCode;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SubscriptionPlanTests {
    private SubscriptionPlan plan(Integer equipmentLimit) {
        return new SubscriptionPlan(1L, PlanCode.ENTERPRISE, "Enterprise", "Test fixture",
                new Money(new BigDecimal("599.00"), "USD"), BillingCycle.MONTHLY,
                "price_fixture", 10, equipmentLimit, true);
    }

    @Test void preservesFiniteEquipmentAllowances() {
        assertThat(plan(5).getMaxEquipment()).isEqualTo(5);
    }

    @Test void representsUnlimitedEquipmentWithoutAnInventedNumericLimit() {
        assertThat(plan(null).getMaxEquipment()).isNull();
        assertThat(plan(null).isSelectable()).isTrue();
    }

    @Test void rejectsNegativeEquipmentLimits() {
        assertThatThrownBy(() -> plan(-1)).isInstanceOf(IllegalArgumentException.class);
    }
}
