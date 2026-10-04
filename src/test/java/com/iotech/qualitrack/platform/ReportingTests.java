package com.iotech.qualitrack.platform;

import com.iotech.qualitrack.platform.batch.domain.model.aggregates.Batch;
import com.iotech.qualitrack.platform.batch.domain.model.valueobjects.BatchStatus;
import com.iotech.qualitrack.platform.batch.domain.repositories.BatchRepository;
import com.iotech.qualitrack.platform.ca.domain.repositories.DeviationAlertRepository;
import com.iotech.qualitrack.platform.equipment.domain.repositories.*;
import com.iotech.qualitrack.platform.ra.application.internal.outboundservices.acl.RaOperationalDataService;
import com.iotech.qualitrack.platform.ra.domain.model.entities.DeviationTrend;
import com.iotech.qualitrack.platform.ra.domain.model.valueobjects.EnvironmentalReading;
import com.iotech.qualitrack.platform.ra.domain.model.valueobjects.MeasurementSummary;
import com.iotech.qualitrack.platform.ra.domain.model.valueobjects.ReportFormat;
import com.iotech.qualitrack.platform.ra.infrastructure.documents.ReportDocumentWriterImpl;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.jupiter.api.Test;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;
import java.util.stream.IntStream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ReportingTests {
    private final EquipmentRepository equipment = mock(EquipmentRepository.class);
    private final BatchRepository batches = mock(BatchRepository.class);
    private final DeviationAlertRepository alerts = mock(DeviationAlertRepository.class);
    private final BpmParameterConfigRepository configs = mock(BpmParameterConfigRepository.class);
    private final RaOperationalDataService data = new RaOperationalDataService(equipment, batches, alerts, configs,
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

    @Test void timeInRangeWeighsEachEvaluatedReadingUntilTheNextOne() {
        var start = Instant.parse("2026-08-01T10:00:00Z");
        var trend = DeviationTrend.fromReadings(4L, 12L, "TEMPERATURE", "°C", List.of(
                reading("CRITICAL", 33.0, start.plusSeconds(3000)),
                reading("NORMAL", 20.0, start),
                reading("WARNING", 27.0, start.plusSeconds(600)),
                reading(null, 26.0, start.plusSeconds(900)),
                reading("NORMAL", 22.0, start.plusSeconds(1200)),
                reading("NORMAL", null, start.plusSeconds(1300)),
                reading("NORMAL", 21.0, start.plusSeconds(2400))));
        // NORMAL 0-10 min, WARNING 10-20, NORMAL 20-50: 40 of 50 minutes in range.
        assertThat(trend.getTimeInRangePercent()).isEqualTo(80.0);
        assertThat(trend.getDeviationCount()).isEqualTo(2);
        assertThat(trend.getCriticalDeviationCount()).isEqualTo(1);
        assertThat(trend.getEvaluatedReadings()).isEqualTo(5);
        assertThat(trend.getDataPoints()).extracting(point -> point.getRecordedValue()).containsExactly(20.0, 27.0, 26.0, 22.0, 21.0, 33.0);
        assertThat(trend.getTrendDirection().name()).isEqualTo("INCREASING");
    }

    @Test void aPeriodThatStartsDeviatedCountsOnceAndFewReadingsHaveNoTimeInRange() {
        var start = Instant.parse("2026-08-01T10:00:00Z");
        var single = DeviationTrend.fromReadings(4L, 12L, "TEMPERATURE", "°C", List.of(reading("WARNING", 27.0, start)));
        assertThat(single.getTimeInRangePercent()).isNull();
        assertThat(single.getDeviationCount()).isEqualTo(1);
        var empty = DeviationTrend.fromReadings(4L, 12L, "TEMPERATURE", "°C", List.of());
        assertThat(empty.getDataPoints()).isEmpty();
        assertThat(empty.getTimeInRangePercent()).isNull();
        assertThat(empty.getDeviationCount()).isZero();
    }

    @Test void summariesAverageOnlyNumericReadingsOfTheSameDeviceMetricAndUnit() {
        var start = Instant.parse("2026-08-01T10:00:00Z");
        var summaries = MeasurementSummary.summarize(4L, List.of(
                new EnvironmentalReading(12L, "TEMPERATURE", 20.0, "°C", "NORMAL", start),
                new EnvironmentalReading(12L, "TEMPERATURE", 25.0, "°C", "WARNING", start.plusSeconds(60)),
                new EnvironmentalReading(12L, "TEMPERATURE", 26.0, "°C", null, start.plusSeconds(120)),
                new EnvironmentalReading(12L, "RFID_TAG", null, "tag", null, start),
                new EnvironmentalReading(11L, "AIR_QUALITY", 350.0, "ppm", "NORMAL", start)));
        assertThat(summaries).extracting(MeasurementSummary::metric).containsExactly("AIR_QUALITY", "TEMPERATURE");
        var temperature = summaries.get(1);
        assertThat(temperature.readings()).isEqualTo(3);
        assertThat(temperature.average()).isEqualTo(23.67);
        assertThat(temperature.minimum()).isEqualTo(20.0);
        assertThat(temperature.maximum()).isEqualTo(26.0);
        assertThat(temperature.lastMeasuredAt()).isEqualTo(start.plusSeconds(120));
    }

    private static EnvironmentalReading reading(String state, Double value, Instant at) {
        return new EnvironmentalReading(12L, "TEMPERATURE", value, "°C", state, at);
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
