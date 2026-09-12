package com.iotech.qualitrack.platform.laboratory.application.internal.commandservices;

import com.iotech.qualitrack.platform.laboratory.application.commandservices.ProductCommandService;
import com.iotech.qualitrack.platform.laboratory.domain.model.aggregates.PharmaceuticalProduct;
import com.iotech.qualitrack.platform.laboratory.domain.model.commands.CreateProductCommand;
import com.iotech.qualitrack.platform.laboratory.domain.model.events.ProductCreatedEvent;
import com.iotech.qualitrack.platform.laboratory.domain.repositories.LaboratoryRepository;
import com.iotech.qualitrack.platform.laboratory.domain.repositories.ProductRepository;
import com.iotech.qualitrack.platform.shared.application.result.ApplicationError;
import com.iotech.qualitrack.platform.shared.application.result.Result;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * Application service implementation that executes pharmaceutical product commands.
 *
 * <p>Handles the orchestration of creating products, enforcing business rules
 * such as laboratory existence, name uniqueness per lab, and global catalog code uniqueness.</p>
 */
@Service
public class ProductCommandServiceImpl implements ProductCommandService {

    private final ProductRepository productRepository;
    private final LaboratoryRepository laboratoryRepository;
    private final ApplicationEventPublisher eventPublisher;

    public ProductCommandServiceImpl(ProductRepository productRepository,
                                     LaboratoryRepository laboratoryRepository,
                                     ApplicationEventPublisher eventPublisher) {
        this.productRepository = productRepository;
        this.laboratoryRepository = laboratoryRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Result<Long, ApplicationError> handle(CreateProductCommand command) {
        if (!laboratoryRepository.existsById(command.laboratoryId())) {
            return Result.failure(ApplicationError.notFound(
                    "Laboratory",
                    String.valueOf(command.laboratoryId())
            ));
        }

        if (productRepository.existsByCode(command.code())) {
            return Result.failure(ApplicationError.conflict(
                    "PharmaceuticalProduct",
                    "Product with internal code '%s' already exists".formatted(command.code())
            ));
        }

        if (productRepository.findByNameAndLaboratoryId(command.name(), command.laboratoryId()).isPresent()) {
            return Result.failure(ApplicationError.conflict(
                    "PharmaceuticalProduct",
                    "Product with name '%s' already exists in this laboratory".formatted(command.name())
            ));
        }

        try {
            var product = new PharmaceuticalProduct(command);
            var savedProduct = productRepository.save(product);

            eventPublisher.publishEvent(ProductCreatedEvent.from(savedProduct));

            return Result.success(savedProduct.getId());

        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("PharmaceuticalProduct", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("create-product", e.getMessage()));
        }
    }
}