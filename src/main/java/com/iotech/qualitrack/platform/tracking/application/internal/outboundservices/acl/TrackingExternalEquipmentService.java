package com.iotech.qualitrack.platform.tracking.application.internal.outboundservices.acl;

import com.iotech.qualitrack.platform.equipment.interfaces.acl.EquipmentContextFacade;
import com.iotech.qualitrack.platform.tracking.domain.model.valueobjects.MonitoredDevice;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Anti-corruption layer to Equipment Management: identity, type and location of the IoT devices whose readings,
 * profiles and actions Tracking &amp; Telemetry records.
 */
@Service
public class TrackingExternalEquipmentService {
    private final EquipmentContextFacade equipmentContextFacade;

    public TrackingExternalEquipmentService(EquipmentContextFacade equipmentContextFacade) {
        this.equipmentContextFacade = equipmentContextFacade;
    }

    /**
     * Any IoT device located in the environment.
     */
    public Optional<MonitoredDevice> findDevice(Long laboratoryId, Long environmentId, Long deviceId) {
        return equipmentContextFacade.findDevice(laboratoryId, environmentId, deviceId)
                .map(TrackingExternalEquipmentService::toMonitoredDevice);
    }

    /**
     * The environmental device that supervises the environment.
     */
    public Optional<MonitoredDevice> findEnvironmentalDevice(Long laboratoryId, Long environmentId) {
        return equipmentContextFacade.findEnvironmentalDevice(laboratoryId, environmentId)
                .map(TrackingExternalEquipmentService::toMonitoredDevice);
    }

    /**
     * A container monitor located in the environment.
     */
    public Optional<MonitoredDevice> findContainerMonitor(Long laboratoryId, Long environmentId, Long deviceId) {
        return findDevice(laboratoryId, environmentId, deviceId).filter(MonitoredDevice::isContainerMonitor);
    }

    /**
     * Whether an Edge Gateway serves a device of its environment: a Gateway synchronizes only its share of the
     * devices, the same ones it reads from its device registry.
     */
    public boolean isServedByEdgeGateway(Long laboratoryId, Long environmentId, Long gatewayEquipmentId,
                                         Long deviceId) {
        return equipmentContextFacade.isServedByEdgeGateway(laboratoryId, environmentId, gatewayEquipmentId, deviceId);
    }

    private static MonitoredDevice toMonitoredDevice(EquipmentContextFacade.DeviceReference device) {
        return new MonitoredDevice(device.id(), device.deviceType());
    }
}
