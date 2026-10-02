package com.iotech.qualitrack.platform.laboratory.interfaces.acl;

/**
 * Inbound Anti-Corruption Layer (ACL) facade for the Laboratory bounded context.
 * * <p>Exposes simplified capabilities to other bounded contexts (like Equipment or Tracking)
 * without exposing internal domain models or requiring them to know about internal queries/commands.</p>
 */
public interface LaboratoryContextFacade {

    /**
     * Verifies if a laboratory exists by its ID.
     *
     * @param labId the numeric identity of the laboratory
     * @return true if it exists, false otherwise
     */
    boolean existsLaboratoryById(Long labId);

    /**
     * Verifies if an environment exists and belongs to the given laboratory.
     *
     * @param laboratoryId the laboratory that must own the environment
     * @param environmentId the environment identifier
     * @return true when the environment exists in the laboratory
     */
    boolean existsEnvironment(Long laboratoryId, Long environmentId);
}