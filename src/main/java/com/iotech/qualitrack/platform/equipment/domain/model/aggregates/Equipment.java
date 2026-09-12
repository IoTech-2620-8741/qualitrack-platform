package com.iotech.qualitrack.platform.equipment.domain.model.aggregates;

import com.iotech.qualitrack.platform.equipment.domain.model.commands.RegisterEquipmentCommand;
import com.iotech.qualitrack.platform.equipment.domain.model.valueobjects.DeviceId;
import com.iotech.qualitrack.platform.equipment.domain.model.valueobjects.EquipmentStatus;
import com.iotech.qualitrack.platform.equipment.domain.model.valueobjects.EquipmentType;
import com.iotech.qualitrack.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;

import java.util.Objects;

/**
 * The Equipment Aggregate Root.
 *
 * <p>Represents a physical equipment registered within a laboratory in the QualiTrack platform.
 * Governs the state of the equipment, its classification, and its IoT sensor linkages.</p>
 */
@Getter
public class Equipment extends AbstractDomainAggregateRoot<Equipment> {

    /**
     * The unique numeric identifier for the equipment.
     */
    private Long id;

    /**
     * The numeric identifier of the laboratory where the equipment is located.
     */
    private Long labId;

    private String name;

    /**
     * The category of the equipment, modeled as a Value Object.
     */
    private EquipmentType type;

    private String model;
    private String serialNumber;

    private EquipmentStatus status;

    /**
     * External identifier for IoT sensor integration (if linked), modeled as a Value Object.
     */
    private DeviceId sensorExternalId;

    /**
     * Default constructor.
     * Required by the persistence and mapping layers (Assemblers) to reconstruct
     * the entity from the database without triggering business logic.
     */
    public Equipment() {
        // Required for reconstruction by JPA or Assemblers
    }

    /**
     * Reconstructs an Equipment entity from persistence data.
     *
     * @param id The numeric ID.
     * @param labId The laboratory ID.
     * @param name The equipment name.
     * @param type The equipment category (VO).
     * @param model The technical model.
     * @param serialNumber The unique serial number.
     * @param status The operational status.
     * @param sensorExternalId The IoT sensor ID (VO) (can be null).
     */
    public Equipment(Long id, Long labId, String name, EquipmentType type, String model, String serialNumber, EquipmentStatus status, DeviceId sensorExternalId) {
        this.id = id;
        this.labId = labId;
        this.name = name;
        this.type = type;
        this.model = model;
        this.serialNumber = serialNumber;
        this.status = status;
        this.sensorExternalId = sensorExternalId;
    }

    /**
     * Registers a new Equipment based on the provided command.
     * <p>Initializes the entity, instantiates the required Value Objects,
     * and sets the default status to OPERATIONAL.</p>
     *
     * @param command The command containing the registration data.
     */
    public Equipment(RegisterEquipmentCommand command) {
        this.labId = Objects.requireNonNull(command.labId(), "Laboratory ID is required");
        this.name = Objects.requireNonNull(command.name(), "Equipment name is required");

        // Instanciamos el Value Object pasando el string del command
        this.type = new EquipmentType(command.type());

        this.model = Objects.requireNonNull(command.model(), "Model is required");
        this.serialNumber = Objects.requireNonNull(command.serialNumber(), "Serial number is required");
        this.status = EquipmentStatus.OPERATIONAL;
    }

    /**
     * Links an external IoT sensor to this equipment.
     *
     * @param sensorExternalId The external sensor identifier string.
     */
    public void linkSensor(String sensorExternalId) {
        // El constructor del DeviceId se encarga de las validaciones de negocio (nulos, vacíos, longitud)
        this.sensorExternalId = new DeviceId(sensorExternalId);
    }

    /**
     * Updates the operational status of the equipment.
     *
     * @param newStatus The new operational status.
     */
    public void updateStatus(EquipmentStatus newStatus) {
        this.status = Objects.requireNonNull(newStatus, "New status cannot be null");
    }
}