package com.iotech.qualitrack.platform.laboratory.application.commandservices;

import com.iotech.qualitrack.platform.laboratory.domain.model.commands.CreateRawMaterialCommand;
import com.iotech.qualitrack.platform.shared.application.result.ApplicationError;
import com.iotech.qualitrack.platform.shared.application.result.Result;

/**
 * Application service contract for commands over raw materials.
 */
public interface RawMaterialCommandService {

    /**
     * Handles the registration of a new raw material with its initial stock.
     *
     * @param command command containing raw material specifications and initial stock
     * @return created raw material identifier (domain ID) or an application error
     * @see CreateRawMaterialCommand
     */
    Result<Long, ApplicationError> handle(CreateRawMaterialCommand command);
}