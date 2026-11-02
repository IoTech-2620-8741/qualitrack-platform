package com.iotech.qualitrack.platform.inventory.application.internal.outboundservices.acl;

import com.iotech.qualitrack.platform.inventory.domain.model.valueobjects.LegacyRawMaterial;
import com.iotech.qualitrack.platform.laboratory.interfaces.acl.LaboratoryContextFacade;
import com.iotech.qualitrack.platform.laboratory.interfaces.acl.LegacyInventoryFacade;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Outbound ACL used by Inventory to validate environment references owned by Laboratory Management and to read the raw
 * materials recorded there before Inventory existed.
 */
@Service
public class InventoryExternalLaboratoryService {

    private final LaboratoryContextFacade laboratoryContextFacade;
    private final LegacyInventoryFacade legacyInventoryFacade;

    public InventoryExternalLaboratoryService(LaboratoryContextFacade laboratoryContextFacade,
                                              LegacyInventoryFacade legacyInventoryFacade) {
        this.laboratoryContextFacade = laboratoryContextFacade;
        this.legacyInventoryFacade = legacyInventoryFacade;
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

    /**
     * Finds an environment of the laboratory with its usage.
     *
     * @return the environment, or empty when it does not belong to the laboratory
     */
    public java.util.Optional<LaboratoryContextFacade.EnvironmentReference> findEnvironment(Long laboratoryId, Long environmentId) {
        return laboratoryContextFacade.findEnvironment(laboratoryId, environmentId);
    }

    /**
     * Raw materials of the laboratory recorded before Inventory existed.
     */
    public List<LegacyRawMaterial> findLegacyMaterials(Long laboratoryId) {
        return legacyInventoryFacade.materials(laboratoryId).stream()
                .map(InventoryExternalLaboratoryService::toLegacyRawMaterial).toList();
    }

    /**
     * Locks a legacy raw material of the laboratory until the import transaction ends.
     *
     * @throws IllegalArgumentException when the material is not recorded in the laboratory
     */
    public LegacyRawMaterial lockLegacyMaterial(Long laboratoryId, Long legacyId) {
        return toLegacyRawMaterial(legacyInventoryFacade.lock(laboratoryId, legacyId));
    }

    private static LegacyRawMaterial toLegacyRawMaterial(LegacyInventoryFacade.Material material) {
        return new LegacyRawMaterial(material.id(), material.code(), material.name(), material.unit(),
                material.minimumStock(), material.supplier(), material.batchNumber(), material.expiresOn(),
                material.balance());
    }
}
