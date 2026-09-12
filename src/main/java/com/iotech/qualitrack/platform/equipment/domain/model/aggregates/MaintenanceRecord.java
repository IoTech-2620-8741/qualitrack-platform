package com.iotech.qualitrack.platform.equipment.domain.model.aggregates;

import com.iotech.qualitrack.platform.equipment.domain.model.commands.RegisterMaintenanceCommand;
import com.iotech.qualitrack.platform.equipment.domain.model.valueobjects.MaintenanceType;
import com.iotech.qualitrack.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Objects;

/**
 * The MaintenanceRecord Aggregate Root.
 *
 * <p>Represents a documented maintenance activity on a specific equipment.
 * It ensures traceability of technical interventions for regulatory compliance.</p>
 */
@Getter
public class MaintenanceRecord extends AbstractDomainAggregateRoot<MaintenanceRecord> {

    /**
     * The unique numeric identifier for the maintenance record.
     */
    private Long id;

    /**
     * The numeric identifier of the equipment associated with this record.
     */
    private Long equipmentId;

    /**
     * The validated date of the maintenance activity.
     */
    private LocalDate maintenanceDate;

    private String technicianName;
    private String description;

    private MaintenanceType type;

    /**
     * Default constructor.
     * Required for reconstruction by JPA or Assemblers.
     */
    public MaintenanceRecord() {
        // Required for reconstruction
    }

    /**
     * Reconstructs a MaintenanceRecord entity from persistence data.
     *
     * @param id The numeric ID.
     * @param equipmentId The associated equipment ID.
     * @param maintenanceDate The native Java date of maintenance.
     * @param technicianName The technician's name.
     * @param description The summary of the activity.
     * @param type The type of maintenance performed.
     */
    public MaintenanceRecord(Long id, Long equipmentId, LocalDate maintenanceDate, String technicianName, String description, MaintenanceType type) {
        this.id = id;
        this.equipmentId = equipmentId;
        this.maintenanceDate = maintenanceDate;
        this.technicianName = technicianName;
        this.description = description;
        this.type = type;
    }

    /**
     * Registers a new Maintenance Record based on the provided command.
     * <p>Validates and transforms external text representations into rich domain objects.</p>
     *
     * @param command The command containing the maintenance activity data.
     */
    public MaintenanceRecord(RegisterMaintenanceCommand command) {
        this.equipmentId = Objects.requireNonNull(command.equipmentId(), "Equipment ID is required");

        // Parseamos el string del command a un objeto fecha real (Fail-Fast)
        try {
            String dateString = Objects.requireNonNull(command.maintenanceDate(), "Maintenance date is required");
            this.maintenanceDate = LocalDate.parse(dateString);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Maintenance date must be a valid format (YYYY-MM-DD)");
        }

        this.technicianName = Objects.requireNonNull(command.technicianName(), "Technician name is required");
        this.description = Objects.requireNonNull(command.description(), "Description is required");

        // Validamos que el tipo enviado coincida con los valores de nuestro Enum
        try {
            String typeString = Objects.requireNonNull(command.type(), "Maintenance type is required");
            this.type = MaintenanceType.valueOf(typeString.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid maintenance type provided: " + command.type());
        }
    }
}