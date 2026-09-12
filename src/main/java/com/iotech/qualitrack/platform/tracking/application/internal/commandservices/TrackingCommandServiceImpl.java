package com.iotech.qualitrack.platform.tracking.application.internal.commandservices;

import com.iotech.qualitrack.platform.shared.application.result.ApplicationError;
import com.iotech.qualitrack.platform.shared.application.result.Result;
import com.iotech.qualitrack.platform.tracking.application.commandservices.TrackingCommandService;
import com.iotech.qualitrack.platform.tracking.domain.model.commands.RecordMeasurementCommand;
import com.iotech.qualitrack.platform.tracking.domain.model.commands.RecordTelemetryHistoryPointCommand;
import com.iotech.qualitrack.platform.tracking.domain.model.commands.UpdateEquipmentTelemetryStatusCommand;
import com.iotech.qualitrack.platform.tracking.domain.model.entities.EquipmentTelemetryStatus;
import com.iotech.qualitrack.platform.tracking.domain.model.entities.Measurement;
import com.iotech.qualitrack.platform.tracking.domain.model.entities.TelemetryHistoryPoint;
import com.iotech.qualitrack.platform.tracking.domain.model.events.EquipmentTelemetryStatusUpdatedEvent;
import com.iotech.qualitrack.platform.tracking.domain.model.events.MeasurementRecordedEvent;
import com.iotech.qualitrack.platform.tracking.domain.model.events.TelemetryAnomalyDetectedEvent;
import com.iotech.qualitrack.platform.tracking.domain.model.events.TelemetryHistoryPointRecordedEvent;
import com.iotech.qualitrack.platform.tracking.domain.repositories.EquipmentTelemetryRepository;
import com.iotech.qualitrack.platform.tracking.domain.repositories.EquipmentTelemetryStatusRepository;
import com.iotech.qualitrack.platform.tracking.domain.repositories.MeasurementRepository;
import com.iotech.qualitrack.platform.tracking.domain.repositories.TelemetryHistoryPointRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * Application service implementation for Tracking write use cases.
 *
 * @remarks
 * Coordinates telemetry ingestion, status updates, and history persistence by
 * using domain repositories. It returns application-level Result objects so
 * REST controllers can map outcomes consistently.
 */
@Service
public class TrackingCommandServiceImpl implements TrackingCommandService {

    private final MeasurementRepository measurementRepository;
    private final EquipmentTelemetryStatusRepository statusRepository;
    private final TelemetryHistoryPointRepository historyPointRepository;
    private final EquipmentTelemetryRepository equipmentTelemetryRepository;
    private final ApplicationEventPublisher eventPublisher;

    /**
     * Creates a new TrackingCommandServiceImpl.
     *
     * @param measurementRepository repository for telemetry measurements
     * @param statusRepository repository for equipment telemetry statuses
     * @param historyPointRepository repository for telemetry history points
     * @param equipmentTelemetryRepository repository for equipment telemetry aggregates
     * @param eventPublisher Spring application event publisher
     */
    public TrackingCommandServiceImpl(
            MeasurementRepository measurementRepository,
            EquipmentTelemetryStatusRepository statusRepository,
            TelemetryHistoryPointRepository historyPointRepository,
            EquipmentTelemetryRepository equipmentTelemetryRepository,
            ApplicationEventPublisher eventPublisher
    ) {
        this.measurementRepository = measurementRepository;
        this.statusRepository = statusRepository;
        this.historyPointRepository = historyPointRepository;
        this.equipmentTelemetryRepository = equipmentTelemetryRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Result<Long, ApplicationError> handle(RecordMeasurementCommand command) {
        try {
            ensureTelemetryAggregateExists(command.equipmentId());

            var measurement = Measurement.record(
                    command.equipmentId(),
                    command.parameterName(),
                    command.value(),
                    command.unit(),
                    command.timestamp()
            );

            var savedMeasurement = measurementRepository.save(measurement);

            eventPublisher.publishEvent(new MeasurementRecordedEvent(
                    savedMeasurement.getId(),
                    savedMeasurement.getEquipmentId(),
                    savedMeasurement.getParameterName(),
                    savedMeasurement.getValue(),
                    savedMeasurement.getUnit(),
                    savedMeasurement.getTimestamp()
            ));

            return Result.success(savedMeasurement.getId());
        } catch (IllegalArgumentException exception) {
            return Result.failure(ApplicationError.validationError("measurement", exception.getMessage()));
        } catch (Exception exception) {
            return Result.failure(ApplicationError.unexpected(
                    "record measurement",
                    exception.getMessage()
            ));
        }
    }

    @Override
    public Result<Long, ApplicationError> handle(RecordTelemetryHistoryPointCommand command) {
        try {
            ensureTelemetryAggregateExists(command.equipmentId());

            var historyPoint = TelemetryHistoryPoint.record(
                    command.equipmentId(),
                    command.parameterName(),
                    command.recordedValue(),
                    command.timestamp(),
                    command.isAnomaly()
            );

            var savedHistoryPoint = historyPointRepository.save(historyPoint);

            eventPublisher.publishEvent(new TelemetryHistoryPointRecordedEvent(
                    savedHistoryPoint.getId(),
                    savedHistoryPoint.getEquipmentId(),
                    savedHistoryPoint.getParameterName(),
                    savedHistoryPoint.getRecordedValue(),
                    savedHistoryPoint.getTimestamp(),
                    savedHistoryPoint.getIsAnomaly()
            ));

            if (Boolean.TRUE.equals(savedHistoryPoint.getIsAnomaly())) {
                eventPublisher.publishEvent(new TelemetryAnomalyDetectedEvent(
                        savedHistoryPoint.getId(),
                        savedHistoryPoint.getEquipmentId(),
                        savedHistoryPoint.getParameterName(),
                        savedHistoryPoint.getRecordedValue(),
                        savedHistoryPoint.getTimestamp()
                ));
            }

            return Result.success(savedHistoryPoint.getId());
        } catch (IllegalArgumentException exception) {
            return Result.failure(ApplicationError.validationError("telemetry history point", exception.getMessage()));
        } catch (Exception exception) {
            return Result.failure(ApplicationError.unexpected(
                    "record telemetry history point",
                    exception.getMessage()
            ));
        }
    }

    @Override
    public Result<Long, ApplicationError> handle(UpdateEquipmentTelemetryStatusCommand command) {
        try {
            ensureTelemetryAggregateExists(command.equipmentId());

            var status = EquipmentTelemetryStatus.update(
                    command.equipmentId(),
                    command.isOnline(),
                    command.currentStatus(),
                    command.lastHeartbeat()
            );

            var savedStatus = statusRepository.save(status);

            eventPublisher.publishEvent(new EquipmentTelemetryStatusUpdatedEvent(
                    savedStatus.getId(),
                    savedStatus.getEquipmentId(),
                    savedStatus.getIsOnline(),
                    savedStatus.getCurrentStatus(),
                    savedStatus.getLastHeartbeat()
            ));

            return Result.success(savedStatus.getId());
        } catch (IllegalArgumentException exception) {
            return Result.failure(ApplicationError.validationError("equipment telemetry status", exception.getMessage()));
        } catch (Exception exception) {
            return Result.failure(ApplicationError.unexpected(
                    "update equipment telemetry status",
                    exception.getMessage()
            ));
        }
    }

    private void ensureTelemetryAggregateExists(Long equipmentId) {
        if (!equipmentTelemetryRepository.existsByEquipmentId(equipmentId)) {
            equipmentTelemetryRepository.save(
                    com.iotech.qualitrack.platform.tracking.domain.model.aggregates.EquipmentTelemetry
                            .createForEquipment(equipmentId)
            );
        }
    }
}