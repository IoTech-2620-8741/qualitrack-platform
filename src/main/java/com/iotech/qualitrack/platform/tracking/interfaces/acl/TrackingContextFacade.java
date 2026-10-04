package com.iotech.qualitrack.platform.tracking.interfaces.acl;

import java.time.Instant;
import java.util.List;

/**
 * Anti-corruption facade that exposes Tracking &amp; Telemetry records to other bounded contexts.
 */
public interface TrackingContextFacade {

    /**
     * Actions executed by a container monitor for a metric in a period, oldest first (US86).
     *
     * @param laboratoryId the laboratory that owns the device
     * @param deviceId the container monitor
     * @param metric the metric that triggered the actions
     * @param from start of the period, inclusive
     * @param to end of the period, inclusive
     * @return the actions; empty when the device executed none
     */
    List<ActuationReference> findActuations(Long laboratoryId, Long deviceId, String metric, Instant from, Instant to);

    /**
     * Action executed by a device, shared with other bounded contexts.
     *
     * @param id the actuation event
     * @param action the executed action
     * @param triggerState the condition that triggered it, when reported
     * @param result EXECUTED or FAILED
     * @param occurredAt when the device executed it
     */
    record ActuationReference(Long id, String action, String triggerState, String result, Instant occurredAt) {
    }
}
