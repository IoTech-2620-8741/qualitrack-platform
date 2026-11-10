package com.iotech.qualitrack.platform.iam.interfaces.rest;

import com.iotech.qualitrack.platform.iam.application.internal.commandservices.EdgeGatewayApiKeyService;
import com.iotech.qualitrack.platform.iam.interfaces.rest.resources.IssuedEdgeGatewayApiKeyResource;
import com.iotech.qualitrack.platform.shared.interfaces.rest.resources.ErrorResource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for provisioning Edge Gateway API keys.
 *
 * <p>Only authorized laboratory administrators and quality
 * managers can generate Gateway credentials.</p>
 *
 * <p>API keys are returned only once and must not be cached.</p>
 */
@RestController
@RequestMapping(
        "/api/v1/laboratories/{laboratoryId}/edge-gateways"
)
@PreAuthorize(
        "@tenantAccess.allows('laboratoryId', #laboratoryId) "
                + "and hasAnyAuthority('ROLE_QA_MANAGER', 'ROLE_ADMIN')"
)
@Tag(
        name = "Edge Gateway API Keys",
        description = "Provisioning of authentication credentials "
                + "for Edge Gateways"
)
public class EdgeGatewayApiKeysController {

    private final EdgeGatewayApiKeyService apiKeyService;

    public EdgeGatewayApiKeysController(
            EdgeGatewayApiKeyService apiKeyService) {

        this.apiKeyService = apiKeyService;
    }

    /**
     * Generates an API key for a registered Edge Gateway.
     *
     * <p>The Gateway must belong to the laboratory,
     * be operational, and have an assigned environment.</p>
     *
     * @param laboratoryId laboratory identifier
     * @param equipmentId Edge Gateway equipment identifier
     * @return the newly generated API key
     */
    @PostMapping("/{equipmentId}/api-key")
    @Operation(
            summary = "Generate Edge Gateway API key",
            description = "Generates an API key for an operational "
                    + "Edge Gateway assigned to a laboratory environment. "
                    + "The API key is returned only once."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "API key generated successfully",
                    content = @Content(
                            schema = @Schema(
                                    implementation =
                                            IssuedEdgeGatewayApiKeyResource.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Authentication required"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Insufficient laboratory permissions"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Edge Gateway not found",
                    content = @Content(
                            schema = @Schema(
                                    implementation = ErrorResource.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Gateway is not eligible or "
                            + "already has an API key",
                    content = @Content(
                            schema = @Schema(
                                    implementation = ErrorResource.class
                            )
                    )
            )
    })
    public ResponseEntity<IssuedEdgeGatewayApiKeyResource> provision(
            @PathVariable Long laboratoryId,
            @PathVariable Long equipmentId) {

        var issued = apiKeyService.provision(
                laboratoryId,
                equipmentId
        );

        var resource = new IssuedEdgeGatewayApiKeyResource(
                issued.sensorExternalId(),
                issued.apiKey()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .cacheControl(CacheControl.noStore())
                .body(resource);
    }
}