package com.iotech.qualitrack.platform.tracking.application.internal.outboundservices.acl;

import com.iotech.qualitrack.platform.ra.domain.model.valueobjects.AuditAction;
import com.iotech.qualitrack.platform.ra.interfaces.acl.RaContextFacade;
import org.springframework.stereotype.Service;

/**
 * Outbound ACL service used by Tracking to write audit records through RA.
 *
 * @remarks
 * This service keeps Tracking independent from RA application internals while
 * still allowing telemetry actions to be traceable.
 */
@Service
public class TrackingExternalAuditService {

    private static final String MEASUREMENT_ENTITY_TYPE = "TELEMETRY_MEASUREMENT";
    private static final String STATUS_ENTITY_TYPE = "EQUIPMENT_TELEMETRY_STATUS";
    private static final String HISTORY_POINT_ENTITY_TYPE = "TELEMETRY_HISTORY_POINT";

    private final RaContextFacade raContextFacade;

    /**
     * Creates a new TrackingExternalAuditService.
     *
     * @param raContextFacade RA bounded context facade
     */
    public TrackingExternalAuditService(RaContextFacade raContextFacade) {
        this.raContextFacade = raContextFacade;
    }

    /**
     * Records an audit log entry for a telemetry measurement.
     *
     * @param measurementId the measurement identifier
     * @param equipmentId the equipment identifier
     * @return true when the audit record succeeds
     */
    public boolean recordMeasurementAudit(Long measurementId, Long equipmentId) {
        return raContextFacade.recordAuditLog(
                AuditAction.REGISTER,
                MEASUREMENT_ENTITY_TYPE,
                measurementId,
                null,
                "Telemetry measurement recorded for equipment %d".formatted(equipmentId)
        );
    }

    /**
     * Records an audit log entry for an equipment telemetry status update.
     *
     * @param statusId the status identifier
     * @param equipmentId the equipment identifier
     * @return true when the audit record succeeds
     */
    public boolean recordStatusUpdateAudit(Long statusId, Long equipmentId) {
        return raContextFacade.recordAuditLog(
                AuditAction.UPDATE,
                STATUS_ENTITY_TYPE,
                statusId,
                null,
                "Equipment telemetry status updated for equipment %d".formatted(equipmentId)
        );
    }

    /**
     * Records an audit log entry for a telemetry history point.
     *
     * @param historyPointId the history point identifier
     * @param equipmentId the equipment identifier
     * @return true when the audit record succeeds
     */
    public boolean recordHistoryPointAudit(Long historyPointId, Long equipmentId) {
        return raContextFacade.recordAuditLog(
                AuditAction.REGISTER,
                HISTORY_POINT_ENTITY_TYPE,
                historyPointId,
                null,
                "Telemetry history point recorded for equipment %d".formatted(equipmentId)
        );
    }
}