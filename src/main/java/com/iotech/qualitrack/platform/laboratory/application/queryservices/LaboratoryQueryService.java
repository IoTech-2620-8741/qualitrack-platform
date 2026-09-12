package com.iotech.qualitrack.platform.laboratory.application.queryservices;

import com.iotech.qualitrack.platform.laboratory.domain.model.aggregates.Laboratory;
import com.iotech.qualitrack.platform.laboratory.domain.model.queries.GetLaboratoryByIdQuery;

import java.util.Optional;

/**
 * Application service contract for laboratory read queries.
 */
public interface LaboratoryQueryService {

    /**
     * Handles retrieval of a laboratory by its id.
     *
     * @param query laboratory-id query
     * @return matching laboratory, if found
     * @see GetLaboratoryByIdQuery
     */
    Optional<Laboratory> handle(GetLaboratoryByIdQuery query);
}