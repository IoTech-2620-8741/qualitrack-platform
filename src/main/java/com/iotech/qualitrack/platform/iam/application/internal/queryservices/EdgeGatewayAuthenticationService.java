package com.iotech.qualitrack.platform.iam.application.internal.queryservices;

import com.iotech.qualitrack.platform.iam.application.internal.outboundservices.acl.IamExternalEquipmentService;
import com.iotech.qualitrack.platform.iam.application.internal.outboundservices.credentials.EdgeGatewayApiKeyStore;
import com.iotech.qualitrack.platform.iam.application.internal.outboundservices.hashing.HashingService;
import com.iotech.qualitrack.platform.iam.domain.model.valueobjects.EdgeGatewayIdentity;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Application service for authenticating Edge Gateways
 * using their external identifier and API key.
 *
 * <p>Gateway authentication is independent of the
 * JWT authentication used by human users.</p>
 */
@Service
@Transactional(readOnly = true)
public class EdgeGatewayAuthenticationService {

    private final IamExternalEquipmentService equipmentService;
    private final EdgeGatewayApiKeyStore apiKeyStore;
    private final HashingService hashingService;

    public EdgeGatewayAuthenticationService(
            IamExternalEquipmentService equipmentService,
            EdgeGatewayApiKeyStore apiKeyStore,
            HashingService hashingService) {

        this.equipmentService = equipmentService;
        this.apiKeyStore = apiKeyStore;
        this.hashingService = hashingService;
    }

    /**
     * Authenticates an Edge Gateway using its API key.
     *
     * <p>The Gateway must exist and possess a matching API
     * key hash in IAM; otherwise the request is rejected.
     * A Gateway with a valid key that is not operational or
     * has no environment is refused with the reason, so the
     * station knows why it cannot connect.</p>
     *
     * @param deviceId Gateway external identifier
     * @param apiKey API key received through X-API-Key
     * @return the authenticated Gateway, the rejection or the refusal
     */
    public GatewayAuthentication authenticate(
            String deviceId,
            String apiKey) {

        if (deviceId == null || deviceId.isBlank()
                || apiKey == null || apiKey.isBlank()) {
            return new Rejected();
        }

        var gateway = equipmentService.findEdgeGatewayByExternalId(deviceId)
                .filter(found -> apiKeyStore.findApiKeyHashByEquipmentId(found.equipmentId())
                        .filter(hash -> hashingService.matches(apiKey, hash))
                        .isPresent());
        if (gateway.isEmpty()) {
            return new Rejected();
        }
        var identity = gateway.get();
        if (!identity.canConnect()) {
            return new Refused(identity.connectionRefusal());
        }
        return new Authenticated(new AuthenticatedGateway(
                identity.equipmentId(),
                identity.sensorExternalId(),
                identity.laboratoryId(),
                identity.environmentId()
        ));
    }

    /**
     * Result of the authentication of an Edge Gateway request.
     */
    public sealed interface GatewayAuthentication permits Authenticated, Rejected, Refused {
    }

    /**
     * The Gateway and its API key are valid and it may connect.
     *
     * @param gateway the authenticated Gateway
     */
    public record Authenticated(AuthenticatedGateway gateway) implements GatewayAuthentication {
    }

    /**
     * Unknown Gateway, missing key or a key that does not match: the request is unauthorized.
     */
    public record Rejected() implements GatewayAuthentication {
    }

    /**
     * The key is valid, but the Gateway may not connect now (not operational or not located).
     *
     * @param reason why the Gateway may not connect
     */
    public record Refused(String reason) implements GatewayAuthentication {
    }

    /**
     * Identity of an authenticated Edge Gateway.
     *
     * <p>Contains the current laboratory and environment
     * assignment obtained from Equipment, not from
     * request parameters supplied by the client.</p>
     *
     * @param equipmentId Gateway equipment identifier
     * @param deviceId Gateway external identifier
     * @param laboratoryId owning laboratory
     * @param environmentId assigned environment
     */
    public record AuthenticatedGateway(
            Long equipmentId,
            String deviceId,
            Long laboratoryId,
            Long environmentId
    ) {}
}