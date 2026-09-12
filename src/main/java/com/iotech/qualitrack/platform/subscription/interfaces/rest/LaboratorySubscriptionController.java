package com.iotech.qualitrack.platform.subscription.interfaces.rest;

import com.iotech.qualitrack.platform.subscription.application.queryservices.SubscriptionQueryService;
import com.iotech.qualitrack.platform.subscription.domain.model.queries.GetActiveSubscriptionByLaboratoryIdQuery;
import com.iotech.qualitrack.platform.subscription.domain.model.queries.GetBillingSummaryByLaboratoryIdQuery;
import com.iotech.qualitrack.platform.subscription.domain.model.valueobjects.SubscriptionStatus;
import com.iotech.qualitrack.platform.subscription.interfaces.rest.resources.SubscriptionResource;
import com.iotech.qualitrack.platform.subscription.interfaces.rest.transform.SubscriptionResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller that exposes laboratory subscription resources.
 */
@RestController
@RequestMapping(value = "/api/v1/laboratories/{laboratoryId}", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Laboratories", description = "Laboratory subscription and billing endpoints")
public class LaboratorySubscriptionController {

    private final SubscriptionQueryService subscriptionQueryService;

    public LaboratorySubscriptionController(SubscriptionQueryService subscriptionQueryService) {
        this.subscriptionQueryService = subscriptionQueryService;
    }

    @GetMapping("/subscriptions")
    @Operation(summary = "Get laboratory subscriptions")
    public ResponseEntity<?> getLaboratorySubscriptions(
            @PathVariable Long laboratoryId,
            @RequestParam(required = false) SubscriptionStatus status
    ) {
        if (SubscriptionStatus.ACTIVE.equals(status)) {
            var subscription = subscriptionQueryService.handle(
                    new GetActiveSubscriptionByLaboratoryIdQuery(laboratoryId)
            );

            if (subscription.isEmpty()) return ResponseEntity.notFound().build();

            return ResponseEntity.ok(
                    SubscriptionResourceFromEntityAssembler.toResourceFromEntity(subscription.get())
            );
        }

        var subscriptions = subscriptionQueryService.handle(
                new GetBillingSummaryByLaboratoryIdQuery(laboratoryId)
        );

        var resources = subscriptions.stream()
                .map(SubscriptionResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }

    @GetMapping("/billing-summary")
    @Operation(summary = "Get laboratory billing summary")
    public ResponseEntity<List<SubscriptionResource>> getBillingSummaryByLaboratoryId(
            @PathVariable Long laboratoryId
    ) {
        var subscriptions = subscriptionQueryService.handle(
                new GetBillingSummaryByLaboratoryIdQuery(laboratoryId)
        );

        var resources = subscriptions.stream()
                .map(SubscriptionResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }
}