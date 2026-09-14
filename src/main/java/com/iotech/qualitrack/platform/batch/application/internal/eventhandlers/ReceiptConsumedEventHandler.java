package com.iotech.qualitrack.platform.batch.application.internal.eventhandlers;

import com.iotech.qualitrack.platform.inventory.interfaces.events.ReceiptConsumedIntegrationEvent;
import com.iotech.qualitrack.platform.batch.domain.model.entities.RawMaterialUsage;
import com.iotech.qualitrack.platform.batch.domain.model.events.RawMaterialLinkedToBatchEvent;
import com.iotech.qualitrack.platform.batch.domain.repositories.RawMaterialUsageRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

@Component
public class ReceiptConsumedEventHandler {
    private final RawMaterialUsageRepository usages;
    private final ApplicationEventPublisher events;
    public ReceiptConsumedEventHandler(RawMaterialUsageRepository usages, ApplicationEventPublisher events) {
        this.usages = usages; this.events = events;
    }
    @EventListener
    @Transactional(propagation = Propagation.MANDATORY)
    public void on(ReceiptConsumedIntegrationEvent event) {
        var usage = new RawMaterialUsage(null, event.productBatchId(), event.materialId(), event.materialName(),
            event.amount().doubleValue(), event.unit(), event.occurredAt().toString());
        usage.recordStockChange(event.stockBefore(), event.stockAfter());
        usage.assignInventoryReceipt(event.receiptId());
        events.publishEvent(RawMaterialLinkedToBatchEvent.from(usages.save(usage)));
    }
}
