package com.iotech.qualitrack.platform.ra.domain.repositories;

import com.iotech.qualitrack.platform.ra.domain.model.valueobjects.ReportDocument;
import java.util.Optional;

public interface ReportDocumentRepository {
    void save(ReportDocument document);
    Optional<ReportDocument> findByReportId(Long reportId);
}
