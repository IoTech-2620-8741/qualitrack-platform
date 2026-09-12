package com.iotech.qualitrack.platform.ra.interfaces.rest.transform;

import com.iotech.qualitrack.platform.ra.domain.model.aggregates.AuditReport;
import com.iotech.qualitrack.platform.ra.interfaces.rest.resources.AuditReportResource;

/**
 * Assembler that transforms audit report aggregate roots into REST resources.
 */
public final class AuditReportResourceFromEntityAssembler {
    private AuditReportResourceFromEntityAssembler() {
    }

    /**
     * Converts an audit report aggregate into its REST resource representation.
     *
     * @param entity the audit report aggregate
     * @return the audit report resource
     */
    public static AuditReportResource toResourceFromEntity(AuditReport entity) {
        return new AuditReportResource(
                entity.getId(),
                entity.getLaboratoryId(),
                entity.getBatchId(),
                entity.getEquipmentId(),
                entity.getGeneratedBy(),
                entity.getGeneratedByName(),
                entity.getReportType(),
                entity.getDateRangeFrom(),
                entity.getDateRangeTo(),
                entity.getFilePath(),
                entity.getChecksum(),
                entity.getGeneratedAt()
        );
    }
}