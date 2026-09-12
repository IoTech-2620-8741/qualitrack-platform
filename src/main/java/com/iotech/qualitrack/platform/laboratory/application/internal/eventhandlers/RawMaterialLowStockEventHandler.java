package com.iotech.qualitrack.platform.laboratory.application.internal.eventhandlers;

import com.iotech.qualitrack.platform.laboratory.domain.model.events.RawMaterialLowStockEvent;
import com.iotech.qualitrack.platform.laboratory.interfaces.events.RawMaterialLowStockIntegrationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Handles internal RawMaterialLowStockEvent events and publishes the corresponding integration event.
 */
@Service
@Slf4j
public class RawMaterialLowStockEventHandler {

    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * Creates a new RawMaterialLowStockEventHandler.
     *
     * @param applicationEventPublisher Spring application event publisher
     */
    public RawMaterialLowStockEventHandler(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * Handles the internal raw material low stock domain event.
     *
     * @param event the internal raw material low stock domain event
     */
    @EventListener(RawMaterialLowStockEvent.class)
    public void on(RawMaterialLowStockEvent event) {
        log.warn(
                "Raw material low stock. rawMaterialId={}, laboratoryId={}, materialName={}, currentStock={}, minimumThreshold={}",
                event.rawMaterialId(),
                event.laboratoryId(),
                event.materialName(),
                event.currentStock(),
                event.minimumThreshold()
        );

        applicationEventPublisher.publishEvent(RawMaterialLowStockIntegrationEvent.from(event));
    }
}