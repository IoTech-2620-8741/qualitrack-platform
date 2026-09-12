package com.iotech.qualitrack.platform.ca.application.internal.outboundservices.acl;

import com.iotech.qualitrack.platform.equipment.domain.repositories.EquipmentRepository;
import org.springframework.stereotype.Service;

/**
 * ACL service used by the CA bounded context to access equipment information.
 *
 * <p>Prevents CA application services from depending directly on equipment
 * application services while still allowing cross-context validation.</p>
 */
@Service
public class CaExternalEquipmentService {

    private final EquipmentRepository equipmentRepository;

    public CaExternalEquipmentService(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    /**
     * Checks whether an equipment exists by its numeric identifier.
     *
     * @param equipmentId The equipment ID.
     * @return true if the equipment exists; otherwise false.
     */
    public boolean existsEquipmentById(Long equipmentId) {
        return equipmentId != null && equipmentRepository.existsById(equipmentId);
    }
}