package com.iotech.qualitrack.platform.batch.domain.model.aggregates;

import com.iotech.qualitrack.platform.batch.domain.model.commands.CreateBatchCommand;
import com.iotech.qualitrack.platform.batch.domain.model.commands.ReleaseBatchCommand;
import com.iotech.qualitrack.platform.batch.domain.model.commands.RejectBatchCommand;
import com.iotech.qualitrack.platform.batch.domain.model.valueobjects.BatchStatus;
import com.iotech.qualitrack.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;

import java.util.Objects;

/**
 * The Batch Aggregate Root.
 *
 * <p>Represents a production batch manufactured by a laboratory within the
 * QualiTrack platform. It governs the batch lifecycle from creation to final
 * release or rejection.</p>
 */
@Getter
public class Batch extends AbstractDomainAggregateRoot<Batch> {

    /**
     * The unique numeric identifier for the production batch.
     */
    private Long id;

    /**
     * The numeric identifier of the laboratory responsible for this batch.
     */
    private Long labId;

    /**
     * The numeric identifier of the product being manufactured.
     */
    private Long productId;

    /**
     * The display name of the product being manufactured.
     */
    private String productName;

    /**
     * The traceability code assigned to this production batch.
     */
    private String batchNumber;

    /**
     * The total amount intended or produced in this batch.
     */
    private Double quantity;

    /**
     * The unit of measurement for the batch quantity.
     */
    private String unit;

    /**
     * The current lifecycle status of the batch.
     */
    private BatchStatus status;

    /**
     * The date when the batch processing started.
     */
    private String startDate;

    /**
     * The date when the batch processing ended, if applicable.
     */
    private String endDate;

    /**
     * Optional manufacturing or quality control notes.
     */
    private String notes;

    /**
     * Default constructor.
     * Required by the persistence and mapping layers to reconstruct the entity.
     */
    public Batch() {
        // Required for reconstruction by JPA or Assemblers
    }

    /**
     * Reconstructs a Batch entity from persistence data.
     *
     * @param id The numeric batch ID.
     * @param labId The laboratory ID.
     * @param productId The product ID.
     * @param productName The product display name.
     * @param batchNumber The batch traceability code.
     * @param quantity The batch quantity.
     * @param unit The quantity unit.
     * @param status The current batch status.
     * @param startDate The start date.
     * @param endDate The end date, if applicable.
     * @param notes Optional manufacturing or quality control notes.
     */
    public Batch(Long id, Long labId, Long productId, String productName, String batchNumber,
                 Double quantity, String unit, BatchStatus status, String startDate,
                 String endDate, String notes) {
        this.id = id;
        this.labId = labId;
        this.productId = productId;
        this.productName = productName;
        this.batchNumber = batchNumber;
        this.quantity = quantity;
        this.unit = unit;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.notes = notes;
    }

    /**
     * Creates a new Batch based on the provided command.
     *
     * <p>The product name and unit are received from the application service,
     * usually after validating the product in the Laboratory context.</p>
     *
     * @param command The command containing the batch creation data.
     * @param productName The product display name.
     * @param unit The unit of measurement for the batch quantity.
     */
    public Batch(CreateBatchCommand command, String productName, String unit) {
        this.labId = Objects.requireNonNull(command.labId(), "Laboratory ID is required");
        this.productId = Objects.requireNonNull(command.productId(), "Product ID is required");
        this.productName = Objects.requireNonNull(productName, "Product name is required");
        this.batchNumber = Objects.requireNonNull(command.batchNumber(), "Batch number is required");
        this.quantity = Objects.requireNonNull(command.quantity(), "Quantity is required");
        this.unit = Objects.requireNonNull(unit, "Unit is required");
        this.status = BatchStatus.PENDING;
        this.startDate = Objects.requireNonNull(command.startDate(), "Start date is required");
        this.notes = command.notes();
    }

    /**
     * Marks the batch as in progress.
     */
    public void start() {
        if (this.status != BatchStatus.PENDING) {
            throw new IllegalStateException("Only pending batches can be started");
        }
        this.status = BatchStatus.IN_PROGRESS;
    }

    /**
     * Releases the batch after successful quality control validation.
     *
     * @param command The command containing release information.
     */
    public void release(ReleaseBatchCommand command) {
        Objects.requireNonNull(command, "Release command is required");

        if (this.status == BatchStatus.RELEASED) {
            throw new IllegalStateException("Batch is already released");
        }
        if (this.status == BatchStatus.REJECTED) {
            throw new IllegalStateException("Rejected batches cannot be released");
        }

        this.status = BatchStatus.RELEASED;
        this.endDate = Objects.requireNonNull(command.releaseDate(), "Release date is required");
        this.notes = command.notes();
    }

    /**
     * Rejects the batch due to quality control or compliance failures.
     *
     * @param command The command containing rejection information.
     */
    public void reject(RejectBatchCommand command) {
        Objects.requireNonNull(command, "Reject command is required");

        if (this.status == BatchStatus.RELEASED) {
            throw new IllegalStateException("Released batches cannot be rejected");
        }
        if (this.status == BatchStatus.REJECTED) {
            throw new IllegalStateException("Batch is already rejected");
        }

        this.status = BatchStatus.REJECTED;
        this.endDate = Objects.requireNonNull(command.rejectionDate(), "Rejection date is required");
        this.notes = Objects.requireNonNull(command.reason(), "Rejection reason is required");
    }
}