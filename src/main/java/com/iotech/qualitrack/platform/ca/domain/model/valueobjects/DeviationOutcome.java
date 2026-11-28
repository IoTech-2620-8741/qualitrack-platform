package com.iotech.qualitrack.platform.ca.domain.model.valueobjects;

/**
 * What a new deviation of the same device and parameter did to the open alert of its incident.
 */
public enum DeviationOutcome {
    /**
     * The deviation joined the incident while it was still deviated, with no higher severity.
     */
    REPEATED,
    /**
     * The deviation raised the severity of the alert.
     */
    ESCALATED,
    /**
     * The condition had returned to normal and deviated again, with no higher severity: the staff is told again.
     */
    RECURRED
}
