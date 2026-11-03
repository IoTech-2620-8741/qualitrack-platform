package com.iotech.qualitrack.platform.ca.interfaces.acl;


import com.iotech.qualitrack.platform.ca.domain.model.aggregates.DeviationAlert;
import com.iotech.qualitrack.platform.ca.domain.repositories.ComplianceEventRepository;
import com.iotech.qualitrack.platform.ca.domain.repositories.DeviationAlertRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * ACL facade exposed by the Compliance and Alerts bounded context.
 *
 * <p>Provides a stable interface for other bounded contexts to interact with CA
 * without depending on CA internal application services or domain commands.</p>
 */
@Service
public class ComplianceContextFacade {

    private final ComplianceEventRepository complianceEventRepository;
    private final DeviationAlertRepository deviationAlertRepository;

    /**
     * Creates a new ComplianceContextFacade.
     *
     * @param complianceEventRepository compliance event repository
     * @param deviationAlertRepository deviation alert repository
     */
    public ComplianceContextFacade(ComplianceEventRepository complianceEventRepository,
                                   DeviationAlertRepository deviationAlertRepository) {
        this.complianceEventRepository = complianceEventRepository;
        this.deviationAlertRepository = deviationAlertRepository;
    }

    /**
     * Alerts of an environment and its containers whose incident started in a period, oldest first (US95).
     *
     * @param laboratoryId the laboratory of the environment
     * @param environmentId the environment
     * @param from start of the period, inclusive
     * @param to end of the period, inclusive
     * @return the alerts with their current status
     */
    public List<AlertReference> findEnvironmentAlerts(Long laboratoryId, Long environmentId, Instant from, Instant to) {
        if (laboratoryId == null || environmentId == null || from == null || to == null) return List.of();
        return deviationAlertRepository.findAllByLaboratoryIdAndEnvironmentId(laboratoryId, environmentId).stream()
                .filter(alert -> alert.detectedAt() != null && !alert.detectedAt().isBefore(from) && !alert.detectedAt().isAfter(to))
                .sorted(Comparator.comparing(DeviationAlert::detectedAt))
                .map(ComplianceContextFacade::toReference)
                .toList();
    }

    /**
     * Alerts recorded for a product batch, in the order they were recorded (US96).
     *
     * @param batchId the product batch
     * @return the alerts with their current status
     */
    public List<AlertReference> findBatchAlerts(Long batchId) {
        if (batchId == null) return List.of();
        return deviationAlertRepository.findAllByBatchId(batchId).stream()
                .map(ComplianceContextFacade::toReference)
                .toList();
    }

    /**
     * Alerts detected by any of the given devices, used by the operational counts of the indicators (US93).
     *
     * @param deviceIds the equipment identifiers
     * @return the alerts with their current status, grouped by device
     */
    public List<AlertReference> findDeviceAlerts(Collection<Long> deviceIds) {
        return deviceIds.stream()
                .flatMap(deviceId -> deviationAlertRepository.findAllByEquipmentId(deviceId).stream())
                .map(ComplianceContextFacade::toReference)
                .toList();
    }

    private static AlertReference toReference(DeviationAlert alert) {
        return new AlertReference(alert.getId(), alert.getEquipmentId(),
                alert.getOrigin() == null ? null : alert.getOrigin().name(), alert.getParameterName(),
                alert.getRecordedValue(), alert.getThresholdValue(), alert.getUnit(), alert.detectedAt(),
                alert.getSeverity().name(), alert.getStatus().name(), alert.getDeviationCount(),
                alert.getResolutionNotes(), alert.getTimestamp());
    }

    /**
     * Deviation alert shared with other bounded contexts.
     *
     * @param id the alert
     * @param deviceId the environmental device or container monitor that detected it
     * @param origin ENVIRONMENT or CONTAINER
     * @param parameterName the monitored variable
     * @param recordedValue the value of the deviation that set the current severity
     * @param thresholdValue the limit crossed by that value
     * @param unit the unit of the value
     * @param detectedAt when the incident started
     * @param severity the highest severity of the incident
     * @param status the current status
     * @param deviationCount deviations grouped in the incident
     * @param resolutionNotes notes of the resolution, if resolved
     * @param recordedAt when the incident started as it was stored, also for older alerts whose time is not an
     *                   ISO-8601 instant
     */
    public record AlertReference(Long id, Long deviceId, String origin, String parameterName, Double recordedValue,
                                 Double thresholdValue, String unit, Instant detectedAt, String severity,
                                 String status, Integer deviationCount, String resolutionNotes, String recordedAt) {
        /**
         * @return true while the incident is not resolved
         */
        public boolean isOpen() {
            return !"RESOLVED".equals(status);
        }
    }

    /**
     * Verifies whether a batch can be released according to compliance rules.
     *
     * <p>At this stage, the rule is intentionally conservative and simple:
     * a batch can be released when there are no unresolved non-compliance events
     * associated with that batch identifier.</p>
     *
     * @param batchId the batch identifier
     * @return true when the batch can be released
     */
    public boolean canReleaseBatch(Long batchId) {
        if (batchId == null || batchId <= 0) {
            return false;
        }

        return complianceEventRepository.findAllByRelatedEntityId(batchId).stream()
                .filter(event -> event.getEventType().subject()
                        == com.iotech.qualitrack.platform.ca.domain.model.valueobjects.ComplianceEventSubject.BATCH)
                .noneMatch(event -> !event.isResolved());
    }
}
