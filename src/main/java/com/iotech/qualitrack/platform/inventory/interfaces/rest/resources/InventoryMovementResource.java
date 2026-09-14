package com.iotech.qualitrack.platform.inventory.interfaces.rest.resources;

import java.math.BigDecimal;
import java.time.Instant;

public record InventoryMovementResource(
        Long id, Long laboratoryId, Long materialId, Long receiptId, Long productBatchId, String type, BigDecimal amount, String unit, BigDecimal stockBefore, BigDecimal stockAfter, String statusBefore, String statusAfter, String reason, Long actorId, Instant occurredAt, String operationId) { }
