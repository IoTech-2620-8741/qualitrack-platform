package com.iotech.qualitrack.platform.inventory.application.commandservices;


/** Explicit opening-balance import; historical records remain in Laboratory. */
public interface InventoryImportService {
    Long importMaterial(Long laboratoryId, Long legacyId);
}
