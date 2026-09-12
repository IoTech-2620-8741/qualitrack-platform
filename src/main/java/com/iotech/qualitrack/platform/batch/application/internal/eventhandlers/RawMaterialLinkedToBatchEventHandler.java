package com.iotech.qualitrack.platform.batch.application.internal.eventhandlers;

import com.iotech.qualitrack.platform.batch.domain.model.events.RawMaterialLinkedToBatchEvent;
import com.iotech.qualitrack.platform.batch.interfaces.events.RawMaterialLinkedToBatchIntegrationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Handles internal RawMaterialLinkedToBatchEvent events and publishes the corresponding integration event.
 */
@Slf4j
@Service
public class RawMaterialLinkedToBatchEventHandler {

    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * Creates a new RawMaterialLinkedToBatchEventHandler.
     *
     * @param applicationEventPublisher Spring application event publisher
     */
    public RawMaterialLinkedToBatchEventHandler(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * Handles the internal raw material linked domain event.
     *
     * @param event the internal raw material linked domain event
     */
    @EventListener(RawMaterialLinkedToBatchEvent.class)
    public void on(RawMaterialLinkedToBatchEvent event) {
        log.info(
                "Raw material linked to batch. usageId={}, batchId={}, rawMaterialId={}, quantityUsed={}, unit={}",
                event.usageId(),
                event.batchId(),
                event.rawMaterialId(),
                event.quantityUsed(),
                event.unit()
        );

        applicationEventPublisher.publishEvent(RawMaterialLinkedToBatchIntegrationEvent.from(event));
    }
}