package com.iotech.qualitrack.platform.equipment.application.commandservices;

import com.iotech.qualitrack.platform.equipment.domain.model.commands.ConfigureBpmParametersCommand;
import com.iotech.qualitrack.platform.shared.application.result.ApplicationError;
import com.iotech.qualitrack.platform.shared.application.result.Result;

/**
 * Application service contract for configuring Business Process Management (BPM) parameters
 * for equipment telemetry.
 */
public interface BpmConfigCommandService {

    /**
     * Handles the configuration of acceptable operating ranges (BPM parameters) for a specific equipment.
     *
     * @param command command containing the parameter limits (min/max) and unit
     * @return the created/updated configuration identifier (domain ID) or an application error
     * @see ConfigureBpmParametersCommand
     */
    Result<Long, ApplicationError> handle(ConfigureBpmParametersCommand command);
}