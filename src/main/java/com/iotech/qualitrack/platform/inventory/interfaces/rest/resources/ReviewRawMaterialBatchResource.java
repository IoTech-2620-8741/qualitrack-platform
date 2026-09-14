package com.iotech.qualitrack.platform.inventory.interfaces.rest.resources;
import jakarta.validation.constraints.*;
import com.iotech.qualitrack.platform.inventory.domain.model.valueobjects.RawMaterialBatchStatus;
public record ReviewRawMaterialBatchResource(@NotNull RawMaterialBatchStatus status, @NotBlank @Size(max=500) String reason) { }
