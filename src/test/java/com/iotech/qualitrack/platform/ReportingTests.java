package com.iotech.qualitrack.platform;

import com.iotech.qualitrack.platform.batch.domain.model.aggregates.Batch;
import com.iotech.qualitrack.platform.batch.domain.model.valueobjects.BatchStatus;
import com.iotech.qualitrack.platform.batch.domain.repositories.BatchRepository;
import com.iotech.qualitrack.platform.ca.domain.repositories.DeviationAlertRepository;
import com.iotech.qualitrack.platform.equipment.domain.model.entities.BpmParameterConfig;
import com.iotech.qualitrack.platform.equipment.domain.model.valueobjects.CriticalVariable;
import com.iotech.qualitrack.platform.equipment.domain.repositories.*;
import com.iotech.qualitrack.platform.ra.application.internal.outboundservices.acl.RaOperationalDataService;
import com.iotech.qualitrack.platform.ra.domain.model.valueobjects.ReportFormat;
import com.iotech.qualitrack.platform.ra.infrastructure.documents.ReportDocumentWriterImpl;
import com.iotech.qualitrack.platform.tracking.domain.model.entities.Measurement;
import com.iotech.qualitrack.platform.tracking.domain.repositories.MeasurementRepository;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.jupiter.api.Test;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.IntStream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ReportingTests {
    private final EquipmentRepository equipment = mock(EquipmentRepository.class);
    private final BatchRepository batches = mock(BatchRepository.class);
    private final DeviationAlertRepository alerts = mock(DeviationAlertRepository.class);
    private final MeasurementRepository measurements = mock(MeasurementRepository.class);
    private final BpmParameterConfigRepository configs = mock(BpmParameterConfigRepository.class);
    private final RaOperationalDataService data = new RaOperationalDataService(equipment, batches, alerts, measurements, configs,
            mock(com.iotech.qualitrack.platform.batch.domain.repositories.RawMaterialUsageRepository.class),
            mock(com.iotech.qualitrack.platform.laboratory.domain.repositories.LaboratoryRepository.class),
            mock(MaintenanceRepository.class));

    @Test void emptyRepositoryProducesNoInventedMetricOrHealthScore() {
        var dashboard = data.dashboard(47L);
        assertThat(dashboard.getMetrics()).isEmpty();
        assertThat(dashboard.getOverallHealthScore()).isNull();
        assertThat(dashboard.getLaboratoryId()).isEqualTo(47L);
    }

    @Test void countsUseOnlyTheRequestedLaboratoryRecords() {
        var released = mock(Batch.class);
        when(released.getStatus()).thenReturn(BatchStatus.RELEASED);
        var pending = mock(Batch.class);
        when(pending.getStatus()).thenReturn(BatchStatus.PENDING);
        when(batches.findAllByLabId(47L)).thenReturn(List.of(released, pending));
        var dashboard = data.dashboard(47L);
        assertThat(dashboard.getMetrics()).extracting(metric -> metric.getValue()).containsExactly(2.0, 1.0);
        assertThat(dashboard.getMetrics()).allMatch(metric -> metric.getTargetValue() == null);
        verify(batches).findAllByLabId(47L);
        verify(alerts, never()).findAll();
    }

    @Test void trendsUseRecordedValuesAndActualConfiguredLimits() {
        when(configs.findAllByEquipmentId(23L)).thenReturn(List.of(
                new BpmParameterConfig(9L, 23L, new CriticalVariable("Temperature"), 18.0, 24.0, "C")));
        when(measurements.findLatestByEquipmentId(23L)).thenReturn(List.of(
                Measurement.record(23L, "Temperature", 25.5, "C", "2026-08-02T10:00:00"),
                Measurement.record(23L, "Temperature", 20.0, "C", "2026-08-01T10:00:00"),
                Measurement.record(23L, "Temperature", 72.0, "F", "2026-08-01T11:00:00")));
        var trend = data.trend(23L, "Temperature");
        assertThat(trend.getDataPoints()).extracting(point -> point.getRecordedValue()).containsExactly(20.0, 25.5);
        assertThat(trend.getDataPoints()).allMatch(point -> point.getUpperThreshold() == 24.0 && point.getLowerThreshold() == 18.0);
    }

    @Test void noConfigurationDoesNotCreateFakeTrendPoints() {
        assertThat(data.trend(23L, "Temperature").getDataPoints()).isEmpty();
    }

    @Test void pdfIsReadableMultipageAndDoesNotDropTheLastRow() throws Exception {
        var rows = IntStream.range(0, 120).mapToObj(index -> List.of("Record " + index, "Temperatura y validación")).toList();
        var bytes = new ReportDocumentWriterImpl().write(ReportFormat.PDF, "QualiTrack", rows);
        try (var pdf = Loader.loadPDF(bytes)) {
            assertThat(pdf.getNumberOfPages()).isGreaterThan(1);
            assertThat(new PDFTextStripper().getText(pdf)).contains("Record 119", "validación");
        }
    }

    @Test void csvEscapesQuotesCommasAndSpreadsheetFormulas() {
        var bytes = new ReportDocumentWriterImpl().write(ReportFormat.CSV, "Test",
                List.of(List.of("label", "one,\"two\""), List.of("formula", "=1+2")));
        assertThat(new String(bytes, StandardCharsets.UTF_8)).contains("\"one,\"\"two\"\"\"", "\"'=1+2\"");
    }
}
