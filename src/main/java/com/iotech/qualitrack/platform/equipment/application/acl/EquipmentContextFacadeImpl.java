package com.iotech.qualitrack.platform.equipment.application.acl;

import com.iotech.qualitrack.platform.equipment.application.queryservices.EquipmentQueryService;
import com.iotech.qualitrack.platform.equipment.domain.model.queries.GetEquipmentByIdQuery;
import com.iotech.qualitrack.platform.equipment.interfaces.acl.EquipmentContextFacade;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

/**
 * Exposes the equipment of a laboratory to other bounded contexts through the Equipment query service.
 */
@Service
public class EquipmentContextFacadeImpl implements EquipmentContextFacade {

    private final EquipmentQueryService equipmentQueryService;

    public EquipmentContextFacadeImpl(EquipmentQueryService equipmentQueryService) {
        this.equipmentQueryService = equipmentQueryService;
    }

    @Override
    public Optional<EquipmentReference> findEquipment(Long laboratoryId, Long equipmentId) {
        if (laboratoryId == null || equipmentId == null || equipmentId <= 0) return Optional.empty();
        return equipmentQueryService.handle(new GetEquipmentByIdQuery(equipmentId))
                .filter(equipment -> Objects.equals(equipment.getLabId(), laboratoryId))
                .map(equipment -> new EquipmentReference(equipment.getId(), equipment.getLabId(), equipment.getName(),
                        equipment.getSerialNumber(), equipment.getStatus() == null ? null : equipment.getStatus().name()));
    }
}
