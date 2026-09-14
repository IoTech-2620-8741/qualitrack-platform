package com.iotech.qualitrack.platform.inventory.interfaces.rest.resources;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
public record ReceiveRawMaterialBatchResource(@NotBlank @Size(max=150) String supplier, @NotBlank @Size(max=50) String batchNumber,
    @NotBlank String unit, @NotNull BigDecimal amount, @NotNull LocalDate receivedOn, @NotNull LocalDate expiresOn) { }
