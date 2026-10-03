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
 * <p>Represents one manufacturing run of a pharmaceutical product in an environment of a
 * laboratory (US73). It governs the batch lifecycle from registration to final release or
 * rejection (US81, US82). Batches registered before environments existed keep a null
 * environment.</p>
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
     * The environment where the batch is manufactured, inherited from its product.
     */
    private Long environmentId;
    /**
     * The numeric identifier of the product being manufactured.
     */
    private Long productId;
    /**
     * The display name of the product being manufactured.
     */
    private String productName;
    /**
     * The traceability code assigned to this production batch, unique in the laboratory.
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
     * The date when the batch was released or rejected, if applicable.
     */
    private String endDate;
    /**
     * Manufacturing notes, the release notes or the rejection reason.
     */
    private String notes;

    /**
     * Default constructor required by the persistence assemblers.
     */
    public Batch() {
        // Required for reconstruction by assemblers
    }

    /**
     * Reconstructs a Batch from persistence data.
     */
    public Batch(Long id, Long labId, Long environmentId, Long productId, String productName, String batchNumber,
                 Double quantity, String unit, BatchStatus status, String startDate, String endDate, String notes) {
        this.id = id;
        this.labId = labId;
        this.environmentId = environmentId;
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
     * Registers a new pending batch of the given product.
     *
     * @param command the validated registration data
     * @param product the product being manufactured; it must belong to the command environment
     */
    public Batch(CreateBatchCommand command, PharmaceuticalProduct product) {
        Objects.requireNonNull(command, "Create batch command is required");
        Objects.requireNonNull(product, "Product is required");
        if (!product.belongsTo(command.laboratoryId(), command.environmentId()) || !product.getId().equals(command.productId())) {
            throw new IllegalArgumentException("The product is not registered in the environment");
        }
        this.labId = command.laboratoryId();
        this.environmentId = command.environmentId();
        this.productId = product.getId();
        this.productName = product.getName();
        this.batchNumber = command.batchNumber();
        this.quantity = command.quantity();
        this.unit = command.unit();
        this.status = BatchStatus.PENDING;
        this.startDate = command.startDate();
        this.notes = command.notes();
    }

    /**
     * Tells whether the batch is a manufacturing run of the product in the environment of the laboratory.
     *
     * @param laboratoryId the laboratory identifier
     * @param environmentId the environment identifier
     * @param productId the product identifier
     * @return true when all identifiers match
     */
    public boolean belongsTo(Long laboratoryId, Long environmentId, Long productId) {
        return Objects.equals(this.labId, laboratoryId) && Objects.equals(this.environmentId, environmentId)
                && Objects.equals(this.productId, productId);
    }

    /**
     * Tells whether the batch still accepts manufacturing records such as consumptions.
     *
     * @return true while the batch is pending or in progress
     */
    public boolean isOpen() {
        return status == BatchStatus.PENDING || status == BatchStatus.IN_PROGRESS;
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
     * @throws IllegalStateException when the batch is already released or rejected
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
        this.endDate = command.releaseDate();
        this.notes = command.notes();
    }

    /**
     * Rejects the batch due to quality control or compliance failures.
     *
     * @param command The command containing rejection information.
     * @throws IllegalStateException when the batch is already released or rejected
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
        this.endDate = command.rejectionDate();
        this.notes = command.reason();
    }
}
