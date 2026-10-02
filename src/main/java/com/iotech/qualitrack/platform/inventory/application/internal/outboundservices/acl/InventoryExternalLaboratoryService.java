package com.iotech.qualitrack.platform.inventory.application.internal.outboundservices.acl;

import com.iotech.qualitrack.platform.laboratory.interfaces.acl.LaboratoryContextFacade;
import org.springframework.stereotype.Service;

/**
 * Outbound ACL used by Inventory to validate environment references owned by Laboratory Management.
 */
@Service
public class InventoryExternalLaboratoryService {

    private final LaboratoryContextFacade laboratoryContextFacade;

    public InventoryExternalLaboratoryService(LaboratoryContextFacade laboratoryContextFacade) {
        this.laboratoryContextFacade = laboratoryContextFacade;
    }

    /**
     * Checks that the environment exists and belongs to the laboratory.
     *
     * @param laboratoryId the laboratory that must own the environment
     * @param environmentId the environment identifier
     * @return true when the environment belongs to the laboratory
     */
    public boolean existsEnvironment(Long laboratoryId, Long environmentId) {
        return laboratoryContextFacade.existsEnvironment(laboratoryId, environmentId);
    }
}
