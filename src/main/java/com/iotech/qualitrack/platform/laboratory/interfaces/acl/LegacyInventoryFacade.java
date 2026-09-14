package com.iotech.qualitrack.platform.laboratory.interfaces.acl;

import java.math.BigDecimal;
import java.util.List;

/** Read-only bridge for the explicit transfer of pre-Inventory opening balances. */
public interface LegacyInventoryFacade {
    List<Material> materials(Long laboratoryId);
    Material lock(Long laboratoryId, Long materialId);
    record Material(Long id, String code, String name, String unit, BigDecimal minimumStock,
        String supplier, String batchNumber, String expiresOn, BigDecimal balance) { }
}
