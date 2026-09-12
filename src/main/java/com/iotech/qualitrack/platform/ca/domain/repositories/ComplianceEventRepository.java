package com.iotech.qualitrack.platform.ca.domain.repositories;

import com.iotech.qualitrack.platform.ca.domain.model.entities.ComplianceEvent;
import com.iotech.qualitrack.platform.ca.domain.model.valueobjects.ComplianceEventType;

import java.util.List;
import java.util.Optional;

/**
 * ComplianceEvent repository port.
 *
 * <p>Handles the persistence contract for compliance audit trail entries.</p>
 */
public interface ComplianceEventRepository {

    Optional<ComplianceEvent> findById(Long id);

    List<ComplianceEvent> findAll();

    List<ComplianceEvent> findAllByRelatedEntityId(Long relatedEntityId);

    List<ComplianceEvent> findAllByEventType(ComplianceEventType eventType);

    ComplianceEvent save(ComplianceEvent complianceEvent);

    boolean existsById(Long id);

    void deleteById(Long id);
}