package com.iotech.qualitrack.platform.laboratory.application.internal.queryservices;

import com.iotech.qualitrack.platform.laboratory.application.queryservices.LaboratoryQueryService;
import com.iotech.qualitrack.platform.laboratory.domain.model.aggregates.Laboratory;
import com.iotech.qualitrack.platform.laboratory.domain.model.queries.GetLaboratoryByIdQuery;
import com.iotech.qualitrack.platform.laboratory.domain.repositories.LaboratoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Application service implementation that resolves laboratory read queries.
 */
@Service
public class LaboratoryQueryServiceImpl implements LaboratoryQueryService {
    private final LaboratoryRepository laboratoryRepository;

    public LaboratoryQueryServiceImpl(LaboratoryRepository laboratoryRepository) {
        this.laboratoryRepository = laboratoryRepository;
    }

    @Override
    public Optional<Laboratory> handle(GetLaboratoryByIdQuery query) {
        return laboratoryRepository.findById(query.laboratoryId());
    }
}