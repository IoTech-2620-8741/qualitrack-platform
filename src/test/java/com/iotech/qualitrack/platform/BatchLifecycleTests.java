package com.iotech.qualitrack.platform;

import com.iotech.qualitrack.platform.batch.domain.model.aggregates.Batch;
import com.iotech.qualitrack.platform.batch.domain.model.valueobjects.BatchStatus;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A batch is in progress from its first raw material consumption (US75) until it is released or rejected.
 */
class BatchLifecycleTests {
    private Batch batch(BatchStatus status) {
        return new Batch(1L, 1L, 2L, 3L, "Paracetamol 500 mg", "PB-LIFE", 1000.0, "units", status, "2026-10-01", null, null);
    }

    @Test void theFirstRawMaterialConsumptionStartsAPendingBatch() {
        var batch = batch(BatchStatus.PENDING);
        assertThat(batch.registerRawMaterialConsumption()).isTrue();
        assertThat(batch.getStatus()).isEqualTo(BatchStatus.IN_PROGRESS);
        assertThat(batch.registerRawMaterialConsumption()).isFalse();
        assertThat(batch.getStatus()).isEqualTo(BatchStatus.IN_PROGRESS);
        assertThat(batch.isOpen()).isTrue();
    }

    @Test void closedBatchesAreNotStartedAgain() {
        for (var status : List.of(BatchStatus.RELEASED, BatchStatus.REJECTED)) {
            var batch = batch(status);
            assertThat(batch.registerRawMaterialConsumption()).isFalse();
            assertThat(batch.getStatus()).isEqualTo(status);
        }
    }
}
