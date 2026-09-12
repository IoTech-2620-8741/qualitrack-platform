package com.iotech.qualitrack.platform.ra.application.internal.eventhandlers;

import com.iotech.qualitrack.platform.ra.domain.model.events.DeviationTrendCalculatedEvent;
import com.iotech.qualitrack.platform.ra.interfaces.events.DeviationTrendCalculatedIntegrationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Application-layer event handler for {@link DeviationTrendCalculatedEvent}.
 *
 * <p>Publishes the Reporting & Audit integration event after a deviation trend
 * is calculated, exposing RA's published language to other bounded contexts.</p>
 */
@Service
@Slf4j
public class DeviationTrendCalculatedEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    /**
     * Creates the handler with the Spring application event publisher.
     *
     * @param eventPublisher Spring application event publisher
     */
    public DeviationTrendCalculatedEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    /**
     * Handles the {@link DeviationTrendCalculatedEvent}.
     *
     * @param event the {@link DeviationTrendCalculatedEvent} published by the RA bounded context
     */
    @EventListener(DeviationTrendCalculatedEvent.class)
    public void on(DeviationTrendCalculatedEvent event) {
        log.info(
                "Deviation trend calculated: Trend ID='{}', Equipment ID='{}', Parameter='{}', Direction='{}', CalculatedAt='{}'.",
                event.trendId(),
                event.equipmentId(),
                event.parameterName(),
                event.trendDirection(),
                event.calculatedAt()
        );

        eventPublisher.publishEvent(DeviationTrendCalculatedIntegrationEvent.from(event));
    }
}