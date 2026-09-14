package com.iotech.qualitrack.platform.inventory.application.internal.commandservices;
import com.iotech.qualitrack.platform.inventory.application.commandservices.InventoryCommandService;
import com.iotech.qualitrack.platform.inventory.application.internal.outboundservices.InventoryMovementRecorder;
import com.iotech.qualitrack.platform.inventory.domain.model.commands.*;
import com.iotech.qualitrack.platform.inventory.domain.model.aggregates.*;
import com.iotech.qualitrack.platform.inventory.domain.model.entities.InventoryMovement;
import com.iotech.qualitrack.platform.inventory.interfaces.events.ReceiptConsumedIntegrationEvent;
import com.iotech.qualitrack.platform.inventory.domain.model.valueobjects.*;
import com.iotech.qualitrack.platform.inventory.domain.repositories.InventoryRepository;
import com.iotech.qualitrack.platform.batch.interfaces.acl.BatchContextFacade;
import com.iotech.qualitrack.platform.shared.application.result.*;
import com.iotech.qualitrack.platform.shared.domain.model.valueobjects.StockUnit;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.*;

@Service
@Transactional
public class InventoryCommandServiceImpl implements InventoryCommandService {
    private final InventoryRepository repository;
    private final BatchContextFacade batches;
    private final InventoryMovementRecorder recorder;
    private final ApplicationEventPublisher events;
    private final Clock clock;
    public InventoryCommandServiceImpl(InventoryRepository repository, BatchContextFacade batches,
            InventoryMovementRecorder recorder, ApplicationEventPublisher events, Clock inventoryClock) {
        this.repository = repository;
        this.batches = batches;
        this.recorder = recorder;
        this.events = events;
        this.clock = inventoryClock;
    }

    @Override
    public Result<RawMaterial, ApplicationError> handle(SaveRawMaterialCommand command) {
        var lab = command.laboratoryId();
        var id = command.materialId();
        var code = command.code();
        var name = command.name();
        var unit = command.unit();
        var minimumStock = command.minimumStock();

        requireText(code, 50, "Code");
        requireText(name, 150, "Name");
        Long legacy = null;
        if (id != null) {
            var previous = material(lab, id, true);
            StockUnit.requireSame(previous.getUnit(), unit);
            legacy = repository.legacyId(lab, id).orElse(null);
        }
        var material = new RawMaterial(id, lab, code, name, unit, minimumStock);
        if (repository.codeExists(lab, material.getCode(), id)) throw conflict("Material code already exists");
        return Result.success(repository.saveMaterial(material, legacy));
    }

    @Override
    public Result<RawMaterialBatch, ApplicationError> handle(ReceiveRawMaterialBatchCommand command) {
        var lab = command.laboratoryId();
        var materialId = command.materialId();
        var supplier = command.supplier();
        var number = command.batchNumber();
        var unit = command.unit();
        var amount = command.amount();
        var received = command.receivedOn();
        var expires = command.expiresOn();

        var material = material(lab, materialId, true);
        requireText(supplier, 150, "Supplier");
        requireText(number, 50, "Batch number");
        StockUnit.requireSame(material.getUnit(), unit);
        if (received == null || received.isAfter(today())) throw new IllegalArgumentException("Receipt date cannot be in the future");
        if (repository.receiptExists(lab, materialId, supplier.trim(), number.trim())) throw conflict("Supplier receipt already exists");
        var receipt = repository.saveReceipt(RawMaterialBatch.receive(lab, materialId, supplier, number, unit, amount, received, expires));
        recorder.record(receipt, null, "RECEIPT", amount, BigDecimal.ZERO, null, "Receipt registered", null);
        return Result.success(receipt);
    }

    @Override
    public Result<RawMaterialBatch, ApplicationError> handle(ReviewRawMaterialBatchCommand command) {
        var lab = command.laboratoryId();
        var receiptId = command.receiptId();
        var target = command.status();
        var reason = command.reason();

        requireText(reason, 500, "Review reason");
        var receipt = receipt(lab, receiptId, true);
        var previous = receipt.getStatus().name();
        try {
            if (target == RawMaterialBatchStatus.RELEASED) {
                if (!today().isBefore(receipt.getExpiresOn())) throw conflict("Expired receipt cannot be released");
                receipt.release();
            } else if (target == RawMaterialBatchStatus.OBSERVED) receipt.observe();
            else if (target == RawMaterialBatchStatus.REJECTED) receipt.reject();
            else throw new IllegalArgumentException("Choose RELEASED, OBSERVED or REJECTED");
        } catch (IllegalStateException ex) { throw conflict(ex.getMessage()); }
        repository.saveReceipt(receipt);
        recorder.record(receipt, null, "REVIEW", BigDecimal.ZERO, receipt.getAvailableAmount(), previous, reason.trim(), null);
        return Result.success(receipt);
    }

    @Override
    public Result<ReceiptConsumption, ApplicationError> handle(ConsumeRawMaterialBatchCommand request) {

        requireText(request.operationId(), 100, "Operation ID");
        // Lock before checking idempotency so concurrent retries cannot consume the same receipt twice.
        var receipt = receipt(request.laboratoryId(), request.rawMaterialBatchId(), true);
        var previous = repository.operation(request.laboratoryId(), request.operationId());
        if (previous.isPresent()) {
            var movement = previous.get();
            if (!receipt.getId().equals(movement.receiptId()) || !request.productBatchId().equals(movement.productBatchId())
                || request.amountUsed().compareTo(movement.amount().negate()) != 0 || !request.unit().equals(movement.unit()))
                throw conflict("Operation ID was already used with different values");
            return Result.success(consumption(movement));
        }
        batches.requireConsumable(request.productBatchId(), request.laboratoryId());
        var before = receipt.getAvailableAmount();
        try { receipt.consume(request.amountUsed(), request.unit(), today()); }
        catch (IllegalStateException ex) { throw conflict(ex.getMessage()); }
        repository.saveReceipt(receipt);
        var movement = recorder.record(receipt, request.productBatchId(), "CONSUMPTION", request.amountUsed().negate(),
            before, receipt.getStatus().name(), "Product batch consumption", request.operationId());
        var material = material(request.laboratoryId(), receipt.getRawMaterialId(), false);
        events.publishEvent(new ReceiptConsumedIntegrationEvent(receipt.getId(), material.getId(), material.getName(),
            request.productBatchId(), request.amountUsed(), receipt.getUnit(), before, receipt.getAvailableAmount(), movement.occurredAt()));
        return Result.success(consumption(movement));
    }

    private ReceiptConsumption consumption(InventoryMovement movement) {
        return new ReceiptConsumption(movement.receiptId(), movement.productBatchId(), movement.amount().negate().setScale(3), movement.unit(),
            movement.stockBefore().setScale(3), movement.stockAfter().setScale(3), movement.operationId());
    }

    private RawMaterial material(Long lab, Long id, boolean lock) {
        return repository.material(lab, id, lock).orElseThrow(() -> new ApplicationException(ApplicationError.notFound("Material", id)));
    }

    private RawMaterialBatch receipt(Long lab, Long id, boolean lock) {
        return repository.receipt(lab, id, lock).orElseThrow(() -> new ApplicationException(ApplicationError.notFound("Receipt", id)));
    }

    private LocalDate today() { return LocalDate.now(clock); }

    private static void requireText(String text, int max, String field) {
        if (text == null || text.isBlank() || text.trim().length() > max)
            throw new IllegalArgumentException(field + " is required (maximum " + max + " characters)");
    }

    private static ApplicationException conflict(String message) {
        return new ApplicationException(ApplicationError.conflict("Inventory", message));
    }
}
