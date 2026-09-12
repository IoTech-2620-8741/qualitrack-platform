package com.iotech.qualitrack.platform.equipment.application.internal.commandservices;

import com.iotech.qualitrack.platform.equipment.application.commandservices.MaintenanceCommandService;
import com.iotech.qualitrack.platform.equipment.domain.model.aggregates.MaintenanceRecord;
import com.iotech.qualitrack.platform.equipment.domain.model.commands.RegisterMaintenanceCommand;
import com.iotech.qualitrack.platform.equipment.domain.model.events.MaintenanceRegisteredEvent;
import com.iotech.qualitrack.platform.equipment.domain.repositories.EquipmentRepository;
import com.iotech.qualitrack.platform.equipment.domain.repositories.MaintenanceRepository;
import com.iotech.qualitrack.platform.shared.application.result.ApplicationError;
import com.iotech.qualitrack.platform.shared.application.result.Result;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * Application service implementation that executes maintenance commands.
 */
@Service
public class MaintenanceCommandServiceImpl implements MaintenanceCommandService {

    private final MaintenanceRepository maintenanceRepository;
    private final EquipmentRepository equipmentRepository;
    private final ApplicationEventPublisher eventPublisher;

    public MaintenanceCommandServiceImpl(MaintenanceRepository maintenanceRepository,
                                         EquipmentRepository equipmentRepository,
                                         ApplicationEventPublisher eventPublisher) {
        this.maintenanceRepository = maintenanceRepository;
        this.equipmentRepository = equipmentRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Result<Long, ApplicationError> handle(RegisterMaintenanceCommand command) {
        if (!equipmentRepository.existsById(command.equipmentId())) {
            return Result.failure(ApplicationError.notFound(
                    "Equipment",
                    String.valueOf(command.equipmentId())
            ));
        }

        try {
            var maintenanceRecord = new MaintenanceRecord(command);
            var savedRecord = maintenanceRepository.save(maintenanceRecord);

            eventPublisher.publishEvent(MaintenanceRegisteredEvent.from(savedRecord));

            return Result.success(savedRecord.getId());

        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("MaintenanceRecord", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("register-maintenance", e.getMessage()));
        }
    }
}