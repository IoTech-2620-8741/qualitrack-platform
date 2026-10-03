package com.iotech.qualitrack.platform.ra.application.internal.outboundservices.acl;

import com.iotech.qualitrack.platform.laboratory.interfaces.acl.LaboratoryContextFacade;
import org.springframework.stereotype.Service;

/**
 * ACL service used by the RA bounded context to interact with Laboratory capabilities.
 *
 * <p>Prevents Reporting and Analysis application services from depending on
 * Laboratory internals while still allowing cross-context validation.</p>
 */
@Service
public class RaExternalLaboratoryService {

    private final LaboratoryContextFacade laboratoryContextFacade;

    /**
     * Creates the service with the Laboratory ACL facade dependency.
     *
     * @param laboratoryContextFacade laboratory bounded-context facade
     */
    public RaExternalLaboratoryService(LaboratoryContextFacade laboratoryContextFacade) {
        this.laboratoryContextFacade = laboratoryContextFacade;
    }

    /**
     * Verifies whether a laboratory exists by its ID.
     *
     * @param laboratoryId the numeric identity of the laboratory
     * @return true if the laboratory exists, false otherwise
     */
    public boolean existsLaboratoryById(Long laboratoryId) {
        return laboratoryId != null && laboratoryContextFacade.existsLaboratoryById(laboratoryId);
    }

    /**
     * Resolves the account of a staff member of the laboratory.
     *
     * @return the user id of the staff member (empty optional inside when it has no account), or empty when the
     * staff member is not registered in the laboratory
     */
    public java.util.Optional<java.util.Optional<Long>> findStaffAccount(Long laboratoryId, Long staffId) {
        return laboratoryContextFacade.findStaffMember(laboratoryId, staffId)
                .map(staff -> java.util.Optional.ofNullable(staff.userId()));
    }
}