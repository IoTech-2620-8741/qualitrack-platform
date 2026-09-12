package com.iotech.qualitrack.platform.ra.infrastructure.persistence.jpa.entities;

import com.iotech.qualitrack.platform.ra.domain.model.valueobjects.ReportType;
import com.iotech.qualitrack.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA persistence entity representing a generated audit report.
 */
@Entity
@Table(name = "audit_reports")
@Getter
@Setter
@NoArgsConstructor
public class AuditReportPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "laboratory_id", nullable = false)
    private Long laboratoryId;

    @Column(name = "batch_id")
    private Long batchId;

    @Column(name = "equipment_id")
    private Long equipmentId;

    @Column(name = "generated_by", nullable = false)
    private Long generatedBy;

    @Column(name = "generated_by_name", nullable = false, length = 150)
    private String generatedByName;

    /**
     * Converted automatically by ReportTypePersistenceConverter.
     */
    @Column(name = "report_type", nullable = false, length = 50)
    private ReportType reportType;

    @Column(name = "date_range_from", length = 30)
    private String dateRangeFrom;

    @Column(name = "date_range_to", length = 30)
    private String dateRangeTo;

    @Column(name = "file_path", nullable = false, length = 500)
    private String filePath;

    @Column(nullable = false, length = 255)
    private String checksum;

    @Column(name = "generated_at", nullable = false, length = 30)
    private String generatedAt;
}