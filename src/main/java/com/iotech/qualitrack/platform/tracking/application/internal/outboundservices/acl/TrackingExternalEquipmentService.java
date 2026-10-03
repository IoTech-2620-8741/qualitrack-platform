package com.iotech.qualitrack.platform.tracking.application.internal.outboundservices.acl;

import com.iotech.qualitrack.platform.equipment.domain.model.entities.BpmParameterConfig;
import com.iotech.qualitrack.platform.equipment.domain.repositories.BpmParameterConfigRepository;
import com.iotech.qualitrack.platform.equipment.domain.repositories.EquipmentRepository;
import com.iotech.qualitrack.platform.equipment.interfaces.acl.EquipmentContextFacade;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Outbound ACL service used by Tracking to access Equipment bounded context data.
 *
 * @remarks
 * Tracking depends on Equipment to verify monitored equipment existence and
 * retrieve BPM threshold configuration for telemetry anomaly evaluation.
 */
@Service
public class TrackingExternalEquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final BpmParameterConfigRepository bpmParameterConfigRepository;
    private final EquipmentContextFacade equipmentContextFacade;

    /**
     * Creates a new TrackingExternalEquipmentService.
     *
     * @param equipmentRepository equipment repository from the Equipment bounded context
     * @param bpmParameterConfigRepository BPM parameter configuration repository
     * @param equipmentContextFacade Equipment facade that resolves the IoT devices of an environment
     */
    public TrackingExternalEquipmentService(
            EquipmentRepository equipmentRepository,
            BpmParameterConfigRepository bpmParameterConfigRepository,
            EquipmentContextFacade equipmentContextFacade
    ) {
        this.equipmentRepository = equipmentRepository;
        this.bpmParameterConfigRepository = bpmParameterConfigRepository;
        this.equipmentContextFacade = equipmentContextFacade;
    }

    /**
     * Verifies that the device is an environmental device or container monitor located in the environment.
     *
     * @param laboratoryId the laboratory that owns the device
     * @param environmentId the environment where the device must be located
     * @param deviceId the device (equipment) identifier
     * @return true when Equipment recognises it as an IoT device of the environment
     */
    public boolean isDeviceLocatedIn(Long laboratoryId, Long environmentId, Long deviceId) {
        return equipmentContextFacade.findDevice(laboratoryId, environmentId, deviceId).isPresent();
    }

    /**
     * Verifies whether an equipment exists.
     *
     * @param equipmentId the equipment identifier
     * @return true when the equipment exists
     */
    public boolean existsEquipmentById(Long equipmentId) {
        return equipmentId != null && equipmentRepository.existsById(equipmentId);
    }

    /**
     * Retrieves the BPM threshold configuration for a monitored parameter.
     *
     * @param equipmentId the equipment identifier
     * @param parameterName the telemetry parameter name
     * @return the BPM parameter configuration when found
     */
    public Optional<BpmParameterConfig> findBpmConfigByEquipmentAndParameter(
            Long equipmentId,
            String parameterName
    ) {
        if (equipmentId == null || parameterName == null || parameterName.isBlank()) {
            return Optional.empty();
        }

        return bpmParameterConfigRepository.findByEquipmentIdAndParameterName(
                equipmentId,
                parameterName
        );
    }

    /**
     * Determines whether a telemetry value is outside its configured BPM range.
     *
     * @param equipmentId the equipment identifier
     * @param parameterName the telemetry parameter name
     * @param value the recorded telemetry value
     * @return true when the value is outside the configured range
     */
    public boolean isTelemetryValueOutOfRange(
            Long equipmentId,
            String parameterName,
            Double value
    ) {
        if (value == null) return false;

        return findBpmConfigByEquipmentAndParameter(equipmentId, parameterName)
                .map(config -> value < config.getMinValue() || value > config.getMaxValue())
                .orElse(false);
    }

    /**
     * Resolves the exceeded threshold value for a telemetry reading.
     *
     * @param equipmentId the equipment identifier
     * @param parameterName the telemetry parameter name
     * @param value the recorded telemetry value
     * @return the minimum or maximum threshold exceeded when available
     */
    public Optional<Double> resolveExceededThreshold(
            Long equipmentId,
            String parameterName,
            Double value
    ) {
        if (value == null) return Optional.empty();

        return findBpmConfigByEquipmentAndParameter(equipmentId, parameterName)
                .flatMap(config -> {
                    if (value < config.getMinValue()) return Optional.of(config.getMinValue());
                    if (value > config.getMaxValue()) return Optional.of(config.getMaxValue());

                    return Optional.empty();
                });
    }
}