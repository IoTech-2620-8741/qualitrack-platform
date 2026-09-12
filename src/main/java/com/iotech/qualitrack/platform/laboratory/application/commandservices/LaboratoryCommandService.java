package com.iotech.qualitrack.platform.laboratory.application.commandservices;

import com.iotech.qualitrack.platform.laboratory.domain.model.aggregates.Laboratory;
import com.iotech.qualitrack.platform.laboratory.domain.model.commands.CreateLaboratoryCommand;
import com.iotech.qualitrack.platform.laboratory.domain.model.commands.UpdateLaboratoryCommand;
import com.iotech.qualitrack.platform.shared.application.result.ApplicationError;
import com.iotech.qualitrack.platform.shared.application.result.Result;

/**
 * Service interface for handling laboratory-related commands.
 */
public interface LaboratoryCommandService {

    // Cambiado a Long para reflejar el ID nativo autogenerado
    Result<Long, ApplicationError> handle(CreateLaboratoryCommand command);

    Result<Laboratory, ApplicationError> handle(UpdateLaboratoryCommand command);
}