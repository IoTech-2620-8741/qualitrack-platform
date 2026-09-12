package com.iotech.qualitrack.platform.ra.domain.model.valueobjects;

import java.util.Objects;

public record ReportDocument(Long reportId, ReportFormat format, byte[] content) {
    public ReportDocument {
        if (reportId == null || reportId <= 0) throw new IllegalArgumentException("A report ID is required");
        Objects.requireNonNull(format, "Report format is required");
        Objects.requireNonNull(content, "Report content is required");
        if (content.length == 0) throw new IllegalArgumentException("Report content cannot be empty");
        content = content.clone();
    }

    @Override
    public byte[] content() { return content.clone(); }
}
