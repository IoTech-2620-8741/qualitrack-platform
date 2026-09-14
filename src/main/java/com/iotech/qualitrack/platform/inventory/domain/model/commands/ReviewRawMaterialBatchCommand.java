package com.iotech.qualitrack.platform.inventory.domain.model.commands;
public record ReviewRawMaterialBatchCommand(Long laboratoryId, Long receiptId, com.iotech.qualitrack.platform.inventory.domain.model.valueobjects.RawMaterialBatchStatus status, String reason) { }
