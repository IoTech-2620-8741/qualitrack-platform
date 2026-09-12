package com.iotech.qualitrack.platform.ra.application.internal.queryservices;

import com.iotech.qualitrack.platform.ra.application.queryservices.ReportDocumentQueryService;
import com.iotech.qualitrack.platform.ra.domain.model.queries.GetReportDocumentByIdQuery;
import com.iotech.qualitrack.platform.ra.domain.model.valueobjects.ReportDocument;
import com.iotech.qualitrack.platform.ra.domain.repositories.AuditReportRepository;
import com.iotech.qualitrack.platform.ra.domain.repositories.ReportDocumentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ReportDocumentQueryServiceImpl implements ReportDocumentQueryService {
    private final AuditReportRepository reports;
    private final ReportDocumentRepository documents;

    public ReportDocumentQueryServiceImpl(AuditReportRepository reports, ReportDocumentRepository documents) {
        this.reports = reports;
        this.documents = documents;
    }

    @Override
    public Optional<ReportDocument> handle(GetReportDocumentByIdQuery query) {
        return reports.findById(query.reportId()).flatMap(report -> documents.findByReportId(query.reportId())
                .map(document -> {
                    if (!report.getChecksum().equals(report.computeChecksum(document.content()))) {
                        throw new IllegalStateException("Stored report checksum mismatch");
                    }
                    return document;
                }));
    }
}
