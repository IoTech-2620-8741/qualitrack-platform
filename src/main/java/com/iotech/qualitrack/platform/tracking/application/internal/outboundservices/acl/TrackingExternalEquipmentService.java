package com.iotech.qualitrack.platform.tracking.application.internal.outboundservices.acl;

import com.iotech.qualitrack.platform.equipment.interfaces.acl.EquipmentContextFacade;
import com.iotech.qualitrack.platform.tracking.domain.model.valueobjects.MonitoredDevice;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Anti-corruption layer to Equipment Management: identity, type and location of the IoT devices whose readings,
 * profiles and actions Tracking &amp; Telemetry records.
 *
 * <p>It translates the device references of {@link EquipmentContextFacade} into {@link MonitoredDevice}, so the rest
 * of Tracking only knows its own model. Every lookup is scoped to a laboratory and an environment: a device of
 * another tenant or located elsewhere is not found.</p>
 */
@Service
public class TrackingExternalEquipmentService {
    private final EquipmentContextFacade equipmentContextFacade;

    public TrackingExternalEquipmentService(EquipmentContextFacade equipmentContextFacade) {
        this.equipmentContextFacade = equipmentContextFacade;
    }

    /**
     * Any IoT device located in the environment.
     *
     * @return the device; empty when it does not exist, is not an IoT device or is not located in the environment
     */
    public Optional<MonitoredDevice> findDevice(Long laboratoryId, Long environmentId, Long deviceId) {
        return equipmentContextFacade.findDevice(laboratoryId, environmentId, deviceId)
                .map(TrackingExternalEquipmentService::toMonitoredDevice);
    }

    /**
     * The environmental device that supervises the environment.
     *
     * @return the device; empty when the environment has none
     */
    public Optional<MonitoredDevice> findEnvironmentalDevice(Long laboratoryId, Long environmentId) {
        return equipmentContextFacade.findEnvironmentalDevice(laboratoryId, environmentId)
                .map(TrackingExternalEquipmentService::toMonitoredDevice);
    }

    /**
     * A container monitor located in the environment.
     *
     * @return the device; empty when it is not a container monitor located in the environment
     */
    public Optional<MonitoredDevice> findContainerMonitor(Long laboratoryId, Long environmentId, Long deviceId) {
        return findDevice(laboratoryId, environmentId, deviceId).filter(MonitoredDevice::isContainerMonitor);
    }

    private static MonitoredDevice toMonitoredDevice(EquipmentContextFacade.DeviceReference device) {
        return new MonitoredDevice(device.id(), device.deviceType());
    }
}
