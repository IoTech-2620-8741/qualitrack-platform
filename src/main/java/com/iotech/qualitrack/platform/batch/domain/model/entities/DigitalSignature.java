package com.iotech.qualitrack.platform.batch.domain.model.entities;

import lombok.Getter;

/**
 * The DigitalSignature domain entity.
 *
 * <p>Represents the digital approval evidence generated when a production batch
 * is released. It supports traceability and regulatory compliance.</p>
 */
@Getter
public class DigitalSignature {

    /**
     * The unique internal numeric identifier for the signature.
     */
    private Long id;

    /**
     * The numeric identifier of the released batch.
     */
    private Long batchId;

    /**
     * The numeric identifier of the user who signed the release.
     */
    private Long signedByUserId;

    /**
     * The generated signature hash.
     */
    private String signatureHash;

    /**
     * The date and time when the release was signed.
     */
    private String signedAt;

    /**
     * Default constructor.
     * Required by the persistence and mapping layers to reconstruct the entity.
     */
    public DigitalSignature() {
        // Required for reconstruction by JPA or Assemblers
    }

    /**
     * Reconstructs a DigitalSignature entity from persistence data.
     *
     * @param id The unique numeric ID.
     * @param batchId The batch ID.
     * @param signedByUserId The signing user ID.
     * @param signatureHash The generated signature hash.
     * @param signedAt The signing date.
     */
    public DigitalSignature(Long id, Long batchId, Long signedByUserId, String signatureHash, String signedAt) {
        this.id = id;
        this.batchId = batchId;
        this.signedByUserId = signedByUserId;
        this.signatureHash = signatureHash;
        this.signedAt = signedAt;
    }

    /**
     * Constructs a new DigitalSignature.
     *
     * @param batchId The batch ID. Cannot be null or less than 1.
     * @param signedByUserId The signing user ID. Cannot be null or less than 1.
     * @param signatureHash The generated signature hash. Cannot be null or blank.
     * @param signedAt The signing date. Cannot be null or blank.
     */
    public DigitalSignature(Long batchId, Long signedByUserId, String signatureHash, String signedAt) {
        if (batchId == null || batchId <= 0) {
            throw new IllegalArgumentException("batchId cannot be null or less than 1");
        }
        if (signedByUserId == null || signedByUserId <= 0) {
            throw new IllegalArgumentException("signedByUserId cannot be null or less than 1");
        }
        if (signatureHash == null || signatureHash.isBlank()) {
            throw new IllegalArgumentException("signatureHash cannot be null or blank");
        }
        if (signedAt == null || signedAt.isBlank()) {
            throw new IllegalArgumentException("signedAt cannot be null or blank");
        }

        this.batchId = batchId;
        this.signedByUserId = signedByUserId;
        this.signatureHash = signatureHash;
        this.signedAt = signedAt;
    }
}