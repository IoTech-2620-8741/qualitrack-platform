package com.iotech.qualitrack.platform.ra.infrastructure.persistence.jpa.repositories;

import com.iotech.qualitrack.platform.ra.infrastructure.persistence.jpa.entities.ReportDocumentPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportDocumentPersistenceRepository extends JpaRepository<ReportDocumentPersistenceEntity, Long> {}
