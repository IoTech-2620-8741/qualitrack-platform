package com.iotech.qualitrack.platform.ra.application.internal.eventhandlers;

import com.iotech.qualitrack.platform.ra.domain.model.events.KpiDashboardCalculatedEvent;
import com.iotech.qualitrack.platform.ra.interfaces.events.KpiDashboardCalculatedIntegrationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Application-layer event handler for {@link KpiDashboardCalculatedEvent}.
 *
 * <p>Publishes the Reporting & Audit integration event after a KPI dashboard
 * is calculated, exposing RA's published language to other bounded contexts.</p>
 */
@Service
@Slf4j
public class KpiDashboardCalculatedEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    /**
     * Creates the handler with the Spring application event publisher.
     *
     * @param eventPublisher Spring application event publisher
     */
    public KpiDashboardCalculatedEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    /**
     * Handles the {@link KpiDashboardCalculatedEvent}.
     *
     * @param event the {@link KpiDashboardCalculatedEvent} published by the RA bounded context
     */
    @EventListener(KpiDashboardCalculatedEvent.class)
    public void on(KpiDashboardCalculatedEvent event) {
        log.info(
                "KPI dashboard calculated: Dashboard ID='{}', Laboratory ID='{}', Overall Health='{}', Metric Count='{}', CalculatedAt='{}'.",
                event.dashboardId(),
                event.laboratoryId(),
                event.overallHealthScore(),
                event.metricCount(),
                event.calculatedAt()
        );

        eventPublisher.publishEvent(KpiDashboardCalculatedIntegrationEvent.from(event));
    }
}