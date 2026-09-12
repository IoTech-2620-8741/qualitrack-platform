package com.iotech.qualitrack.platform.ca.domain.model.aggregates;

import com.iotech.qualitrack.platform.ca.domain.model.commands.AcknowledgeAlertCommand;
import com.iotech.qualitrack.platform.ca.domain.model.commands.CreateDeviationAlertCommand;
import com.iotech.qualitrack.platform.ca.domain.model.commands.ResolveAlertCommand;
import com.iotech.qualitrack.platform.ca.domain.model.valueobjects.AlertSeverity;
import com.iotech.qualitrack.platform.ca.domain.model.valueobjects.AlertStatus;
import com.iotech.qualitrack.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;

import java.util.Objects;

/**
 * The DeviationAlert Aggregate Root.
 *
 * <p>Represents a deviation detected in a monitored process parameter.
 * It governs the lifecycle of a compliance alert from creation to acknowledgement
 * and final resolution.</p>
 */
@Getter
public class DeviationAlert extends AbstractDomainAggregateRoot<DeviationAlert> {

    /**
     * The unique numeric identifier for the deviation alert.
     */
    private Long id;

    /**
     * The numeric identifier of the equipment that generated the alert.
     */
    private Long equipmentId;

    /**
     * The numeric identifier of the production batch associated with the alert, if applicable.
     */
    private Long batchId;

    /**
     * The name of the monitored process parameter.
     */
    private String parameterName;

    /**
     * The measured value that triggered the deviation.
     */
    private Double recordedValue;

    /**
     * The configured threshold value that was exceeded.
     */
    private Double thresholdValue;

    /**
     * The measurement unit for the recorded and threshold values.
     */
    private String unit;

    /**
     * The timestamp when the deviation occurred.
     */
    private String timestamp;

    /**
     * The impact level of the deviation alert.
     */
    private AlertSeverity severity;

    /**
     * The current lifecycle status of the alert.
     */
    private AlertStatus status;

    /**
     * The numeric identifier of the user who acknowledged the alert, if applicable.
     */
    private Long acknowledgedBy;

    /**
     * The numeric identifier of the user who resolved the alert, if applicable.
     */
    private Long resolvedBy;

    /**
     * Notes describing the corrective action or resolution, if applicable.
     */
    private String resolutionNotes;

    /**
     * Default constructor.
     * Required by the persistence and mapping layers to reconstruct the entity.
     */
    public DeviationAlert() {
        // Required for reconstruction by JPA or Assemblers
    }

    /**
     * Reconstructs a DeviationAlert entity from persistence data.
     *
     * @param id The numeric alert ID.
     * @param equipmentId The equipment ID.
     * @param batchId The batch ID, if applicable.
     * @param parameterName The monitored parameter name.
     * @param recordedValue The measured value.
     * @param thresholdValue The threshold value.
     * @param unit The measurement unit.
     * @param timestamp The deviation timestamp.
     * @param severity The alert severity.
     * @param status The alert status.
     * @param acknowledgedBy The user who acknowledged the alert, if applicable.
     * @param resolvedBy The user who resolved the alert, if applicable.
     * @param resolutionNotes Resolution or corrective action notes, if applicable.
     */
    public DeviationAlert(
            Long id,
            Long equipmentId,
            Long batchId,
            String parameterName,
            Double recordedValue,
            Double thresholdValue,
            String unit,
            String timestamp,
            AlertSeverity severity,
            AlertStatus status,
            Long acknowledgedBy,
            Long resolvedBy,
            String resolutionNotes
    ) {
        this.id = id;
        this.equipmentId = equipmentId;
        this.batchId = batchId;
        this.parameterName = parameterName;
        this.recordedValue = recordedValue;
        this.thresholdValue = thresholdValue;
        this.unit = unit;
        this.timestamp = timestamp;
        this.severity = severity;
        this.status = status;
        this.acknowledgedBy = acknowledgedBy;
        this.resolvedBy = resolvedBy;
        this.resolutionNotes = resolutionNotes;
    }

    /**
     * Creates a new DeviationAlert based on the provided command.
     *
     * @param command The command containing the alert creation data.
     */
    public DeviationAlert(CreateDeviationAlertCommand command) {
        Objects.requireNonNull(command, "Create deviation alert command is required");

        this.equipmentId = Objects.requireNonNull(command.equipmentId(), "Equipment ID is required");
        this.batchId = command.batchId();
        this.parameterName = Objects.requireNonNull(command.parameterName(), "Parameter name is required");
        this.recordedValue = Objects.requireNonNull(command.recordedValue(), "Recorded value is required");
        this.thresholdValue = Objects.requireNonNull(command.thresholdValue(), "Threshold value is required");
        this.unit = Objects.requireNonNull(command.unit(), "Unit is required");
        this.timestamp = Objects.requireNonNull(command.timestamp(), "Timestamp is required");
        this.severity = Objects.requireNonNull(command.severity(), "Severity is required");
        this.status = AlertStatus.UNRESOLVED;
    }

    /**
     * Acknowledges the deviation alert.
     *
     * @param command The command containing acknowledgement information.
     */
    public void acknowledge(AcknowledgeAlertCommand command) {
        Objects.requireNonNull(command, "Acknowledge alert command is required");

        if (this.status == AlertStatus.RESOLVED) {
            throw new IllegalStateException("Resolved alerts cannot be acknowledged");
        }
        if (this.status == AlertStatus.ACKNOWLEDGED) {
            throw new IllegalStateException("Alert is already acknowledged");
        }

        this.status = AlertStatus.ACKNOWLEDGED;
        this.acknowledgedBy = Objects.requireNonNull(command.acknowledgedBy(), "Acknowledged by is required");
    }

    /**
     * Resolves the deviation alert after corrective action.
     *
     * @param command The command containing resolution information.
     */
    public void resolve(ResolveAlertCommand command) {
        Objects.requireNonNull(command, "Resolve alert command is required");

        if (this.status == AlertStatus.RESOLVED) {
            throw new IllegalStateException("Alert is already resolved");
        }

        this.status = AlertStatus.RESOLVED;
        this.resolvedBy = Objects.requireNonNull(command.resolvedBy(), "Resolved by is required");
        this.resolutionNotes = Objects.requireNonNull(command.resolutionNotes(), "Resolution notes are required");
    }

    /**
     * Indicates whether this alert is still unresolved.
     *
     * @return true if the alert is unresolved; otherwise false.
     */
    public boolean isUnresolved() {
        return this.status == AlertStatus.UNRESOLVED;
    }

    /**
     * Indicates whether this alert is critical.
     *
     * @return true if the alert severity is critical; otherwise false.
     */
    public boolean isCritical() {
        return this.severity == AlertSeverity.CRITICAL;
    }
}