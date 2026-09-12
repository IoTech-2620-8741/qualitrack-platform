package com.iotech.qualitrack.platform.ca.application.queryservices;

import com.iotech.qualitrack.platform.ca.domain.model.aggregates.DeviationAlert;
import com.iotech.qualitrack.platform.ca.domain.model.entities.ComplianceEvent;
import com.iotech.qualitrack.platform.ca.domain.model.entities.NotificationPreference;
import com.iotech.qualitrack.platform.ca.domain.model.queries.GetAlertByIdQuery;
import com.iotech.qualitrack.platform.ca.domain.model.queries.GetAlertsQuery;
import com.iotech.qualitrack.platform.ca.domain.model.queries.GetComplianceEventsByRelatedEntityIdQuery;
import com.iotech.qualitrack.platform.ca.domain.model.queries.GetNotificationPreferenceByUserIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Application service contract for compliance alerts, audit events, and notification preference read queries.
 */
public interface CaQueryService {

    /**
     * Handles retrieval of deviation alerts using optional filters.
     *
     * @param query alert filter query
     * @return list of deviation alerts matching the provided filters
     * @see GetAlertsQuery
     */
    List<DeviationAlert> handle(GetAlertsQuery query);

    /**
     * Handles retrieval of a deviation alert by its unique numeric ID.
     *
     * @param query alert-id query
     * @return matching deviation alert, if found
     * @see GetAlertByIdQuery
     */
    Optional<DeviationAlert> handle(GetAlertByIdQuery query);

    /**
     * Handles retrieval of compliance events related to a specific entity.
     *
     * @param query related-entity-id query
     * @return list of compliance events for the given related entity
     * @see GetComplianceEventsByRelatedEntityIdQuery
     */
    List<ComplianceEvent> handle(GetComplianceEventsByRelatedEntityIdQuery query);

    /**
     * Handles retrieval of notification preferences by user ID.
     *
     * @param query user-id query
     * @return matching notification preference, if found
     * @see GetNotificationPreferenceByUserIdQuery
     */
    Optional<NotificationPreference> handle(GetNotificationPreferenceByUserIdQuery query);
}