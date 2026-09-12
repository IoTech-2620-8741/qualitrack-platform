package com.iotech.qualitrack.platform.laboratory.application.internal.commandservices;

import com.iotech.qualitrack.platform.laboratory.application.commandservices.StaffCommandService;
import com.iotech.qualitrack.platform.laboratory.domain.model.aggregates.StaffMember;
import com.iotech.qualitrack.platform.laboratory.domain.model.commands.DeactivateStaffCommand;
import com.iotech.qualitrack.platform.laboratory.domain.model.commands.RegisterStaffCommand;
import com.iotech.qualitrack.platform.laboratory.domain.model.events.StaffDeactivatedEvent;
import com.iotech.qualitrack.platform.laboratory.domain.model.events.StaffRegisteredEvent;
import com.iotech.qualitrack.platform.laboratory.domain.repositories.LaboratoryRepository;
import com.iotech.qualitrack.platform.laboratory.domain.repositories.StaffRepository;
import com.iotech.qualitrack.platform.shared.application.result.ApplicationError;
import com.iotech.qualitrack.platform.shared.application.result.Result;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * Application service implementation that executes staff commands.
 *
 * <p>Handles the orchestration of registering and deactivating staff members,
 * enforcing business rules such as laboratory existence, email uniqueness,
 * and valid state transitions.</p>
 */
@Service
public class StaffCommandServiceImpl implements StaffCommandService {

    private final StaffRepository staffRepository;
    private final LaboratoryRepository laboratoryRepository;
    private final ApplicationEventPublisher eventPublisher;

    public StaffCommandServiceImpl(StaffRepository staffRepository,
                                   LaboratoryRepository laboratoryRepository,
                                   ApplicationEventPublisher eventPublisher) {
        this.staffRepository = staffRepository;
        this.laboratoryRepository = laboratoryRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Result<Long, ApplicationError> handle(RegisterStaffCommand command) {
        if (!laboratoryRepository.existsById(command.laboratoryId())) {
            return Result.failure(ApplicationError.notFound(
                    "Laboratory",
                    String.valueOf(command.laboratoryId())
            ));
        }

        if (staffRepository.existsByEmail(command.email())) {
            return Result.failure(ApplicationError.conflict(
                    "StaffMember",
                    "Staff member with email '%s' already exists".formatted(command.email())
            ));
        }

        try {
            var staffMember = new StaffMember(command);
            var savedStaff = staffRepository.save(staffMember);

            eventPublisher.publishEvent(new StaffRegisteredEvent(
                    savedStaff.getId(),
                    savedStaff.getLaboratoryId(),
                    savedStaff.getFullName(),
                    savedStaff.getRole(),
                    savedStaff.getEmail()
            ));

            return Result.success(savedStaff.getId());

        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("StaffMember", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("register-staff", e.getMessage()));
        }
    }

    @Override
    public Result<Long, ApplicationError> handle(DeactivateStaffCommand command) {
        return staffRepository.findById(command.staffMemberId()).map(staff -> {
            if (!staff.isActive()) {
                return Result.<Long, ApplicationError>failure(ApplicationError.conflict(
                        "StaffMember",
                        "Staff member is already inactive"
                ));
            }

            try {
                staff.deactivate();
                var savedStaff = staffRepository.save(staff);

                eventPublisher.publishEvent(StaffDeactivatedEvent.from(savedStaff));

                return Result.<Long, ApplicationError>success(savedStaff.getId());
            } catch (Exception e) {
                return Result.<Long, ApplicationError>failure(ApplicationError.unexpected("deactivate-staff", e.getMessage()));
            }

        }).orElseGet(() -> Result.failure(ApplicationError.notFound(
                "StaffMember",
                String.valueOf(command.staffMemberId())
        )));
    }
}