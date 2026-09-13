package com.iotech.qualitrack.platform.inventory.domain.model.valueobjects;

/** Receipt eligibility is separate from product batch status and expiration dates. */
public enum RawMaterialBatchStatus {
    QUARANTINED,
    RELEASED,
    OBSERVED,
    REJECTED
}
