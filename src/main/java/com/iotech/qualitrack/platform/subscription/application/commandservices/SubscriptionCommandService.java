package com.iotech.qualitrack.platform.subscription.application.commandservices;

import com.iotech.qualitrack.platform.shared.application.result.ApplicationError;
import com.iotech.qualitrack.platform.shared.application.result.Result;
import com.iotech.qualitrack.platform.subscription.domain.model.commands.ActivateSubscriptionCommand;
import com.iotech.qualitrack.platform.subscription.domain.model.commands.CancelSubscriptionCommand;
import com.iotech.qualitrack.platform.subscription.domain.model.commands.CreateCheckoutSessionCommand;
import com.iotech.qualitrack.platform.subscription.domain.model.commands.RecordStripePaymentCommand;

/**
 * Application command service contract for subscription write use cases.
 */
public interface SubscriptionCommandService {

    /**
     * Creates a Stripe checkout session.
     *
     * @param command The checkout session command.
     * @return The checkout session URL or an application error.
     */
    Result<String, ApplicationError> handle(CreateCheckoutSessionCommand command);

    /**
     * Activates a subscription after Stripe confirms payment.
     *
     * @param command The activate subscription command.
     * @return The subscription identifier or an application error.
     */
    Result<Long, ApplicationError> handle(ActivateSubscriptionCommand command);

    /**
     * Cancels an active subscription.
     *
     * @param command The cancel subscription command.
     * @return The cancelled subscription identifier or an application error.
     */
    Result<Long, ApplicationError> handle(CancelSubscriptionCommand command);

    /**
     * Records a Stripe payment.
     *
     * @param command The record Stripe payment command.
     * @return The payment identifier or an application error.
     */
    Result<Long, ApplicationError> handle(RecordStripePaymentCommand command);
}