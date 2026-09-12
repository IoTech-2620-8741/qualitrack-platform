package com.iotech.qualitrack.platform.ra.application.queryservices;

import com.iotech.qualitrack.platform.ra.domain.model.queries.GetReportDocumentByIdQuery;
import com.iotech.qualitrack.platform.ra.domain.model.valueobjects.ReportDocument;
import java.util.Optional;

public interface ReportDocumentQueryService {
    Optional<ReportDocument> handle(GetReportDocumentByIdQuery query);
}
