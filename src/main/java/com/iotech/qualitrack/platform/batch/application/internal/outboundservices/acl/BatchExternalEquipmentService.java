package com.iotech.qualitrack.platform.batch.application.internal.outboundservices.acl;

import com.iotech.qualitrack.platform.equipment.interfaces.acl.EquipmentContextFacade;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Outbound ACL used by Product Batch Management to read the equipment owned by Equipment Management.
 */
@Service
public class BatchExternalEquipmentService {

    private final EquipmentContextFacade equipmentContextFacade;

    public BatchExternalEquipmentService(EquipmentContextFacade equipmentContextFacade) {
        this.equipmentContextFacade = equipmentContextFacade;
    }

    /**
     * Finds an equipment of the laboratory.
     *
     * @return the equipment, or empty when it is not registered in the laboratory
     */
    public Optional<EquipmentContextFacade.EquipmentReference> findEquipment(Long laboratoryId, Long equipmentId) {
        return equipmentContextFacade.findEquipment(laboratoryId, equipmentId);
    }
}
