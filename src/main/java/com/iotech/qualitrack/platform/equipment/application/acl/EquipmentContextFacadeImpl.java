package com.iotech.qualitrack.platform.equipment.application.acl;

import com.iotech.qualitrack.platform.equipment.application.queryservices.BpmConfigQueryService;
import com.iotech.qualitrack.platform.equipment.application.queryservices.EquipmentQueryService;
import com.iotech.qualitrack.platform.equipment.application.queryservices.MaintenanceQueryService;
import com.iotech.qualitrack.platform.equipment.domain.model.aggregates.Equipment;
import com.iotech.qualitrack.platform.equipment.domain.model.queries.GetBpmParameterConfigsByEquipmentIdQuery;
import com.iotech.qualitrack.platform.equipment.domain.model.queries.GetEquipmentByIdQuery;
import com.iotech.qualitrack.platform.equipment.domain.model.queries.GetEquipmentByLabIdQuery;
import com.iotech.qualitrack.platform.equipment.domain.model.queries.GetMaintenanceByEquipmentIdQuery;
import com.iotech.qualitrack.platform.equipment.domain.model.valueobjects.IotDeviceType;
import com.iotech.qualitrack.platform.equipment.interfaces.acl.EquipmentContextFacade;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Exposes the equipment of a laboratory to other bounded contexts through the Equipment query services.
 */
@Service
public class EquipmentContextFacadeImpl implements EquipmentContextFacade {

    private final EquipmentQueryService equipmentQueryService;
    private final BpmConfigQueryService bpmConfigQueryService;
    private final MaintenanceQueryService maintenanceQueryService;

    public EquipmentContextFacadeImpl(EquipmentQueryService equipmentQueryService,
                                      BpmConfigQueryService bpmConfigQueryService,
                                      MaintenanceQueryService maintenanceQueryService) {
        this.equipmentQueryService = equipmentQueryService;
        this.bpmConfigQueryService = bpmConfigQueryService;
        this.maintenanceQueryService = maintenanceQueryService;
    }

    @Override
    public Optional<EquipmentReference> findEquipment(Long laboratoryId, Long equipmentId) {
        if (laboratoryId == null || equipmentId == null || equipmentId <= 0) return Optional.empty();
        if (laboratoryId <= 0) return Optional.empty();
        return equipmentQueryService.handle(new GetEquipmentByIdQuery(laboratoryId, equipmentId))
                .map(EquipmentContextFacadeImpl::toReference);
    }

    @Override
    public List<EquipmentReference> findLaboratoryEquipment(Long laboratoryId) {
        if (laboratoryId == null || laboratoryId <= 0) return List.of();
        return equipmentQueryService.handle(new GetEquipmentByLabIdQuery(laboratoryId)).stream()
                .map(EquipmentContextFacadeImpl::toReference).toList();
    }

    @Override
    public Optional<EquipmentLogReference> findEquipmentLog(Long laboratoryId, Long environmentId, Long equipmentId) {
        if (laboratoryId == null || laboratoryId <= 0 || environmentId == null || environmentId <= 0
                || equipmentId == null || equipmentId <= 0) {
            return Optional.empty();
        }
        return equipmentQueryService.handle(new GetEquipmentByIdQuery(laboratoryId, equipmentId))
                .filter(equipment -> equipment.isLocatedIn(laboratoryId, environmentId))
                .map(equipment -> new EquipmentLogReference(equipment.getId(), equipment.getLabId(),
                        equipment.getEnvironmentId(), equipment.getName(),
                        equipment.getType() == null ? null : equipment.getType().name(), equipment.getModel(),
                        equipment.getSerialNumber(), equipment.getStatus() == null ? null : equipment.getStatus().name(),
                        equipment.getSensorExternalId() == null ? null : equipment.getSensorExternalId().value(),
                        bpmConfigQueryService.handle(new GetBpmParameterConfigsByEquipmentIdQuery(equipment.getId()))
                                .stream().map(config -> new ParameterRange(config.getParameterName().name(),
                                        config.getMinValue(), config.getMaxValue(), config.getUnit())).toList(),
                        maintenanceQueryService.handle(new GetMaintenanceByEquipmentIdQuery(laboratoryId, environmentId,
                                        equipment.getId())).orElse(List.of())
                                .stream().map(record -> new MaintenanceEntry(record.getId(), record.getMaintenanceDate(),
                                        record.getType() == null ? null : record.getType().name(),
                                        record.getTechnicianName(), record.getDescription())).toList()));
    }

    private static EquipmentReference toReference(Equipment equipment) {
        return new EquipmentReference(equipment.getId(), equipment.getLabId(), equipment.getName(),
                equipment.getSerialNumber(), equipment.getStatus() == null ? null : equipment.getStatus().name());
    }

    @Override
    public Optional<DeviceReference> findDevice(Long laboratoryId, Long environmentId, Long deviceId) {
        if (laboratoryId == null || laboratoryId <= 0 || environmentId == null || deviceId == null || deviceId <= 0) {
            return Optional.empty();
        }
        return equipmentQueryService.handle(new GetEquipmentByIdQuery(laboratoryId, deviceId))
                .filter(equipment -> equipment.isIotDevice() && equipment.isLocatedIn(laboratoryId, environmentId))
                .map(device -> new DeviceReference(device.getId(), device.getLabId(), device.getEnvironmentId(),
                        device.getName(), device.getDeviceType().name(),
                        device.getSensorExternalId() == null ? null : device.getSensorExternalId().value()));
    }

    @Override
    public Optional<DeviceReference> findEnvironmentalDevice(Long laboratoryId, Long environmentId) {
        if (laboratoryId == null || laboratoryId <= 0 || environmentId == null || environmentId <= 0) {
            return Optional.empty();
        }
        return equipmentQueryService.handle(new GetEquipmentByLabIdQuery(laboratoryId)).stream()
                .filter(equipment -> equipment.getDeviceType() == IotDeviceType.ENVIRONMENTAL_DEVICE
                        && equipment.isLocatedIn(laboratoryId, environmentId))
                .findFirst()
                .map(device -> new DeviceReference(device.getId(), device.getLabId(), device.getEnvironmentId(),
                        device.getName(), device.getDeviceType().name(),
                        device.getSensorExternalId() == null ? null : device.getSensorExternalId().value()));
    }

    @Override
    public List<DeviceReference> findDevices(Long laboratoryId) {
        if (laboratoryId == null || laboratoryId <= 0) return List.of();
        return equipmentQueryService.handle(new GetEquipmentByLabIdQuery(laboratoryId)).stream()
                .filter(equipment -> equipment.isIotDevice() && laboratoryId.equals(equipment.getLabId()))
                .map(device -> new DeviceReference(device.getId(), device.getLabId(), device.getEnvironmentId(),
                        device.getName(), device.getDeviceType().name(),
                        device.getSensorExternalId() == null ? null : device.getSensorExternalId().value()))
                .toList();
    }

    @Override
    public Optional<ContainerReference> findContainerMonitor(Long laboratoryId, Long deviceId) {
        if (laboratoryId == null || laboratoryId <= 0 || deviceId == null || deviceId <= 0) return Optional.empty();
        return equipmentQueryService.handle(new GetEquipmentByIdQuery(laboratoryId, deviceId))
                .filter(equipment -> equipment.getDeviceType() == IotDeviceType.CONTAINER_MONITOR)
                .map(container -> new ContainerReference(container.getId(), container.getLabId(), container.getEnvironmentId(),
                        container.getName(), container.getStatus() == null ? null : container.getStatus().name()));
    }
}
