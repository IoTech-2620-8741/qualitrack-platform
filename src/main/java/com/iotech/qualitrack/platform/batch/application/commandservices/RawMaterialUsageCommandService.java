package com.iotech.qualitrack.platform.batch.application.commandservices;

import com.iotech.qualitrack.platform.batch.domain.model.commands.LinkRawMaterialCommand;
import com.iotech.qualitrack.platform.shared.application.result.ApplicationError;
import com.iotech.qualitrack.platform.shared.application.result.Result;

/**
 * Application service contract for commands over raw material usage records.
 */
public interface RawMaterialUsageCommandService {

    /**
     * Handles the linking of a raw material usage record to a production batch.
     *
     * @param command command containing batch, raw material, and consumed quantity data
     * @return created raw material usage identifier (domain ID) or an application error
     * @see LinkRawMaterialCommand
     */
    Result<Long, ApplicationError> handle(LinkRawMaterialCommand command);
}