package com.iotech.qualitrack.platform.inventory.application.internal.outboundservices;
import com.iotech.qualitrack.platform.inventory.domain.model.aggregates.RawMaterialBatch;
import com.iotech.qualitrack.platform.inventory.domain.model.entities.InventoryMovement;
import com.iotech.qualitrack.platform.inventory.domain.repositories.InventoryRepository;
import com.iotech.qualitrack.platform.shared.application.security.CurrentUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.*;
import java.math.BigDecimal;
import java.time.Clock;

@Service
public class InventoryMovementRecorder {
    private final InventoryRepository repository;
    private final CurrentUser actor;
    private final Clock clock;
    public InventoryMovementRecorder(InventoryRepository repository, CurrentUser actor, Clock inventoryClock) {
        this.repository = repository;
        this.actor = actor;
        this.clock = inventoryClock;
    }
    @Transactional(propagation = Propagation.MANDATORY)
    public InventoryMovement record(RawMaterialBatch receipt, Long batch, String type, BigDecimal amount,
            BigDecimal before, String previousStatus, String reason, String operationId) {
        var user = actor.userId();
        if (user == null) throw new org.springframework.security.access.AccessDeniedException("Authenticated reviewer is required");
        return repository.append(new InventoryMovement(null, receipt.getLaboratoryId(), receipt.getRawMaterialId(), receipt.getId(),
            batch, type, amount, receipt.getUnit(), before, receipt.getAvailableAmount(), previousStatus,
            receipt.getStatus().name(), reason, user, clock.instant(), operationId));
    }
}
