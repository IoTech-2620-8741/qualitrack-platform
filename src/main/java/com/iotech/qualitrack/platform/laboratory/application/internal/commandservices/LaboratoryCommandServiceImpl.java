package com.iotech.qualitrack.platform.laboratory.application.internal.commandservices;

import com.iotech.qualitrack.platform.laboratory.application.commandservices.LaboratoryCommandService;
import com.iotech.qualitrack.platform.laboratory.domain.model.aggregates.Laboratory;
import com.iotech.qualitrack.platform.laboratory.domain.model.commands.CreateLaboratoryCommand;
import com.iotech.qualitrack.platform.laboratory.domain.model.commands.UpdateLaboratoryCommand;
import com.iotech.qualitrack.platform.laboratory.domain.model.events.LaboratoryRegisteredEvent;
import com.iotech.qualitrack.platform.laboratory.domain.model.valueobjects.LaboratoryName;
import com.iotech.qualitrack.platform.laboratory.domain.repositories.LaboratoryRepository;
import com.iotech.qualitrack.platform.shared.application.result.ApplicationError;
import com.iotech.qualitrack.platform.shared.application.result.Result;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * Application service implementation that executes laboratory commands.
 *
 * <p>Handles the orchestration of creating and updating laboratories, enforcing
 * unique business rules (like Name and RUC uniqueness) before persisting state.</p>
 */
@Service
public class LaboratoryCommandServiceImpl implements LaboratoryCommandService {

    private final LaboratoryRepository laboratoryRepository;
    private final ApplicationEventPublisher eventPublisher;

    public LaboratoryCommandServiceImpl(LaboratoryRepository laboratoryRepository,
                                        ApplicationEventPublisher eventPublisher) {
        this.laboratoryRepository = laboratoryRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Result<Long, ApplicationError> handle(CreateLaboratoryCommand command) {
        try {
            var name = new LaboratoryName(command.name());

            if (laboratoryRepository.existsByName(name)) {
                return Result.failure(ApplicationError.conflict(
                        "Laboratory",
                        "Laboratory with name '%s' already exists".formatted(command.name())
                ));
            }

            if (laboratoryRepository.existsByRuc(command.ruc())) {
                return Result.failure(ApplicationError.conflict(
                        "Laboratory",
                        "Laboratory with RUC '%s' already exists".formatted(command.ruc())
                ));
            }

            var laboratory = new Laboratory(command);
            var savedLaboratory = laboratoryRepository.save(laboratory);

            eventPublisher.publishEvent(LaboratoryRegisteredEvent.from(savedLaboratory));

            return Result.success(savedLaboratory.getId());

        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("Laboratory", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("create-laboratory", e.getMessage()));
        }
    }

    @Override
    public Result<Laboratory, ApplicationError> handle(UpdateLaboratoryCommand command) {
        var result = laboratoryRepository.findById(command.laboratoryId());

        if (result.isEmpty()) {
            return Result.failure(ApplicationError.notFound(
                    "Laboratory",
                    String.valueOf(command.laboratoryId())
            ));
        }

        var laboratoryToUpdate = result.get();

        try {
            laboratoryToUpdate.updateProfile(command);
            var updatedLaboratory = laboratoryRepository.save(laboratoryToUpdate);
            return Result.success(updatedLaboratory);
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("Laboratory", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("update-laboratory", e.getMessage()));
        }
    }
}