package com.iotech.qualitrack.platform.tracking.application.acl;

import com.iotech.qualitrack.platform.tracking.domain.repositories.ActuationEventRepository;
import com.iotech.qualitrack.platform.tracking.interfaces.acl.TrackingContextFacade;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

/**
 * Implementation of {@link TrackingContextFacade} over the Tracking repositories.
 */
@Service
public class TrackingContextFacadeImpl implements TrackingContextFacade {
    private final ActuationEventRepository actuationEventRepository;

    public TrackingContextFacadeImpl(ActuationEventRepository actuationEventRepository) {
        this.actuationEventRepository = actuationEventRepository;
    }

    @Override
    public List<ActuationReference> findActuations(Long laboratoryId, Long deviceId, String metric, Instant from, Instant to) {
        if (laboratoryId == null || deviceId == null || metric == null || from == null || to == null || to.isBefore(from)) {
            return List.of();
        }
        return actuationEventRepository.findByDeviceAndPeriod(deviceId, from, to).stream()
                .filter(event -> laboratoryId.equals(event.getLaboratoryId()))
                .filter(event -> event.getTriggerMetric() != null && metric.equals(event.getTriggerMetric().name()))
                .map(event -> new ActuationReference(event.getId(), event.getAction().name(),
                        event.getTriggerState() == null ? null : event.getTriggerState().name(),
                        event.getResult().name(), event.getOccurredAt()))
                .toList();
    }
}
