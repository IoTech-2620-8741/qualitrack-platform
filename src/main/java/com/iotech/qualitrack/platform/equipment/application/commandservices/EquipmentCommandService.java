package com.iotech.qualitrack.platform.equipment.application.commandservices;

import com.iotech.qualitrack.platform.equipment.domain.model.commands.LinkSensorCommand;
import com.iotech.qualitrack.platform.equipment.domain.model.commands.RegisterEquipmentCommand;
import com.iotech.qualitrack.platform.shared.application.result.ApplicationError;
import com.iotech.qualitrack.platform.shared.application.result.Result;

/**
 * Application service contract for commands over equipment records.
 */
public interface EquipmentCommandService {

    /**
     * Handles the registration of a new equipment in a laboratory.
     *
     * @param command command containing initial equipment data
     * @return created equipment identifier (domain ID) or an application error
     * @see RegisterEquipmentCommand
     */
    Result<Long, ApplicationError> handle(RegisterEquipmentCommand command);

    /**
     * Handles the linking of an external IoT sensor to an existing equipment.
     *
     * @param command command containing the equipment ID and external sensor ID
     * @return the equipment identifier (domain ID) upon successful linking or an application error
     * @see LinkSensorCommand
     */
    Result<Long, ApplicationError> handle(LinkSensorCommand command);
}