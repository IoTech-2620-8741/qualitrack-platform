package com.iotech.qualitrack.platform.ra.domain.model.queries;

public record GetReportDocumentByIdQuery(Long reportId) {
    public GetReportDocumentByIdQuery {
        if (reportId == null || reportId <= 0) throw new IllegalArgumentException("A report ID is required");
    }
}
