package com.iotech.qualitrack.platform.laboratory.application.queryservices;

import com.iotech.qualitrack.platform.laboratory.domain.model.aggregates.RawMaterial;
import com.iotech.qualitrack.platform.laboratory.domain.model.queries.GetLowStockMaterialsByLabIdQuery;
import com.iotech.qualitrack.platform.laboratory.domain.model.queries.GetRawMaterialsByLabIdQuery;

import java.util.List;

/**
 * Application service contract for raw material read queries.
 */
public interface RawMaterialQueryService {

    /**
     * Handles retrieval of all raw materials belonging to a specific laboratory.
     *
     * @param query laboratory-id query
     * @return list of raw materials for the given laboratory
     * @see GetRawMaterialsByLabIdQuery
     */
    List<RawMaterial> handle(GetRawMaterialsByLabIdQuery query);

    /**
     * Handles retrieval of raw materials that are below their minimum safety stock threshold.
     *
     * @param query laboratory-id query
     * @return list of low-stock raw materials for the given laboratory
     * @see GetLowStockMaterialsByLabIdQuery
     */
    List<RawMaterial> handle(GetLowStockMaterialsByLabIdQuery query);
}