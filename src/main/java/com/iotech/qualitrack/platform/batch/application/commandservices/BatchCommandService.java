package com.iotech.qualitrack.platform.batch.application.commandservices;

import com.iotech.qualitrack.platform.batch.domain.model.commands.CreateBatchCommand;
import com.iotech.qualitrack.platform.batch.domain.model.commands.ReleaseBatchCommand;
import com.iotech.qualitrack.platform.batch.domain.model.commands.RejectBatchCommand;
import com.iotech.qualitrack.platform.shared.application.result.ApplicationError;
import com.iotech.qualitrack.platform.shared.application.result.Result;

/**
 * Application service contract for commands over production batches.
 */
public interface BatchCommandService {

    /**
     * Handles the creation of a new production batch.
     *
     * @param command command containing initial batch manufacturing data
     * @return created batch identifier (domain ID) or an application error
     * @see CreateBatchCommand
     */
    Result<Long, ApplicationError> handle(CreateBatchCommand command);

    /**
     * Handles the release of an existing production batch after quality control validation.
     *
     * @param command command containing release date and final notes
     * @return released batch identifier (domain ID) or an application error
     * @see ReleaseBatchCommand
     */
    Result<Long, ApplicationError> handle(ReleaseBatchCommand command);

    /**
     * Handles the rejection of an existing production batch.
     *
     * @param command command containing rejection date and reason
     * @return rejected batch identifier (domain ID) or an application error
     * @see RejectBatchCommand
     */
    Result<Long, ApplicationError> handle(RejectBatchCommand command);
}