package com.iotech.qualitrack.platform.inventory.interfaces.rest.resources;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
public record SaveRawMaterialResource(@NotBlank @Size(max=50) String code, @NotBlank @Size(max=150) String name,
    @NotBlank String unit, @NotNull BigDecimal minimumStock) { }
