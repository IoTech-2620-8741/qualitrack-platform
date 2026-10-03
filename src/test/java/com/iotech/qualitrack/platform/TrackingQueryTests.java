package com.iotech.qualitrack.platform;

import com.iotech.qualitrack.platform.tracking.application.internal.outboundservices.acl.TrackingExternalEquipmentService;
import com.iotech.qualitrack.platform.tracking.application.internal.queryservices.TrackingQueryServiceImpl;
import com.iotech.qualitrack.platform.tracking.domain.model.entities.Measurement;
import com.iotech.qualitrack.platform.tracking.domain.model.queries.GetLatestMeasurementsQuery;
import com.iotech.qualitrack.platform.tracking.domain.model.valueobjects.DeviceConnection;
import com.iotech.qualitrack.platform.tracking.domain.model.valueobjects.DeviceConnectionStatus;
import com.iotech.qualitrack.platform.tracking.domain.model.valueobjects.ExpectedCommunicationPeriod;
import com.iotech.qualitrack.platform.tracking.domain.repositories.*;
import org.junit.jupiter.api.Test;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TrackingQueryTests {
    @Test void latestReadingsRetainDistinctParametersUnitsAndEquipment() {
        var repository = mock(MeasurementRepository.class);
        var service = new TrackingQueryServiceImpl(repository, mock(EquipmentTelemetryStatusRepository.class),
                mock(TelemetryHistoryPointRepository.class), mock(TrackingExternalEquipmentService.class),
                new ExpectedCommunicationPeriod(Duration.ofMinutes(5)), Clock.systemUTC());
        var temperature = Measurement.record(23L, "Temperature", 22.0, "C", "2026-09-01T12:00:00Z");
        var humidity = Measurement.record(23L, "Humidity", 60.0, "%", "2026-09-01T12:00:00Z");
        var differentUnit = Measurement.record(23L, "Temperature", 71.6, "F", "2026-09-01T12:00:00Z");
        var otherEquipment = Measurement.record(24L, "Temperature", 21.0, "C", "2026-09-01T12:00:00Z");
        var older = Measurement.record(23L, "Temperature", 20.0, "C", "2026-09-01T11:00:00Z");
        when(repository.findLatest()).thenReturn(List.of(temperature, humidity, differentUnit, otherEquipment, older));
        assertThat(service.handle(new GetLatestMeasurementsQuery(null)))
                .containsExactly(temperature, humidity, differentUnit, otherEquipment);
        when(repository.findLatestByEquipmentId(23L)).thenReturn(List.of(temperature, older));
        assertThat(service.handle(new GetLatestMeasurementsQuery(23L))).containsExactly(temperature);
        verify(repository).findLatestByEquipmentId(23L);
    }

    @Test
    void deviceRequiresReviewWhenSilentForLongerThanTheExpectedPeriod() {
        var period = new ExpectedCommunicationPeriod(Duration.ofMinutes(5));
        var now = Instant.parse("2026-10-03T15:00:00Z");

        assertThat(DeviceConnection.evaluate(7L, now.minusSeconds(300), now, period).status())
                .isEqualTo(DeviceConnectionStatus.CONNECTED);
        assertThat(DeviceConnection.evaluate(7L, now.minusSeconds(301), now, period).status())
                .isEqualTo(DeviceConnectionStatus.REQUIRES_REVIEW);
        assertThat(DeviceConnection.evaluate(7L, null, now, period).status())
                .isEqualTo(DeviceConnectionStatus.REQUIRES_REVIEW);
        assertThatThrownBy(() -> new ExpectedCommunicationPeriod(Duration.ZERO)).isInstanceOf(IllegalArgumentException.class);
    }
}
