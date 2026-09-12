package com.iotech.qualitrack.platform.ra.infrastructure.persistence.jpa.adapters;

import com.iotech.qualitrack.platform.ra.domain.model.valueobjects.ReportDocument;
import com.iotech.qualitrack.platform.ra.domain.repositories.ReportDocumentRepository;
import com.iotech.qualitrack.platform.ra.infrastructure.persistence.jpa.entities.ReportDocumentPersistenceEntity;
import com.iotech.qualitrack.platform.ra.infrastructure.persistence.jpa.repositories.ReportDocumentPersistenceRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class ReportDocumentRepositoryImpl implements ReportDocumentRepository {
    private final ReportDocumentPersistenceRepository repository;

    public ReportDocumentRepositoryImpl(ReportDocumentPersistenceRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(ReportDocument document) {
        if (repository.existsById(document.reportId())) throw new IllegalStateException("Report content is immutable");
        var entity = new ReportDocumentPersistenceEntity();
        entity.setReportId(document.reportId());
        entity.setFormat(document.format());
        entity.setContent(document.content());
        repository.save(entity);
    }

    @Override
    public Optional<ReportDocument> findByReportId(Long reportId) {
        return repository.findById(reportId)
                .map(entity -> new ReportDocument(entity.getReportId(), entity.getFormat(), entity.getContent()));
    }
}
