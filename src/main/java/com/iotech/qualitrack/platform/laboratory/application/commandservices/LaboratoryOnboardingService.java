package com.iotech.qualitrack.platform.laboratory.application.commandservices;

import com.iotech.qualitrack.platform.laboratory.domain.model.commands.CreateLaboratoryCommand;

public interface LaboratoryOnboardingService {
    Long create(Long userId, CreateLaboratoryCommand command);
}
