package com.iotech.qualitrack.platform.batch.application.internal.outboundservices.acl;

import com.iotech.qualitrack.platform.laboratory.interfaces.acl.LaboratoryContextFacade;
import org.springframework.stereotype.Service;

/**
 * ACL service used by the Batch bounded context to interact with Laboratory capabilities.
 */
@Service
public class ExternalLaboratoryService {

    private final LaboratoryContextFacade laboratoryContextFacade;

    /**
     * Creates the service with the Laboratory ACL facade dependency.
     *
     * @param laboratoryContextFacade laboratory bounded-context facade
     */
    public ExternalLaboratoryService(LaboratoryContextFacade laboratoryContextFacade) {
        this.laboratoryContextFacade = laboratoryContextFacade;
    }

    /**
     * Verifies if a laboratory exists by its ID through the Laboratory bounded context.
     *
     * @param labId the numeric identity of the laboratory
     * @return true if the laboratory exists, false otherwise
     */
    public boolean existsLaboratoryById(Long labId) {
        return laboratoryContextFacade.existsLaboratoryById(labId);
    }
}