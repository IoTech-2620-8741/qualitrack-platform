package com.iotech.qualitrack.platform.batch.application.internal.outboundservices.acl;

import com.iotech.qualitrack.platform.ca.interfaces.acl.ComplianceContextFacade;
import org.springframework.stereotype.Service;

/**
 * ACL service used by the Batch bounded context to interact with Compliance and Alerts capabilities.
 *
 * <p>Acts as an anti-corruption layer to prevent CA domain concepts from leaking into
 * the Batch bounded context.</p>
 */
@Service
public class BatchExternalComplianceService {

    private final ComplianceContextFacade complianceContextFacade;

    /**
     * Creates the service with the Compliance ACL facade dependency.
     *
     * @param complianceContextFacade compliance bounded-context facade
     */
    public BatchExternalComplianceService(ComplianceContextFacade complianceContextFacade) {
        this.complianceContextFacade = complianceContextFacade;
    }

    /**
     * Verifies whether a batch can be released according to compliance rules.
     *
     * @param batchId the batch identifier
     * @return true when the batch can be released
     */
    public boolean canReleaseBatch(Long batchId) {
        return complianceContextFacade.canReleaseBatch(batchId);
    }
}