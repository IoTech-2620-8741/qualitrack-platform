package com.iotech.qualitrack.platform.ra.domain.model.aggregates;

import com.iotech.qualitrack.platform.ra.domain.model.valueobjects.ReportType;
import com.iotech.qualitrack.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;

import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.HexFormat;
import java.util.Objects;

/**
 * Aggregate root representing an immutable generated audit report.
 *
 * <p>Audit reports are generated artifacts used for traceability, compliance,
 * KPI review, equipment logs, or batch documentation. The checksum protects the
 * integrity of the generated content.</p>
 */
@Getter
public class AuditReport extends AbstractDomainAggregateRoot<AuditReport> {

    /**
     * The unique numeric identifier of the report.
     */
    private Long id;

    /**
     * The numeric identifier of the laboratory associated with this report.
     */
    private Long laboratoryId;

    /**
     * The numeric identifier of the production batch, if applicable.
     */
    private Long batchId;

    /**
     * The numeric identifier of the equipment, if applicable.
     */
    private Long equipmentId;

    /**
     * The numeric identifier of the user who generated the report.
     */
    private Long generatedBy;

    /**
     * Human-readable name of the user who generated the report.
     */
    private String generatedByName;

    /**
     * Type of generated report.
     */
    private ReportType reportType;

    /**
     * Start timestamp of the reporting period, if applicable.
     */
    private String dateRangeFrom;

    /**
     * End timestamp of the reporting period, if applicable.
     */
    private String dateRangeTo;

    /**
     * Path or logical reference where the generated file is stored.
     */
    private String filePath;

    /**
     * SHA-256 checksum of the generated report content.
     */
    private String checksum;

    /**
     * Timestamp when the report was generated.
     */
    private String generatedAt;

    /**
     * Default constructor.
     * Required by the persistence and mapping layers to reconstruct the aggregate.
     */
    public AuditReport() {
        // Required for reconstruction by JPA or Assemblers
    }

    /**
     * Reconstructs an AuditReport aggregate from persistence data.
     *
     * @param id The report identifier.
     * @param laboratoryId The laboratory identifier.
     * @param batchId The batch identifier, if applicable.
     * @param equipmentId The equipment identifier, if applicable.
     * @param generatedBy The generating user identifier.
     * @param generatedByName The generating user display name.
     * @param reportType The report type.
     * @param dateRangeFrom The start of the reporting period.
     * @param dateRangeTo The end of the reporting period.
     * @param filePath The stored file path or logical reference.
     * @param checksum The generated content checksum.
     * @param generatedAt The generation timestamp.
     */
    public AuditReport(
            Long id,
            Long laboratoryId,
            Long batchId,
            Long equipmentId,
            Long generatedBy,
            String generatedByName,
            ReportType reportType,
            String dateRangeFrom,
            String dateRangeTo,
            String filePath,
            String checksum,
            String generatedAt
    ) {
        this.id = id;
        this.laboratoryId = laboratoryId;
        this.batchId = batchId;
        this.equipmentId = equipmentId;
        this.generatedBy = generatedBy;
        this.generatedByName = generatedByName;
        this.reportType = reportType;
        this.dateRangeFrom = dateRangeFrom;
        this.dateRangeTo = dateRangeTo;
        this.filePath = filePath;
        this.checksum = checksum;
        this.generatedAt = generatedAt;
    }

    /**
     * Creates a new immutable audit report record.
     *
     * @param laboratoryId The laboratory identifier.
     * @param batchId The batch identifier, if applicable.
     * @param equipmentId The equipment identifier, if applicable.
     * @param generatedBy The generating user identifier.
     * @param generatedByName The generating user display name.
     * @param reportType The report type.
     * @param dateRangeFrom The start of the reporting period.
     * @param dateRangeTo The end of the reporting period.
     * @param filePath The stored file path or logical reference.
     * @param content The generated report content.
     */
    public AuditReport(
            Long laboratoryId,
            Long batchId,
            Long equipmentId,
            Long generatedBy,
            String generatedByName,
            ReportType reportType,
            String dateRangeFrom,
            String dateRangeTo,
            String filePath,
            byte[] content
    ) {
        this.laboratoryId = Objects.requireNonNull(laboratoryId, "laboratoryId cannot be null");
        this.batchId = batchId;
        this.equipmentId = equipmentId;
        this.generatedBy = Objects.requireNonNull(generatedBy, "generatedBy cannot be null");
        this.generatedByName = Objects.requireNonNull(generatedByName, "generatedByName cannot be null");
        this.reportType = Objects.requireNonNull(reportType, "reportType cannot be null");
        this.dateRangeFrom = dateRangeFrom;
        this.dateRangeTo = dateRangeTo;
        this.filePath = Objects.requireNonNull(filePath, "filePath cannot be null");
        this.checksum = computeChecksum(content);
        this.generatedAt = LocalDateTime.now().toString();
    }

    /**
     * Computes a SHA-256 checksum for the generated report content.
     *
     * @param content The generated report content.
     * @return SHA-256 checksum represented as hexadecimal text.
     */
    public String computeChecksum(byte[] content) {
        try {
            var digest = MessageDigest.getInstance("SHA-256");
            var hash = digest.digest(content == null ? new byte[0] : content);
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException("SHA-256 is required for report integrity", exception);
        }
    }

    /**
     * Indicates whether this report is immutable by domain policy.
     *
     * @return Always true for generated audit reports.
     */
    public boolean isImmutable() {
        return true;
    }

    /**
     * Indicates whether the report is associated with a batch.
     *
     * @return true if this report has a batch identifier.
     */
    public boolean isBatchReport() {
        return batchId != null;
    }

    /**
     * Indicates whether the report is associated with equipment.
     *
     * @return true if this report has an equipment identifier.
     */
    public boolean isEquipmentReport() {
        return equipmentId != null;
    }
}
