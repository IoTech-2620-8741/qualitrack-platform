package com.iotech.qualitrack.platform.equipment.application.internal.eventhandlers;

import com.iotech.qualitrack.platform.equipment.domain.model.events.BpmParameterConfiguredEvent;
import com.iotech.qualitrack.platform.equipment.interfaces.events.BpmParameterConfiguredIntegrationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Handles BPM parameter configuration domain events and publishes Equipment integration events.
 */
@Service
@Slf4j
public class BpmParameterConfiguredEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    /**
     * Creates the handler with the Spring application event publisher.
     *
     * @param eventPublisher Spring application event publisher
     */
    public BpmParameterConfiguredEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    /**
     * Handles BPM parameter configuration events.
     *
     * @param event The internal Equipment domain event.
     */
    @EventListener(BpmParameterConfiguredEvent.class)
    public void on(BpmParameterConfiguredEvent event) {
        log.info(
                "BPM parameter configured: Config ID='{}', Equipment ID='{}', Parameter='{}', Min='{}', Max='{}', Unit='{}'.",
                event.configId(),
                event.equipmentId(),
                event.parameterName(),
                event.minValue(),
                event.maxValue(),
                event.unit()
        );

        eventPublisher.publishEvent(BpmParameterConfiguredIntegrationEvent.from(event));
    }
}