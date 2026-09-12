package com.iotech.qualitrack.platform.laboratory.interfaces.acl;

import java.math.BigDecimal;

/** Transactional inventory capability exposed to the Batch context. */
public interface RawMaterialStockFacade {
    StockConsumption consume(Long materialId, Long laboratoryId, BigDecimal quantity, String unit);

    record StockConsumption(String materialName, String unit, BigDecimal stockBefore, BigDecimal stockAfter) { }
}
