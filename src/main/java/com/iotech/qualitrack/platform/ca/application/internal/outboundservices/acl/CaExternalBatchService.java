package com.iotech.qualitrack.platform.ca.application.internal.outboundservices.acl;

import com.iotech.qualitrack.platform.batch.domain.repositories.BatchRepository;
import org.springframework.stereotype.Service;

/**
 * ACL service used by the CA bounded context to access batch information.
 *
 * <p>Prevents CA application services from depending directly on batch
 * application services while still allowing cross-context validation.</p>
 */
@Service
public class CaExternalBatchService {

    private final BatchRepository batchRepository;

    public CaExternalBatchService(BatchRepository batchRepository) {
        this.batchRepository = batchRepository;
    }

    /**
     * Checks whether a batch exists by its numeric identifier.
     *
     * @param batchId The batch ID.
     * @return true if the batch exists; otherwise false.
     */
    public boolean existsBatchById(Long batchId) {
        return batchId != null && batchRepository.existsById(batchId);
    }
}