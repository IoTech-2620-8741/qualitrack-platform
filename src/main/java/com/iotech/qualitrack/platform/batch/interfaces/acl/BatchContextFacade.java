package com.iotech.qualitrack.platform.batch.interfaces.acl;

/**
 * Inbound Anti-Corruption Layer (ACL) facade for the Batch bounded context.
 *
 * <p>Exposes simplified capabilities to other bounded contexts, such as
 * Compliance & Alerts (CA), Reporting & Audit (RA), or Tracking, without exposing
 * internal domain models or requiring them to know about internal queries/commands.</p>
 */
public interface BatchContextFacade {

    /**
     * Verifies if a batch exists by its ID.
     *
     * @param batchId the numeric identity of the batch
     * @return true if it exists, false otherwise
     */
    boolean existsBatchById(Long batchId);

    /**
     * Verifies if a batch belongs to a specific laboratory.
     *
     * @param batchId the numeric identity of the batch
     * @param laboratoryId the numeric identity of the laboratory
     * @return true if the batch belongs to the laboratory, false otherwise
     */
    boolean belongsToLaboratory(Long batchId, Long laboratoryId);

    /**
     * Verifies if a batch has been released.
     *
     * @param batchId the numeric identity of the batch
     * @return true if the batch status is RELEASED, false otherwise
     */
    boolean isBatchReleased(Long batchId);

    /**
     * Verifies if a batch has been rejected.
     *
     * @param batchId the numeric identity of the batch
     * @return true if the batch status is REJECTED, false otherwise
     */
    boolean isBatchRejected(Long batchId);
}