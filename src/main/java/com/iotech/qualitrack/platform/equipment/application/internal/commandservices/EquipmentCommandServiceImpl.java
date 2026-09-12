package com.iotech.qualitrack.platform.equipment.application.internal.commandservices;

import com.iotech.qualitrack.platform.equipment.application.commandservices.EquipmentCommandService;
import com.iotech.qualitrack.platform.equipment.application.internal.outboundservices.acl.ExternalLabService;
import com.iotech.qualitrack.platform.equipment.domain.model.aggregates.Equipment;
import com.iotech.qualitrack.platform.equipment.domain.model.commands.LinkSensorCommand;
import com.iotech.qualitrack.platform.equipment.domain.model.commands.RegisterEquipmentCommand;
import com.iotech.qualitrack.platform.equipment.domain.model.events.EquipmentRegisteredEvent;
import com.iotech.qualitrack.platform.equipment.domain.model.events.SensorLinkedEvent;
import com.iotech.qualitrack.platform.equipment.domain.repositories.EquipmentRepository;
import com.iotech.qualitrack.platform.shared.application.result.ApplicationError;
import com.iotech.qualitrack.platform.shared.application.result.Result;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * Application service implementation that executes equipment commands.
 *
 * <p>Handles the orchestration of registering equipment and linking IoT sensors,
 * enforcing business rules such as cross-context laboratory validation and
 * unique serial numbers.</p>
 */
@Service
public class EquipmentCommandServiceImpl implements EquipmentCommandService {

    private final EquipmentRepository equipmentRepository;
    private final ExternalLabService externalLabService;
    private final ApplicationEventPublisher eventPublisher;

    public EquipmentCommandServiceImpl(EquipmentRepository equipmentRepository,
                                       ExternalLabService externalLabService,
                                       ApplicationEventPublisher eventPublisher) {
        this.equipmentRepository = equipmentRepository;
        this.externalLabService = externalLabService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Result<Long, ApplicationError> handle(RegisterEquipmentCommand command) {
        if (!externalLabService.existsLaboratoryById(command.labId())) {
            return Result.failure(ApplicationError.notFound(
                    "Laboratory",
                    String.valueOf(command.labId())
            ));
        }

        if (equipmentRepository.existsBySerialNumber(command.serialNumber())) {
            return Result.failure(ApplicationError.conflict(
                    "Equipment",
                    "Equipment with serial number '%s' already exists".formatted(command.serialNumber())
            ));
        }

        try {
            var equipment = new Equipment(command);
            var savedEquipment = equipmentRepository.save(equipment);

            eventPublisher.publishEvent(EquipmentRegisteredEvent.from(savedEquipment));

            return Result.success(savedEquipment.getId());

        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("Equipment", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("register-equipment", e.getMessage()));
        }
    }

    @Override
    public Result<Long, ApplicationError> handle(LinkSensorCommand command) {
        var result = equipmentRepository.findById(command.equipmentId());

        if (result.isEmpty()) {
            return Result.failure(ApplicationError.notFound(
                    "Equipment",
                    String.valueOf(command.equipmentId())
            ));
        }

        var equipment = result.get();

        try {
            equipment.linkSensor(command.sensorExternalId());

            var updatedEquipment = equipmentRepository.save(equipment);

            eventPublisher.publishEvent(new SensorLinkedEvent(
                    updatedEquipment.getId(),
                    command.sensorExternalId()
            ));

            return Result.success(updatedEquipment.getId());

        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("Equipment", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("link-sensor", e.getMessage()));
        }
    }
}