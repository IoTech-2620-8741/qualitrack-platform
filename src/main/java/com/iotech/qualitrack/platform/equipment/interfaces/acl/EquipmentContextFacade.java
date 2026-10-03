package com.iotech.qualitrack.platform.equipment.interfaces.acl;

import java.util.Optional;

/**
 * Boundary through which other bounded contexts read the equipment of a laboratory.
 */
public interface EquipmentContextFacade {

    /**
     * Finds an equipment registered in the laboratory.
     *
     * @param laboratoryId the laboratory that must own the equipment
     * @param equipmentId the equipment identifier
     * @return the equipment, or empty when it does not exist in the laboratory
     */
    Optional<EquipmentReference> findEquipment(Long laboratoryId, Long equipmentId);

    /**
     * Finds an IoT device (environmental device or container monitor) located in an environment.
     *
     * @param laboratoryId the laboratory that must own the device
     * @param environmentId the environment where the device must be located
     * @param deviceId the device (equipment) identifier
     * @return the device, or empty when it is not an IoT device located in the environment
     */
    Optional<DeviceReference> findDevice(Long laboratoryId, Long environmentId, Long deviceId);

    /**
     * Equipment data shared with other bounded contexts.
     *
     * @param id the equipment identifier
     * @param laboratoryId the owning laboratory
     * @param name the equipment name
     * @param serialNumber the serial number
     * @param status the operational status name (OPERATIONAL, MAINTENANCE, OUT_OF_SERVICE or INACTIVE)
     */
    record EquipmentReference(Long id, Long laboratoryId, String name, String serialNumber, String status) {
        /**
         * Only operational equipment can be used in a manufacturing process.
         *
         * @return true when the equipment is operational
         */
        public boolean isAvailable() {
            return "OPERATIONAL".equals(status);
        }
    }

    /**
     * IoT device data shared with other bounded contexts.
     *
     * @param id the device (equipment) identifier
     * @param laboratoryId the owning laboratory
     * @param environmentId the environment where the device is located
     * @param name the device name
     * @param deviceType ENVIRONMENTAL_DEVICE or CONTAINER_MONITOR
     * @param sensorExternalId the identifier with which Edge recognises the device
     */
    record DeviceReference(Long id, Long laboratoryId, Long environmentId, String name, String deviceType,
                           String sensorExternalId) {
    }
}
