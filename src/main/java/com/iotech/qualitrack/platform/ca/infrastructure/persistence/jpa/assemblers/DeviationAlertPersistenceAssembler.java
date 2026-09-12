package com.iotech.qualitrack.platform.ca.infrastructure.persistence.jpa.assemblers;

import com.iotech.qualitrack.platform.ca.domain.model.aggregates.DeviationAlert;
import com.iotech.qualitrack.platform.ca.infrastructure.persistence.jpa.entities.DeviationAlertPersistenceEntity;

/**
 * Static assembler between deviation alert domain and persistence representations.
 */
public final class DeviationAlertPersistenceAssembler {

    private DeviationAlertPersistenceAssembler() {
    }

    public static DeviationAlert toDomainFromPersistence(DeviationAlertPersistenceEntity entity) {
        if (entity == null) return null;

        return new DeviationAlert(
                entity.getId(),
                entity.getEquipmentId(),
                entity.getBatchId(),
                entity.getParameterName(),
                entity.getRecordedValue(),
                entity.getThresholdValue(),
                entity.getUnit(),
                entity.getTimestamp(),
                entity.getSeverity(),
                entity.getStatus(),
                entity.getAcknowledgedBy(),
                entity.getResolvedBy(),
                entity.getResolutionNotes()
        );
    }

    public static DeviationAlertPersistenceEntity toPersistenceFromDomain(DeviationAlert alert) {
        if (alert == null) return null;

        var entity = new DeviationAlertPersistenceEntity();

        if (alert.getId() != null) {
            entity.setId(alert.getId());
        }

        entity.setEquipmentId(alert.getEquipmentId());
        entity.setBatchId(alert.getBatchId());
        entity.setParameterName(alert.getParameterName());
        entity.setRecordedValue(alert.getRecordedValue());
        entity.setThresholdValue(alert.getThresholdValue());
        entity.setUnit(alert.getUnit());
        entity.setTimestamp(alert.getTimestamp());
        entity.setSeverity(alert.getSeverity());
        entity.setStatus(alert.getStatus());
        entity.setAcknowledgedBy(alert.getAcknowledgedBy());
        entity.setResolvedBy(alert.getResolvedBy());
        entity.setResolutionNotes(alert.getResolutionNotes());

        return entity;
    }
}