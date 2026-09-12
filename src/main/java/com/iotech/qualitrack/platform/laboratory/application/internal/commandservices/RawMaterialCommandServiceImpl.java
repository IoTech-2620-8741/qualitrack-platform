package com.iotech.qualitrack.platform.laboratory.application.internal.commandservices;

import com.iotech.qualitrack.platform.laboratory.application.commandservices.RawMaterialCommandService;
import com.iotech.qualitrack.platform.laboratory.domain.model.aggregates.RawMaterial;
import com.iotech.qualitrack.platform.laboratory.domain.model.commands.CreateRawMaterialCommand;
import com.iotech.qualitrack.platform.laboratory.domain.model.events.RawMaterialCreatedEvent;
import com.iotech.qualitrack.platform.laboratory.domain.model.events.RawMaterialLowStockEvent;
import com.iotech.qualitrack.platform.laboratory.domain.repositories.LaboratoryRepository;
import com.iotech.qualitrack.platform.laboratory.domain.repositories.RawMaterialRepository;
import com.iotech.qualitrack.platform.shared.application.result.ApplicationError;
import com.iotech.qualitrack.platform.shared.application.result.Result;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * Application service implementation that executes raw material commands.
 *
 * <p>Handles the orchestration of registering raw materials, enforcing business rules
 * such as laboratory existence and global catalog code uniqueness.</p>
 */
@Service
public class RawMaterialCommandServiceImpl implements RawMaterialCommandService {

    private final RawMaterialRepository rawMaterialRepository;
    private final LaboratoryRepository laboratoryRepository;
    private final ApplicationEventPublisher eventPublisher;

    public RawMaterialCommandServiceImpl(RawMaterialRepository rawMaterialRepository,
                                         LaboratoryRepository laboratoryRepository,
                                         ApplicationEventPublisher eventPublisher) {
        this.rawMaterialRepository = rawMaterialRepository;
        this.laboratoryRepository = laboratoryRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Result<Long, ApplicationError> handle(CreateRawMaterialCommand command) {
        if (!laboratoryRepository.existsById(command.laboratoryId())) {
            return Result.failure(ApplicationError.notFound(
                    "Laboratory",
                    String.valueOf(command.laboratoryId())
            ));
        }

        if (rawMaterialRepository.existsByCode(command.code())) {
            return Result.failure(ApplicationError.conflict(
                    "RawMaterial",
                    "Raw material with internal code '%s' already exists".formatted(command.code())
            ));
        }

        try {
            var rawMaterial = new RawMaterial(command);
            var savedMaterial = rawMaterialRepository.save(rawMaterial);

            eventPublisher.publishEvent(RawMaterialCreatedEvent.from(savedMaterial));

            if (isLowStock(savedMaterial)) {
                eventPublisher.publishEvent(new RawMaterialLowStockEvent(
                        savedMaterial.getId(),
                        savedMaterial.getLaboratoryId(),
                        savedMaterial.getName(),
                        savedMaterial.getCurrentStock(),
                        savedMaterial.getMinimumThreshold()
                ));
            }

            return Result.success(savedMaterial.getId());

        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("RawMaterial", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("create-raw-material", e.getMessage()));
        }
    }

    private boolean isLowStock(RawMaterial rawMaterial) {
        return rawMaterial.getCurrentStock().compareTo(rawMaterial.getMinimumThreshold()) <= 0;
    }
}
