package com.iotech.qualitrack.platform.inventory.domain.model.commands;
public record ReceiveRawMaterialBatchCommand(Long laboratoryId, Long materialId, String supplier, String batchNumber, String unit, java.math.BigDecimal amount, java.time.LocalDate receivedOn, java.time.LocalDate expiresOn) { }
