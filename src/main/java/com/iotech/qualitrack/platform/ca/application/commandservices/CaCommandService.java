package com.iotech.qualitrack.platform.ca.application.commandservices;

import com.iotech.qualitrack.platform.ca.domain.model.commands.AcknowledgeAlertCommand;
import com.iotech.qualitrack.platform.ca.domain.model.commands.CreateDeviationAlertCommand;
import com.iotech.qualitrack.platform.ca.domain.model.commands.ResolveAlertCommand;
import com.iotech.qualitrack.platform.ca.domain.model.commands.UpdateNotificationPreferenceCommand;
import com.iotech.qualitrack.platform.ca.domain.model.entities.NotificationPreference;
import com.iotech.qualitrack.platform.shared.application.result.ApplicationError;
import com.iotech.qualitrack.platform.shared.application.result.Result;

/**
 * Application service contract for commands over compliance alerts and notification preferences.
 */
public interface CaCommandService {

    /**
     * Handles the creation of a new deviation alert.
     *
     * @param command command containing deviation telemetry and severity data
     * @return created alert identifier (domain ID) or an application error
     * @see CreateDeviationAlertCommand
     */
    Result<Long, ApplicationError> handle(CreateDeviationAlertCommand command);

    /**
     * Handles the acknowledgement of an existing deviation alert.
     *
     * @param command command containing alert and user acknowledgement data
     * @return acknowledged alert identifier (domain ID) or an application error
     * @see AcknowledgeAlertCommand
     */
    Result<Long, ApplicationError> handle(AcknowledgeAlertCommand command);

    /**
     * Handles the resolution of an existing deviation alert.
     *
     * @param command command containing alert resolution data
     * @return resolved alert identifier (domain ID) or an application error
     * @see ResolveAlertCommand
     */
    Result<Long, ApplicationError> handle(ResolveAlertCommand command);

    /**
     * Handles the update of user notification preferences.
     *
     * @param command command containing notification preference settings
     * @return updated notification preference or an application error
     * @see UpdateNotificationPreferenceCommand
     */
    Result<NotificationPreference, ApplicationError> handle(UpdateNotificationPreferenceCommand command);
}