package com.iotech.qualitrack.platform.iam.domain.model.valueobjects;

/**
 * Edge Gateway as IAM needs it to issue its API key and to authenticate it.
 *
 * @param equipmentId      the equipment identifier of the Gateway
 * @param laboratoryId     the laboratory that owns the Gateway
 * @param environmentId    the environment where the Gateway is located, or null when it is not located
 * @param sensorExternalId the identifier with which the Gateway signs its requests
 * @param operational      true when the Gateway is operational
 * @param status           the operational status of the Gateway, for example MAINTENANCE
 */
public record EdgeGatewayIdentity(Long equipmentId, Long laboratoryId, Long environmentId, String sensorExternalId,
                                  boolean operational, String status) {

    /**
     * Whether the Gateway is located in an environment.
     *
     * @return true when it has an environment
     */
    public boolean isLocated() {
        return environmentId != null;
    }

    /**
     * Whether the Gateway may hold and use an API key: operational and located in an environment.
     *
     * @return true when it is operational and located
     */
    public boolean canConnect() {
        return operational && isLocated();
    }

    /**
     * Why the Gateway may not connect now, told only to the station that holds its valid API key.
     *
     * @return the reason, or null when the Gateway may connect
     */
    public String connectionRefusal() {
        if (!operational) return "The Edge Gateway is in " + status + " status; only operational Gateways connect";
        if (!isLocated()) return "The Edge Gateway is not located in an environment";
        return null;
    }
}
