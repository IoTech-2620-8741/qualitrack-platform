package com.iotech.qualitrack.platform.equipment.application.commandservices;

import com.iotech.qualitrack.platform.equipment.domain.model.commands.RegisterMaintenanceCommand;
import com.iotech.qualitrack.platform.shared.application.result.ApplicationError;
import com.iotech.qualitrack.platform.shared.application.result.Result;

/**
 * Application service contract for commands over maintenance records.
 */
public interface MaintenanceCommandService {

    /**
     * Handles the registration of a new maintenance activity performed on an equipment.
     *
     * @param command command containing the maintenance details (technician, date, type, etc.)
     * @return created maintenance record identifier (domain ID) or an application error
     * @see RegisterMaintenanceCommand
     */
    Result<Long, ApplicationError> handle(RegisterMaintenanceCommand command);
}