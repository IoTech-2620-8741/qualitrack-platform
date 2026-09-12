package com.iotech.qualitrack.platform.ca.domain.model.entities;

import com.iotech.qualitrack.platform.ca.domain.model.valueobjects.ComplianceEventType;
import lombok.Getter;

import java.util.Objects;

/**
 * The ComplianceEvent domain entity.
 *
 * <p>Represents an audit trail entry related to a compliance-relevant action
 * or event in the QualiTrack platform.</p>
 */
@Getter
public class ComplianceEvent {

    /**
     * The unique internal numeric identifier for the compliance event.
     */
    private Long id;

    /**
     * The numeric identifier of the related domain entity.
     */
    private Long relatedEntityId;

    /**
     * The classification or type of compliance event.
     */
    private ComplianceEventType eventType;

    /**
     * A detailed description of the compliance event.
     */
    private String description;

    /**
     * The timestamp when the event occurred.
     */
    private String timestamp;

    /**
     * The numeric identifier of the user or agent that resolved the event, if applicable.
     */
    private Long resolvedBy;

    /**
     * Default constructor.
     * Required by the persistence and mapping layers to reconstruct the entity.
     */
    public ComplianceEvent() {
        // Required for reconstruction by JPA or Assemblers
    }

    /**
     * Reconstructs a ComplianceEvent entity from persistence data.
     *
     * @param id The unique numeric ID.
     * @param relatedEntityId The related domain entity ID.
     * @param eventType The compliance event type.
     * @param description The event description.
     * @param timestamp The event timestamp.
     * @param resolvedBy The resolver user ID, if applicable.
     */
    public ComplianceEvent(
            Long id,
            Long relatedEntityId,
            ComplianceEventType eventType,
            String description,
            String timestamp,
            Long resolvedBy
    ) {
        this.id = id;
        this.relatedEntityId = relatedEntityId;
        this.eventType = eventType;
        this.description = description;
        this.timestamp = timestamp;
        this.resolvedBy = resolvedBy;
    }

    /**
     * Constructs a new ComplianceEvent.
     *
     * @param relatedEntityId The related domain entity ID. Cannot be null or less than 1.
     * @param eventType The compliance event type. Cannot be null.
     * @param description The event description. Cannot be null or blank.
     * @param timestamp The event timestamp. Cannot be null or blank.
     * @param resolvedBy The resolver user ID, if applicable.
     */
    public ComplianceEvent(
            Long relatedEntityId,
            ComplianceEventType eventType,
            String description,
            String timestamp,
            Long resolvedBy
    ) {
        if (relatedEntityId == null || relatedEntityId <= 0) {
            throw new IllegalArgumentException("relatedEntityId cannot be null or less than 1");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("description cannot be null or blank");
        }
        if (timestamp == null || timestamp.isBlank()) {
            throw new IllegalArgumentException("timestamp cannot be null or blank");
        }
        if (resolvedBy != null && resolvedBy <= 0) {
            throw new IllegalArgumentException("resolvedBy cannot be less than 1");
        }

        this.relatedEntityId = relatedEntityId;
        this.eventType = Objects.requireNonNull(eventType, "eventType is required");
        this.description = description;
        this.timestamp = timestamp;
        this.resolvedBy = resolvedBy;
    }

    /**
     * Determines whether this compliance event has been resolved.
     *
     * @return true when the event has a resolver assigned
     */
    public boolean isResolved() {
        return resolvedBy != null;
    }

    /**
     * Determines whether this compliance event is still pending resolution.
     *
     * @return true when the event has not been resolved
     */
    public boolean isUnresolved() {
        return !isResolved();
    }

    /**
     * Marks this compliance event as resolved by a user or system agent.
     *
     * @param resolverId the resolver user or agent identifier
     */
    public void resolve(Long resolverId) {
        if (resolverId == null || resolverId <= 0) {
            throw new IllegalArgumentException("resolverId cannot be null or less than 1");
        }

        this.resolvedBy = resolverId;
    }
}