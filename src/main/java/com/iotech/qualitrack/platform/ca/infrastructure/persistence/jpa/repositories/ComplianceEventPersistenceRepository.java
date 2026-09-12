package com.iotech.qualitrack.platform.ca.infrastructure.persistence.jpa.repositories;

import com.iotech.qualitrack.platform.ca.domain.model.valueobjects.ComplianceEventType;
import com.iotech.qualitrack.platform.ca.infrastructure.persistence.jpa.entities.ComplianceEventPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for {@link ComplianceEventPersistenceEntity}.
 *
 * <p>Handles database operations for the compliance_events table using Long as
 * the primary key. Provides custom queries for fetching audit trail entries
 * by related entity or event type.</p>
 */
@Repository
public interface ComplianceEventPersistenceRepository extends JpaRepository<ComplianceEventPersistenceEntity, Long> {

    /**
     * Finds all compliance events associated with a specific related entity.
     *
     * @param relatedEntityId The numeric ID of the related domain entity.
     */
    List<ComplianceEventPersistenceEntity> findAllByRelatedEntityId(Long relatedEntityId);

    /**
     * Finds all compliance events with a specific event type.
     *
     * @param eventType The compliance event type.
     */
    List<ComplianceEventPersistenceEntity> findAllByEventType(ComplianceEventType eventType);
}