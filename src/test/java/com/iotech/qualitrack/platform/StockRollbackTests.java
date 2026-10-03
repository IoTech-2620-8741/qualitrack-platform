package com.iotech.qualitrack.platform;

import com.iotech.qualitrack.platform.batch.application.commandservices.RawMaterialUsageCommandService;
import com.iotech.qualitrack.platform.batch.domain.model.aggregates.Batch;
import com.iotech.qualitrack.platform.batch.domain.model.commands.LinkRawMaterialCommand;
import com.iotech.qualitrack.platform.batch.domain.model.valueobjects.BatchStatus;
import com.iotech.qualitrack.platform.batch.domain.repositories.BatchRepository;
import com.iotech.qualitrack.platform.batch.domain.repositories.RawMaterialUsageRepository;
import com.iotech.qualitrack.platform.laboratory.domain.model.aggregates.RawMaterial;
import com.iotech.qualitrack.platform.laboratory.domain.repositories.RawMaterialRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

@SpringBootTest(properties = "spring.datasource.url=jdbc:h2:mem:stock_rollback;MODE=MySQL;NON_KEYWORDS=VALUE;DB_CLOSE_DELAY=-1")
class StockRollbackTests {
    @Autowired RawMaterialUsageCommandService commands;
    @Autowired RawMaterialRepository materials;
    @Autowired BatchRepository batches;
    @MockitoSpyBean RawMaterialUsageRepository usages;

    @Test
    void aFailedUsageWriteRollsBackTheAlreadyDecrementedInventory() {
        var material = materials.save(new RawMaterial(null, 42L, "Rollback fixture", "RM-ROLLBACK",
                "Supplier", "SUP-1", "2028-01-01", new BigDecimal("100"), "kg", BigDecimal.ZERO));
        var batch = batches.save(new Batch(null, 42L, null, 1L, "ROLLBACK-LOT", "Fixture", 10.0,
                "units", BatchStatus.IN_PROGRESS, "2026-09-08", null, "Test"));
        doThrow(new IllegalStateException("Simulated usage write failure")).when(usages).save(any());
        assertThatThrownBy(() -> commands.handle(new LinkRawMaterialCommand(batch.getId(), material.getId(), 50.0, "kg")))
                .isInstanceOf(org.springframework.dao.InvalidDataAccessApiUsageException.class)
                .hasRootCauseInstanceOf(IllegalStateException.class).hasMessageContaining("Simulated usage write failure");
        assertThat(materials.findById(material.getId()).orElseThrow().getCurrentStock()).isEqualByComparingTo("100");
        assertThat(usages.findAllByRawMaterialId(material.getId())).isEmpty();
    }
}
