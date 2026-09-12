package com.iotech.qualitrack.platform.tracking.infrastructure.persistence.jpa.adapters;

import com.iotech.qualitrack.platform.tracking.domain.model.aggregates.EquipmentTelemetry;
import com.iotech.qualitrack.platform.tracking.domain.repositories.EquipmentTelemetryRepository;
import com.iotech.qualitrack.platform.tracking.infrastructure.persistence.jpa.assemblers.EquipmentTelemetryPersistenceAssembler;
import com.iotech.qualitrack.platform.tracking.infrastructure.persistence.jpa.assemblers.EquipmentTelemetryStatusPersistenceAssembler;
import com.iotech.qualitrack.platform.tracking.infrastructure.persistence.jpa.assemblers.MeasurementPersistenceAssembler;
import com.iotech.qualitrack.platform.tracking.infrastructure.persistence.jpa.assemblers.TelemetryHistoryPointPersistenceAssembler;
import com.iotech.qualitrack.platform.tracking.infrastructure.persistence.jpa.repositories.EquipmentTelemetryPersistenceRepository;
import com.iotech.qualitrack.platform.tracking.infrastructure.persistence.jpa.repositories.EquipmentTelemetryStatusPersistenceRepository;
import com.iotech.qualitrack.platform.tracking.infrastructure.persistence.jpa.repositories.MeasurementPersistenceRepository;
import com.iotech.qualitrack.platform.tracking.infrastructure.persistence.jpa.repositories.TelemetryHistoryPointPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * JPA adapter implementation for the {@link EquipmentTelemetryRepository} aggregate port.
 */
@Repository
public class EquipmentTelemetryRepositoryImpl implements EquipmentTelemetryRepository {

    private final EquipmentTelemetryPersistenceRepository telemetryPersistenceRepository;
    private final EquipmentTelemetryStatusPersistenceRepository statusPersistenceRepository;
    private final MeasurementPersistenceRepository measurementPersistenceRepository;
    private final TelemetryHistoryPointPersistenceRepository historyPointPersistenceRepository;

    public EquipmentTelemetryRepositoryImpl(
            EquipmentTelemetryPersistenceRepository telemetryPersistenceRepository,
            EquipmentTelemetryStatusPersistenceRepository statusPersistenceRepository,
            MeasurementPersistenceRepository measurementPersistenceRepository,
            TelemetryHistoryPointPersistenceRepository historyPointPersistenceRepository
    ) {
        this.telemetryPersistenceRepository = telemetryPersistenceRepository;
        this.statusPersistenceRepository = statusPersistenceRepository;
        this.measurementPersistenceRepository = measurementPersistenceRepository;
        this.historyPointPersistenceRepository = historyPointPersistenceRepository;
    }

    @Override
    public Optional<EquipmentTelemetry> findById(Long id) {
        return telemetryPersistenceRepository.findById(id)
                .map(entity -> {
                    var status = statusPersistenceRepository
                            .findFirstByEquipmentIdOrderByLastHeartbeatDesc(entity.getEquipmentId())
                            .map(EquipmentTelemetryStatusPersistenceAssembler::toDomainFromPersistence)
                            .orElse(null);

                    var measurements = measurementPersistenceRepository
                            .findAllByEquipmentIdOrderByTimestampDesc(entity.getEquipmentId())
                            .stream()
                            .limit(50)
                            .map(MeasurementPersistenceAssembler::toDomainFromPersistence)
                            .toList();

                    var historyPoints = historyPointPersistenceRepository
                            .findAllByEquipmentIdOrderByTimestampDesc(entity.getEquipmentId())
                            .stream()
                            .limit(100)
                            .map(TelemetryHistoryPointPersistenceAssembler::toDomainFromPersistence)
                            .toList();

                    return EquipmentTelemetryPersistenceAssembler.toDomainFromPersistence(
                            entity,
                            status,
                            measurements,
                            historyPoints
                    );
                });
    }

    @Override
    public Optional<EquipmentTelemetry> findByEquipmentId(Long equipmentId) {
        return telemetryPersistenceRepository.findByEquipmentId(equipmentId)
                .map(entity -> {
                    var status = statusPersistenceRepository
                            .findFirstByEquipmentIdOrderByLastHeartbeatDesc(equipmentId)
                            .map(EquipmentTelemetryStatusPersistenceAssembler::toDomainFromPersistence)
                            .orElse(null);

                    var measurements = measurementPersistenceRepository
                            .findAllByEquipmentIdOrderByTimestampDesc(equipmentId)
                            .stream()
                            .limit(50)
                            .map(MeasurementPersistenceAssembler::toDomainFromPersistence)
                            .toList();

                    var historyPoints = historyPointPersistenceRepository
                            .findAllByEquipmentIdOrderByTimestampDesc(equipmentId)
                            .stream()
                            .limit(100)
                            .map(TelemetryHistoryPointPersistenceAssembler::toDomainFromPersistence)
                            .toList();

                    return EquipmentTelemetryPersistenceAssembler.toDomainFromPersistence(
                            entity,
                            status,
                            measurements,
                            historyPoints
                    );
                });
    }

    @Override
    public EquipmentTelemetry save(EquipmentTelemetry equipmentTelemetry) {
        var entity = EquipmentTelemetryPersistenceAssembler.toPersistenceFromDomain(equipmentTelemetry);
        var saved = telemetryPersistenceRepository.save(entity);

        return findById(saved.getId())
                .orElseGet(() -> EquipmentTelemetryPersistenceAssembler.toDomainFromPersistence(
                        saved,
                        equipmentTelemetry.getStatus(),
                        equipmentTelemetry.getMeasurements(),
                        equipmentTelemetry.getHistoryPoints()
                ));
    }

    @Override
    public boolean existsByEquipmentId(Long equipmentId) {
        return telemetryPersistenceRepository.existsByEquipmentId(equipmentId);
    }
}