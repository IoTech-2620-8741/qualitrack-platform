package com.iotech.qualitrack.platform.ra.infrastructure.persistence.jpa.entities;

import com.iotech.qualitrack.platform.ra.domain.model.valueobjects.ReportFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "audit_report_documents")
@Getter
@Setter
@NoArgsConstructor
public class ReportDocumentPersistenceEntity {
    @Id
    @Column(name = "report_id")
    private Long reportId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private ReportFormat format;

    @Lob
    @Column(nullable = false, columnDefinition = "LONGBLOB")
    private byte[] content;
}
