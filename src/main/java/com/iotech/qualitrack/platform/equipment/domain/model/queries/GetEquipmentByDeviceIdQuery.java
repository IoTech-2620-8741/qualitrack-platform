package com.iotech.qualitrack.platform.equipment.domain.model.queries;

/**
 * Query to get equipment by its linked external IoT sensor device ID.
 *
 * @param deviceId The external sensor ID. Cannot be null or blank.
 */
public record GetEquipmentByDeviceIdQuery(String deviceId) {
    public GetEquipmentByDeviceIdQuery {
        if (deviceId == null || deviceId.isBlank()) {
            throw new IllegalArgumentException("Device id is required and cannot be blank.");
        }
    }
}