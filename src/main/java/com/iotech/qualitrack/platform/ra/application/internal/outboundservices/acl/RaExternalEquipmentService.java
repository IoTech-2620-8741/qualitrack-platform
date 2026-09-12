package com.iotech.qualitrack.platform.ra.application.internal.outboundservices.acl;

import com.iotech.qualitrack.platform.equipment.domain.model.valueobjects.EquipmentStatus;
import com.iotech.qualitrack.platform.equipment.domain.repositories.EquipmentRepository;
import org.springframework.stereotype.Service;

/**
 * ACL service used by the RA bounded context to access equipment information.
 *
 * <p>Provides cross-context validation and aggregate counts required by reports
 * and KPI dashboard calculations.</p>
 */
@Service
public class RaExternalEquipmentService {

    private final EquipmentRepository equipmentRepository;

    /**
     * Creates the service with the Equipment repository dependency.
     *
     * @param equipmentRepository equipment bounded-context repository port
     */
    public RaExternalEquipmentService(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    /**
     * Checks whether an equipment exists by its numeric identifier.
     *
     * @param equipmentId the equipment identifier
     * @return true if the equipment exists, false otherwise
     */
    public boolean existsEquipmentById(Long equipmentId) {
        return equipmentId != null && equipmentRepository.existsById(equipmentId);
    }

    /**
     * Counts all equipment registered for a laboratory.
     *
     * @param laboratoryId the laboratory identifier
     * @return number of equipment records for the laboratory
     */
    public long countEquipmentByLaboratoryId(Long laboratoryId) {
        if (laboratoryId == null || laboratoryId <= 0) return 0;

        return equipmentRepository.findAllByLabId(laboratoryId).size();
    }

    /**
     * Counts equipment that requires calibration or maintenance attention.
     *
     * @param laboratoryId the laboratory identifier
     * @return number of equipment records requiring attention
     */
    public long countEquipmentRequiringAttentionByLaboratoryId(Long laboratoryId) {
        if (laboratoryId == null || laboratoryId <= 0) return 0;

        return equipmentRepository.findAllByLabId(laboratoryId).stream()
                .filter(equipment ->
                        equipment.getStatus() == EquipmentStatus.MAINTENANCE
                                || equipment.getStatus() == EquipmentStatus.OUT_OF_SERVICE
                                || equipment.getStatus() == EquipmentStatus.INACTIVE
                )
                .count();
    }
}