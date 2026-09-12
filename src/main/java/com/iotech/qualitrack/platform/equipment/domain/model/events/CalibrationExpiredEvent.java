package com.iotech.qualitrack.platform.equipment.domain.model.events;

/**
 * Domain event published when an equipment's calibration validity period expires.
 *
 * <p>This event is critical for triggering automated alerts in the frontend (calibration-alert view)
 * and ensuring DIGEMID compliance by warning quality control managers to halt the use of the equipment.</p>
 *
 * @param equipmentId   The numeric identity of the equipment with expired calibration.
 * @param laboratoryId  The numeric identity of the laboratory owning the equipment.
 * @param equipmentName The name of the equipment (useful for display in alerts).
 * @param serialNumber  The serial number of the equipment for precise identification.
 */
public record CalibrationExpiredEvent(
        Long equipmentId,
        Long laboratoryId,
        String equipmentName,
        String serialNumber) {
}