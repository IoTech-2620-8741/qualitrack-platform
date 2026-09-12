package com.iotech.qualitrack.platform.laboratory.application.commandservices;

import com.iotech.qualitrack.platform.laboratory.domain.model.commands.CreateProductCommand;
import com.iotech.qualitrack.platform.shared.application.result.ApplicationError;
import com.iotech.qualitrack.platform.shared.application.result.Result;

/**
 * Application service contract for commands over pharmaceutical products.
 */
public interface ProductCommandService {

    /**
     * Handles the creation of a new pharmaceutical product in a laboratory's catalog.
     *
     * @param command command containing initial product data
     * @return created product identifier (domain ID) or an application error
     * @see CreateProductCommand
     */
    Result<Long, ApplicationError> handle(CreateProductCommand command);
}