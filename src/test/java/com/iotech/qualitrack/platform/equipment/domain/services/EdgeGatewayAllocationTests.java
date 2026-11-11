package com.iotech.qualitrack.platform.equipment.domain.services;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Covers how the IoT devices of an environment are distributed among its Edge Gateways.
 */
class EdgeGatewayAllocationTests {

    @Test
    void environmentWithoutGatewaysLeavesEveryDeviceUncovered() {
        var allocation = EdgeGatewayAllocation.of(List.of(), devices(1, 3));

        assertThat(allocation.deviceIdsByGatewayId()).isEmpty();
        assertThat(allocation.uncoveredDeviceIds()).containsExactly(1L, 2L, 3L);
        assertThat(allocation.deviceIdsOf(90L)).isEmpty();
    }

    @Test
    void oneGatewayServesUpToFifteenDevices() {
        var allocation = EdgeGatewayAllocation.of(List.of(90L), devices(1, 15));

        assertThat(allocation.deviceIdsOf(90L)).hasSize(15).containsExactlyElementsOf(devices(1, 15));
        assertThat(allocation.uncoveredDeviceIds()).isEmpty();
    }

    @Test
    void devicesBeyondTheCapacityOfTheOnlyGatewayAreUncovered() {
        var allocation = EdgeGatewayAllocation.of(List.of(90L), devices(1, 20));

        assertThat(allocation.deviceIdsOf(90L)).containsExactlyElementsOf(devices(1, 15));
        assertThat(allocation.uncoveredDeviceIds()).containsExactlyElementsOf(devices(16, 20));
    }

    @Test
    void secondGatewayReceivesTheDevicesTheFirstOneCannotServe() {
        var allocation = EdgeGatewayAllocation.of(List.of(91L, 90L), devices(1, 20));

        assertThat(allocation.deviceIdsByGatewayId().keySet()).containsExactly(90L, 91L);
        assertThat(allocation.deviceIdsOf(90L)).containsExactlyElementsOf(devices(1, 15));
        assertThat(allocation.deviceIdsOf(91L)).containsExactlyElementsOf(devices(16, 20));
        assertThat(allocation.uncoveredDeviceIds()).isEmpty();
    }

    @Test
    void gatewayWithoutDevicesLeftStaysInTheDistributionWithNone() {
        var allocation = EdgeGatewayAllocation.of(List.of(90L, 91L), devices(1, 4));

        assertThat(allocation.deviceIdsOf(90L)).hasSize(4);
        assertThat(allocation.deviceIdsOf(91L)).isEmpty();
        assertThat(allocation.uncoveredDeviceIds()).isEmpty();
    }

    @Test
    void devicesAfterARemovedOneMoveUpInTheQueue() {
        var remaining = devices(1, 20).stream().filter(id -> id != 3L).toList();
        var allocation = EdgeGatewayAllocation.of(List.of(90L, 91L), remaining);

        assertThat(allocation.deviceIdsOf(90L)).hasSize(15).contains(16L).doesNotContain(3L);
        assertThat(allocation.deviceIdsOf(91L)).containsExactly(17L, 18L, 19L, 20L);
    }

    private static List<Long> devices(long from, long to) {
        return LongStream.rangeClosed(from, to).boxed().toList();
    }
}
