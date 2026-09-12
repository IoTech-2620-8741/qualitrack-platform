package com.iotech.qualitrack.platform.laboratory.application.commandservices;

import com.iotech.qualitrack.platform.laboratory.domain.model.commands.DeactivateStaffCommand;
import com.iotech.qualitrack.platform.laboratory.domain.model.commands.RegisterStaffCommand;
import com.iotech.qualitrack.platform.shared.application.result.ApplicationError;
import com.iotech.qualitrack.platform.shared.application.result.Result;

/**
 * Application service contract for commands over staff members.
 */
public interface StaffCommandService {

    /**
     * Handles the registration of a new staff member to a laboratory.
     *
     * @param command command containing staff and laboratory target information
     * @return created staff member identifier (domain ID) or an application error
     * @see RegisterStaffCommand
     */
    Result<Long, ApplicationError> handle(RegisterStaffCommand command);

    /**
     * Handles the deactivation of an existing staff member.
     *
     * @param command command containing the target staff member id and laboratory id
     * @return deactivated staff member identifier or an application error
     * @see DeactivateStaffCommand
     */
    Result<Long, ApplicationError> handle(DeactivateStaffCommand command);
}