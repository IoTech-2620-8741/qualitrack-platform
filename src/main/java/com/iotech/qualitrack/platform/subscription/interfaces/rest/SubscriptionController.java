package com.iotech.qualitrack.platform.subscription.interfaces.rest;

import com.iotech.qualitrack.platform.shared.application.result.ApplicationError;
import com.iotech.qualitrack.platform.shared.interfaces.rest.transform.ErrorResponseAssembler;
import com.iotech.qualitrack.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import com.iotech.qualitrack.platform.subscription.application.commandservices.SubscriptionCommandService;
import com.iotech.qualitrack.platform.subscription.application.queryservices.SubscriptionQueryService;
import com.iotech.qualitrack.platform.subscription.domain.model.queries.GetPaymentsBySubscriptionIdQuery;
import com.iotech.qualitrack.platform.subscription.domain.model.valueobjects.SubscriptionStatus;
import com.iotech.qualitrack.platform.subscription.interfaces.rest.resources.SubscriptionPaymentResource;
import com.iotech.qualitrack.platform.subscription.interfaces.rest.resources.UpdateSubscriptionStatusResource;
import com.iotech.qualitrack.platform.subscription.interfaces.rest.transform.SubscriptionPaymentResourceFromEntityAssembler;
import com.iotech.qualitrack.platform.subscription.interfaces.rest.transform.UpdateSubscriptionStatusCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller that exposes subscription resources.
 */
@RestController
@RequestMapping(value = "/api/v1/subscriptions", produces = APPLICATION_JSON_VALUE)
public class SubscriptionController {

    private final SubscriptionCommandService subscriptionCommandService;
    private final SubscriptionQueryService subscriptionQueryService;

    public SubscriptionController(
            SubscriptionCommandService subscriptionCommandService,
            SubscriptionQueryService subscriptionQueryService
    ) {
        this.subscriptionCommandService = subscriptionCommandService;
        this.subscriptionQueryService = subscriptionQueryService;
    }

    @GetMapping("/{subscriptionId}/payments")
    @Operation(summary = "Get subscription payments")
    public ResponseEntity<List<SubscriptionPaymentResource>> getPaymentsBySubscriptionId(
            @PathVariable Long subscriptionId
    ) {
        var payments = subscriptionQueryService.handle(
                new GetPaymentsBySubscriptionIdQuery(subscriptionId)
        );

        var resources = payments.stream()
                .map(SubscriptionPaymentResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }

    @PatchMapping(value = "/{subscriptionId}", consumes = APPLICATION_JSON_VALUE)
    @org.springframework.security.access.prepost.PreAuthorize("@tenantAccess.allows('userId', #resource.cancelledBy())")
    @Operation(summary = "Update subscription status")
    public ResponseEntity<?> updateSubscriptionStatus(
            @PathVariable Long subscriptionId,
            @RequestBody UpdateSubscriptionStatusResource resource
    ) {
        if (resource.status() == null) {
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(
                    ApplicationError.validationError("Subscription", "status is required")
            );
        }

        if (!SubscriptionStatus.CANCELLED.equals(resource.status())) {
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(
                    ApplicationError.validationError(
                            "Subscription",
                            "Only status CANCELLED is supported for subscription updates"
                    )
            );
        }

        var command = UpdateSubscriptionStatusCommandFromResourceAssembler
                .toCancelCommandFromResource(subscriptionId, resource);

        var result = subscriptionCommandService.handle(command);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                id -> id,
                HttpStatus.OK
        );
    }
}
