package com.iotech.qualitrack.platform.batch.domain.model.entities;

import com.iotech.qualitrack.platform.batch.domain.model.commands.RejectBatchCommand;
import lombok.Getter;

import java.util.Objects;

/**
 * The RejectionRecord domain entity.
 *
 * <p>Represents the rejection evidence generated when a production batch fails
 * quality control or compliance validation.</p>
 */
@Getter
public class RejectionRecord {

    /**
     * The unique internal numeric identifier for the rejection record.
     */
    private Long id;

    /**
     * The numeric identifier of the rejected batch.
     */
    private Long batchId;

    /**
     * The date when the batch was rejected.
     */
    private String rejectionDate;

    /**
     * The justification for rejecting the batch.
     */
    private String reason;

    /**
     * Default constructor.
     * Required by the persistence and mapping layers to reconstruct the entity.
     */
    public RejectionRecord() {
        // Required for reconstruction by JPA or Assemblers
    }

    /**
     * Reconstructs a RejectionRecord entity from persistence data.
     *
     * @param id The unique numeric ID.
     * @param batchId The batch ID.
     * @param rejectionDate The rejection date.
     * @param reason The rejection reason.
     */
    public RejectionRecord(Long id, Long batchId, String rejectionDate, String reason) {
        this.id = id;
        this.batchId = batchId;
        this.rejectionDate = rejectionDate;
        this.reason = reason;
    }

    /**
     * Constructs a new RejectionRecord from a rejection command.
     *
     * @param command The command containing rejection data.
     */
    public RejectionRecord(RejectBatchCommand command) {
        this.batchId = Objects.requireNonNull(command.batchId(), "Batch ID is required");
        this.rejectionDate = Objects.requireNonNull(command.rejectionDate(), "Rejection date is required");
        this.reason = Objects.requireNonNull(command.reason(), "Rejection reason is required");
    }
}