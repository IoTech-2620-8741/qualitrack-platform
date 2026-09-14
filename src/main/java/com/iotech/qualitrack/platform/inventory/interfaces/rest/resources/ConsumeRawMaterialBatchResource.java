package com.iotech.qualitrack.platform.inventory.interfaces.rest.resources;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
public record ConsumeRawMaterialBatchResource(@NotNull @Positive Long receiptId, @NotNull @Positive Long productBatchId,
    @NotNull BigDecimal amount, @NotBlank String unit, @NotBlank @Size(max=100) String operationId) { }
