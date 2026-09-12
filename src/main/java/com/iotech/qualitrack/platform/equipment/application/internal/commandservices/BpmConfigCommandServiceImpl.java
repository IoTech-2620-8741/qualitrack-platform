package com.iotech.qualitrack.platform.equipment.application.internal.commandservices;

import com.iotech.qualitrack.platform.equipment.application.commandservices.BpmConfigCommandService;
import com.iotech.qualitrack.platform.equipment.domain.model.commands.ConfigureBpmParametersCommand;
import com.iotech.qualitrack.platform.equipment.domain.model.entities.BpmParameterConfig;
import com.iotech.qualitrack.platform.equipment.domain.model.events.BpmParameterConfiguredEvent;
import com.iotech.qualitrack.platform.equipment.domain.repositories.BpmParameterConfigRepository;
import com.iotech.qualitrack.platform.equipment.domain.repositories.EquipmentRepository;
import com.iotech.qualitrack.platform.shared.application.result.ApplicationError;
import com.iotech.qualitrack.platform.shared.application.result.Result;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Application service implementation that executes BPM configuration commands.
 */
@Service
public class BpmConfigCommandServiceImpl implements BpmConfigCommandService {

    private final BpmParameterConfigRepository bpmConfigRepository;
    private final EquipmentRepository equipmentRepository;
    private final ApplicationEventPublisher eventPublisher;

    public BpmConfigCommandServiceImpl(BpmParameterConfigRepository bpmConfigRepository,
                                       EquipmentRepository equipmentRepository,
                                       ApplicationEventPublisher eventPublisher) {
        this.bpmConfigRepository = bpmConfigRepository;
        this.equipmentRepository = equipmentRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Result<Long, ApplicationError> handle(ConfigureBpmParametersCommand command) {
        if (!equipmentRepository.existsById(command.equipmentId())) {
            return Result.failure(ApplicationError.notFound(
                    "Equipment",
                    String.valueOf(command.equipmentId())
            ));
        }

        try {
            Optional<BpmParameterConfig> existingConfig = bpmConfigRepository
                    .findByEquipmentIdAndParameterName(command.equipmentId(), command.parameterName());

            BpmParameterConfig configToSave;

            if (existingConfig.isPresent()) {
                configToSave = existingConfig.get();
                configToSave.updateLimits(command.minValue(), command.maxValue());
            } else {
                configToSave = new BpmParameterConfig(command);
            }

            var savedConfig = bpmConfigRepository.save(configToSave);

            eventPublisher.publishEvent(BpmParameterConfiguredEvent.from(savedConfig));

            return Result.success(savedConfig.getId());

        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("BpmParameterConfig", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("configure-bpm", e.getMessage()));
        }
    }
}