package com.iotech.qualitrack.platform.ca.domain.model.valueobjects;

/**
 * Represents the type of compliance event recorded in the audit trail.
 */
public enum ComplianceEventType {
    DEVIATION_ALERT_CREATED,
    DEVIATION_ALERT_ACKNOWLEDGED,
    DEVIATION_ALERT_RESOLVED,
    NOTIFICATION_PREFERENCE_UPDATED,
    BATCH_BLOCKED,
    BATCH_RELEASED,
    BATCH_REJECTED,
    RAW_MATERIAL_LOW_STOCK,
    EQUIPMENT_CALIBRATION_EXPIRED,
    EQUIPMENT_DEVIATION_DETECTED;

    public ComplianceEventSubject subject() {
        return switch (this) {
            case DEVIATION_ALERT_CREATED, DEVIATION_ALERT_ACKNOWLEDGED, DEVIATION_ALERT_RESOLVED -> ComplianceEventSubject.ALERT;
            case NOTIFICATION_PREFERENCE_UPDATED -> ComplianceEventSubject.USER;
            case BATCH_BLOCKED, BATCH_RELEASED, BATCH_REJECTED -> ComplianceEventSubject.BATCH;
            case RAW_MATERIAL_LOW_STOCK -> ComplianceEventSubject.RAW_MATERIAL;
            case EQUIPMENT_CALIBRATION_EXPIRED, EQUIPMENT_DEVIATION_DETECTED -> ComplianceEventSubject.EQUIPMENT;
        };
    }
}
