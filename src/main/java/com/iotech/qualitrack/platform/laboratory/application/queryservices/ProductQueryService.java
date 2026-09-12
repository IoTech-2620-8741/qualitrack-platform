package com.iotech.qualitrack.platform.laboratory.application.queryservices;

import com.iotech.qualitrack.platform.laboratory.domain.model.aggregates.PharmaceuticalProduct;
import com.iotech.qualitrack.platform.laboratory.domain.model.queries.GetProductsByLabIdQuery;

import java.util.List;

/**
 * Application service contract for pharmaceutical product read queries.
 */
public interface ProductQueryService {

    /**
     * Handles retrieval of all pharmaceutical products associated with a specific laboratory.
     *
     * @param query laboratory-id query
     * @return list of pharmaceutical products for the given laboratory
     * @see GetProductsByLabIdQuery
     */
    List<PharmaceuticalProduct> handle(GetProductsByLabIdQuery query);
}