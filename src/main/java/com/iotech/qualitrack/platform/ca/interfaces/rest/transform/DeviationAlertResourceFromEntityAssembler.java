package com.iotech.qualitrack.platform.ca.interfaces.rest.transform;

import com.iotech.qualitrack.platform.ca.domain.model.aggregates.DeviationAlert;
import com.iotech.qualitrack.platform.ca.interfaces.rest.resources.DeviationAlertResource;

/**
 * Assembler to convert DeviationAlert domain entities into REST resources.
 */
public class DeviationAlertResourceFromEntityAssembler {

    public static DeviationAlertResource toResourceFromEntity(DeviationAlert entity) {
        return new DeviationAlertResource(
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
}