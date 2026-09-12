package com.iotech.qualitrack.platform.ca.application.internal.queryservices;

import com.iotech.qualitrack.platform.ca.application.queryservices.CaQueryService;
import com.iotech.qualitrack.platform.ca.domain.model.aggregates.DeviationAlert;
import com.iotech.qualitrack.platform.ca.domain.model.entities.ComplianceEvent;
import com.iotech.qualitrack.platform.ca.domain.model.entities.NotificationPreference;
import com.iotech.qualitrack.platform.ca.domain.model.queries.GetAlertByIdQuery;
import com.iotech.qualitrack.platform.ca.domain.model.queries.GetAlertsQuery;
import com.iotech.qualitrack.platform.ca.domain.model.queries.GetComplianceEventsByRelatedEntityIdQuery;
import com.iotech.qualitrack.platform.ca.domain.model.queries.GetNotificationPreferenceByUserIdQuery;
import com.iotech.qualitrack.platform.ca.domain.repositories.ComplianceEventRepository;
import com.iotech.qualitrack.platform.ca.domain.repositories.DeviationAlertRepository;
import com.iotech.qualitrack.platform.ca.domain.repositories.NotificationPreferenceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Application service implementation that executes CA read queries.
 *
 * <p>Handles retrieval of deviation alerts, compliance events, and notification
 * preferences using the domain repository ports.</p>
 */
@Service
public class CaQueryServiceImpl implements CaQueryService {

    private final DeviationAlertRepository deviationAlertRepository;
    private final ComplianceEventRepository complianceEventRepository;
    private final NotificationPreferenceRepository notificationPreferenceRepository;

    public CaQueryServiceImpl(
            DeviationAlertRepository deviationAlertRepository,
            ComplianceEventRepository complianceEventRepository,
            NotificationPreferenceRepository notificationPreferenceRepository
    ) {
        this.deviationAlertRepository = deviationAlertRepository;
        this.complianceEventRepository = complianceEventRepository;
        this.notificationPreferenceRepository = notificationPreferenceRepository;
    }

    @Override
    public List<DeviationAlert> handle(GetAlertsQuery query) {
        if (query.equipmentId() != null && query.status() != null) {
            return deviationAlertRepository.findAllByEquipmentIdAndStatus(
                    query.equipmentId(),
                    query.status()
            );
        }

        if (query.batchId() != null && query.status() != null) {
            return deviationAlertRepository.findAllByBatchIdAndStatus(
                    query.batchId(),
                    query.status()
            );
        }

        if (query.equipmentId() != null) {
            return deviationAlertRepository.findAllByEquipmentId(query.equipmentId());
        }

        if (query.batchId() != null) {
            return deviationAlertRepository.findAllByBatchId(query.batchId());
        }

        if (query.status() != null) {
            return deviationAlertRepository.findAllByStatus(query.status());
        }

        if (query.severity() != null) {
            return deviationAlertRepository.findAllBySeverity(query.severity());
        }

        return deviationAlertRepository.findAll();
    }

    @Override
    public Optional<DeviationAlert> handle(GetAlertByIdQuery query) {
        return deviationAlertRepository.findById(query.alertId());
    }

    @Override
    public List<ComplianceEvent> handle(GetComplianceEventsByRelatedEntityIdQuery query) {
        var direct = complianceEventRepository.findAllByRelatedEntityId(query.relatedEntityId()).stream()
                .filter(event -> event.getEventType().subject() == query.subject());
        var alerts = switch (query.subject()) {
            case EQUIPMENT -> deviationAlertRepository.findAllByEquipmentId(query.relatedEntityId());
            case BATCH -> deviationAlertRepository.findAllByBatchId(query.relatedEntityId());
            default -> List.<DeviationAlert>of();
        };
        var related = alerts.stream().flatMap(alert -> complianceEventRepository.findAllByRelatedEntityId(alert.getId()).stream())
                .filter(event -> event.getEventType().subject()
                        == com.iotech.qualitrack.platform.ca.domain.model.valueobjects.ComplianceEventSubject.ALERT);
        return java.util.stream.Stream.concat(direct, related)
                .sorted(java.util.Comparator.comparing(ComplianceEvent::getTimestamp)).toList();
    }

    @Override
    public Optional<NotificationPreference> handle(GetNotificationPreferenceByUserIdQuery query) {
        return notificationPreferenceRepository.findByUserId(query.userId());
    }
}
