package com.iotech.qualitrack.platform.inventory.interfaces.rest;

import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@Order(0)
@RestControllerAdvice(assignableTypes = {InventoryController.class, EnvironmentInventoryController.class})
public class InventoryExceptionHandler {
    @ExceptionHandler({DataIntegrityViolationException.class, PessimisticLockingFailureException.class})
    public ResponseEntity<?> conflict(RuntimeException exception) {
        return ResponseEntity.status(409).body(Map.of("code", "INVENTORY_CONFLICT",
            "message", "Concurrent change or duplicate inventory record. Reload and retry with the same operation ID."));
    }
}
