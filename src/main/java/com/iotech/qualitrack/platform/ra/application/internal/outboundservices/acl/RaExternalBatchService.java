package com.iotech.qualitrack.platform.ra.application.internal.outboundservices.acl;

import com.iotech.qualitrack.platform.batch.domain.model.valueobjects.BatchStatus;
import com.iotech.qualitrack.platform.batch.domain.repositories.BatchRepository;
import org.springframework.stereotype.Service;

/**
 * ACL service used by the RA bounded context to access batch information.
 *
 * <p>Provides a small anti-corruption boundary over Batch data needed by reports
 * and KPI calculations.</p>
 */
@Service
public class RaExternalBatchService {

    private final BatchRepository batchRepository;

    /**
     * Creates the service with the Batch repository dependency.
     *
     * @param batchRepository batch bounded-context repository port
     */
    public RaExternalBatchService(BatchRepository batchRepository) {
        this.batchRepository = batchRepository;
    }

    /**
     * Checks whether a batch exists by its numeric identifier.
     *
     * @param batchId the batch identifier
     * @return true if the batch exists, false otherwise
     */
    public boolean existsBatchById(Long batchId) {
        return batchId != null && batchRepository.existsById(batchId);
    }

    /**
     * Counts all batches registered for a laboratory.
     *
     * @param laboratoryId the laboratory identifier
     * @return number of batches for the laboratory
     */
    public long countBatchesByLaboratoryId(Long laboratoryId) {
        if (laboratoryId == null || laboratoryId <= 0) return 0;

        return batchRepository.findAllByLabId(laboratoryId).size();
    }

    /**
     * Counts released batches registered for a laboratory.
     *
     * @param laboratoryId the laboratory identifier
     * @return number of released batches for the laboratory
     */
    public long countReleasedBatchesByLaboratoryId(Long laboratoryId) {
        if (laboratoryId == null || laboratoryId <= 0) return 0;

        return batchRepository.findAllByLabId(laboratoryId).stream()
                .filter(batch -> batch.getStatus() == BatchStatus.RELEASED)
                .count();
    }

    /**
     * Counts rejected batches registered for a laboratory.
     *
     * @param laboratoryId the laboratory identifier
     * @return number of rejected batches for the laboratory
     */
    public long countRejectedBatchesByLaboratoryId(Long laboratoryId) {
        if (laboratoryId == null || laboratoryId <= 0) return 0;

        return batchRepository.findAllByLabId(laboratoryId).stream()
                .filter(batch -> batch.getStatus() == BatchStatus.REJECTED)
                .count();
    }
}