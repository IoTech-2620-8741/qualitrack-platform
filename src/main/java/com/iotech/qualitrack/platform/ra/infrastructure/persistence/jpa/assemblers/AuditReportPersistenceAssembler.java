package com.iotech.qualitrack.platform.ra.infrastructure.persistence.jpa.assemblers;

import com.iotech.qualitrack.platform.ra.domain.model.aggregates.AuditReport;
import com.iotech.qualitrack.platform.ra.infrastructure.persistence.jpa.entities.AuditReportPersistenceEntity;

/**
 * Static assembler between audit report domain and persistence representations.
 */
public final class AuditReportPersistenceAssembler {

    private AuditReportPersistenceAssembler() {
    }

    public static AuditReport toDomainFromPersistence(AuditReportPersistenceEntity entity) {
        if (entity == null) return null;

        return new AuditReport(
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

    public static AuditReportPersistenceEntity toPersistenceFromDomain(AuditReport report) {
        if (report == null) return null;

        var entity = new AuditReportPersistenceEntity();

        if (report.getId() != null) {
            entity.setId(report.getId());
        }

        entity.setLaboratoryId(report.getLaboratoryId());
        entity.setBatchId(report.getBatchId());
        entity.setEquipmentId(report.getEquipmentId());
        entity.setGeneratedBy(report.getGeneratedBy());
        entity.setGeneratedByName(report.getGeneratedByName());
        entity.setReportType(report.getReportType());
        entity.setDateRangeFrom(report.getDateRangeFrom());
        entity.setDateRangeTo(report.getDateRangeTo());
        entity.setFilePath(report.getFilePath());
        entity.setChecksum(report.getChecksum());
        entity.setGeneratedAt(report.getGeneratedAt());

        return entity;
    }
}