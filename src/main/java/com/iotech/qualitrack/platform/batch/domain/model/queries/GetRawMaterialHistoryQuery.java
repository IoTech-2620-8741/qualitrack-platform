package com.iotech.qualitrack.platform.batch.domain.model.queries;

public record GetRawMaterialHistoryQuery(Long rawMaterialId) {
    public GetRawMaterialHistoryQuery {
        if (rawMaterialId == null || rawMaterialId <= 0) throw new IllegalArgumentException("Invalid raw material ID");
    }
}
