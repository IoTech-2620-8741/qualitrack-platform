package com.iotech.qualitrack.platform.ra.infrastructure.documents;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/** Shared paginated PDF layout for operational reports; contains no business data queries. */
abstract class AbstractReportPdfRenderer {
    protected static final float MARGIN = 40;
    protected static final float WIDTH = PDRectangle.A4.getWidth() - 2 * MARGIN;
    protected static final float BOTTOM = PDRectangle.A4.getHeight() - 62;
    protected static final Color INK = new Color(30, 48, 59);
    protected static final Color TEAL = new Color(0, 112, 119);
    protected static final Color MUTED = new Color(92, 109, 119);
    protected static final Color LINE = new Color(217, 228, 231);
    protected static final Color LIGHT = new Color(243, 247, 248);
    protected static final Color RED = new Color(185, 49, 64);
    protected static final Color AMBER = new Color(178, 111, 15);
    protected static final Color GREEN = new Color(31, 126, 99);
    protected final PDType1Font regular = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
    protected final PDType1Font bold = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
    private PDDocument document;
    private PDPageContentStream stream;
    private String reportTitle;
    private String reportReference;
    protected float y;

    @FunctionalInterface
    protected interface Contents { void draw() throws IOException; }

    protected byte[] renderDocument(String title, String reference, Contents contents) {
        reportTitle = title;
        reportReference = reference;
        try (var pdf = new PDDocument(); var output = new ByteArrayOutputStream()) {
            document = pdf;
            document.getDocumentInformation().setTitle("QualiTrack - " + title + " / " + value(reference));
            document.getDocumentInformation().setAuthor("IoTech / QualiTrack");
            newPage();
            contents.draw();
            stream.close();
            stream = null;
            footers();
            document.save(output);
            return output.toByteArray();
        } catch (IOException exception) {
            throw new IllegalStateException("Report document could not be generated", exception);
        } finally {
            if (stream != null) {
                try { stream.close(); } catch (IOException ignored) { /* Document is closing. */ }
            }
        }
    }

    protected record Metric(String label, String value, Color color) {}

    protected void metrics(List<Metric> metrics) throws IOException {
        float cellWidth = WIDTH / metrics.size();
        var lines = new ArrayList<List<String>>();
        for (var metric : metrics) lines.add(wrap(metric.value(), cellWidth - 24, 12, bold));
        float height = Math.max(60, 39 + lines.stream().mapToInt(List::size).max().orElse(1) * 14);
        ensure(height + 9);
        rectangle(MARGIN, y, WIDTH, height, LIGHT);
        for (int i = 0; i < metrics.size(); i++) {
            float x = MARGIN + i * cellWidth + 12;
            text(metrics.get(i).label(), x, y + 10, 8, bold, MUTED);
            for (int j = 0; j < lines.get(i).size(); j++) {
                text(lines.get(i).get(j), x, y + 27 + j * 14, 12, bold, metrics.get(i).color());
            }
        }
        y += height + 9;
    }

    protected void countChart(String title, java.util.Map<String, Long> counts, Color color) throws IOException {
        ensure(66);
        text(title, MARGIN, y, 9, bold, MUTED);
        y += 23;
        long maximum = counts.values().stream().mapToLong(Long::longValue).max().orElse(0);
        for (var entry : counts.entrySet()) {
            var labels = wrap(human(entry.getKey()), 145, 9, regular);
            float height = Math.max(25, labels.size() * 12 + 8);
            ensure(height);
            for (int i = 0; i < labels.size(); i++) text(labels.get(i), MARGIN, y + i * 12, 9, regular, INK);
            rectangle(MARGIN + 156, y + 2, WIDTH - 195, 9, LIGHT);
            if (maximum > 0 && entry.getValue() > 0) {
                rectangle(MARGIN + 156, y + 2, (WIDTH - 195) * entry.getValue() / maximum, 9, color);
            }
            text(entry.getValue().toString(), MARGIN + WIDTH - 30, y, 9, bold, INK);
            y += height;
        }
        y += 7;
    }

    protected void countChartPair(String firstTitle, java.util.Map<String, Long> first,
            String secondTitle, java.util.Map<String, Long> second) throws IOException {
        float height = 33 + 23 * Math.max(first.size(), second.size());
        ensure(height);
        float groupWidth = (WIDTH - 24) / 2;
        var groups = List.of(first, second);
        var titles = List.of(firstTitle, secondTitle);
        for (int group = 0; group < 2; group++) {
            float x = MARGIN + group * (groupWidth + 24);
            text(titles.get(group), x, y, 8, bold, MUTED);
            long maximum = groups.get(group).values().stream().mapToLong(Long::longValue).max().orElse(0);
            int index = 0;
            for (var entry : groups.get(group).entrySet()) {
                float top = y + 21 + index++ * 23;
                text(human(entry.getKey()), x, top, 8, regular, INK);
                float barWidth = groupWidth - 123;
                rectangle(x + 88, top + 1, barWidth, 9, LIGHT);
                Color color = switch (entry.getKey()) {
                    case "CRITICAL", "UNRESOLVED" -> RED;
                    case "WARNING", "ACKNOWLEDGED" -> AMBER;
                    case "RESOLVED" -> GREEN;
                    default -> TEAL;
                };
                if (maximum > 0) rectangle(x + 88, top + 1, barWidth * entry.getValue() / maximum, 9, color);
                text(entry.getValue().toString(), x + groupWidth - 23, top, 9, bold, INK);
            }
        }
        y += height;
    }

    protected void newPage() throws IOException {
        if (stream != null) stream.close();
        var page = new PDPage(PDRectangle.A4);
        document.addPage(page);
        stream = new PDPageContentStream(document, page);
        text("QualiTrack", MARGIN, 28, 24, bold, INK);
        text("IoTech / QUALITY RECORD", MARGIN + WIDTH - 149, 37, 9, bold, TEAL);
        text(reportTitle, MARGIN, 62, 18, bold, INK);
        var reference = wrap(value(reportReference), WIDTH, 10, regular);
        y = 90;
        for (var line : reference) { text(line, MARGIN, y, 10, regular, MUTED); y += 13; }
        rule(MARGIN, y + 5, WIDTH, TEAL);
        y += 21;
    }

    protected void ensure(float height) throws IOException {
        if (y + height > BOTTOM) newPage();
    }

    protected void section(String number, String title) throws IOException {
        ensure(80);
        y += 9;
        text(number, MARGIN, y + 2, 9, bold, TEAL);
        text(title, MARGIN + 24, y, 12, bold, INK);
        y += 24;
    }

    protected void table(List<String> headings, List<List<String>> rows, float[] fractions) throws IOException {
        ensure(54);
        headerRow(headings, fractions);
        for (int rowIndex = 0; rowIndex < rows.size(); rowIndex++) {
            var cells = new ArrayList<List<String>>();
            for (int column = 0; column < headings.size(); column++) {
                cells.add(wrap(value(rows.get(rowIndex).get(column)), WIDTH * fractions[column] - 16, 9, regular));
            }
            int lineCount = cells.stream().mapToInt(List::size).max().orElse(1);
            int offset = 0;
            // Split oversized rows across pages, repeating column headings without dropping cell text.
            while (offset < lineCount) {
                if (BOTTOM - y < 29) { newPage(); headerRow(headings, fractions); }
                int available = Math.max(1, (int) ((BOTTOM - y - 14) / 13));
                if (offset == 0 && lineCount > available && lineCount * 13 + 14 < BOTTOM - 160) {
                    newPage();
                    headerRow(headings, fractions);
                    available = Math.max(1, (int) ((BOTTOM - y - 14) / 13));
                }
                int take = Math.min(available, lineCount - offset);
                float height = take * 13 + 14;
                rectangle(MARGIN, y, WIDTH, height, rowIndex % 2 == 0 ? LIGHT : Color.WHITE);
                float x = MARGIN;
                for (int column = 0; column < cells.size(); column++) {
                    var lines = cells.get(column);
                    for (int line = offset; line < Math.min(offset + take, lines.size()); line++) {
                        text(lines.get(line), x + 8, y + 7 + (line - offset) * 13, 9, regular, INK);
                    }
                    x += WIDTH * fractions[column];
                }
                rule(MARGIN, y + height, WIDTH, LINE);
                y += height;
                offset += take;
            }
        }
        y += 8;
    }

    protected void headerRow(List<String> headings, float[] fractions) throws IOException {
        var lines = new ArrayList<List<String>>();
        for (int i = 0; i < headings.size(); i++) lines.add(wrap(headings.get(i), WIDTH * fractions[i] - 16, 8, bold));
        float height = lines.stream().mapToInt(List::size).max().orElse(1) * 11 + 14;
        rectangle(MARGIN, y, WIDTH, height, TEAL);
        float x = MARGIN;
        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < lines.get(i).size(); j++) text(lines.get(i).get(j), x + 8, y + 7 + j * 11, 8, bold, Color.WHITE);
            x += WIDTH * fractions[i];
        }
        y += height;
    }

    protected void paragraph(String value, Color color) throws IOException {
        for (var line : wrap(value, WIDTH, 9, regular)) {
            ensure(14);
            text(line, MARGIN, y, 9, regular, color);
            y += 13;
        }
        y += 7;
    }

    protected void footers() throws IOException {
        int pageNumber = 0;
        for (var page : document.getPages()) {
            try (var footer = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true)) {
                stream = footer;
                rule(MARGIN, PDRectangle.A4.getHeight() - 43, WIDTH, LINE);
                text("QualiTrack / IoTech - Operational snapshot", MARGIN, PDRectangle.A4.getHeight() - 32, 8, regular, MUTED);
                text("Page " + (++pageNumber) + " / " + document.getNumberOfPages(), MARGIN + WIDTH - 60,
                        PDRectangle.A4.getHeight() - 32, 8, regular, MUTED);
            }
        }
        stream = null;
    }

    protected void text(String text, float x, float top, float size, PDType1Font font, Color color) throws IOException {
        stream.beginText();
        stream.setFont(font, size);
        stream.setNonStrokingColor(color);
        stream.newLineAtOffset(x, PDRectangle.A4.getHeight() - top - size);
        stream.showText(safe(text, font));
        stream.endText();
    }

    protected void rectangle(float x, float top, float width, float height, Color color) throws IOException {
        stream.setNonStrokingColor(color);
        stream.addRect(x, PDRectangle.A4.getHeight() - top - height, width, height);
        stream.fill();
    }

    protected void rule(float x, float top, float width, Color color) throws IOException {
        stream.setStrokingColor(color);
        stream.setLineWidth(0.7f);
        stream.moveTo(x, PDRectangle.A4.getHeight() - top);
        stream.lineTo(x + width, PDRectangle.A4.getHeight() - top);
        stream.stroke();
    }

    protected List<String> wrap(String input, float maxWidth, float size, PDType1Font font) throws IOException {
        var result = new ArrayList<String>();
        for (var paragraph : value(input).split("\\R", -1)) {
            var line = new StringBuilder();
            for (var word : safe(paragraph, font).split("\\s+")) {
                if (!line.isEmpty() && width(line + " " + word, size, font) <= maxWidth) { line.append(' ').append(word); continue; }
                if (!line.isEmpty()) { result.add(line.toString()); line.setLength(0); }
                for (char character : word.toCharArray()) {
                    if (!line.isEmpty() && width(line.toString() + character, size, font) > maxWidth) {
                        result.add(line.toString()); line.setLength(0);
                    }
                    line.append(character);
                }
            }
            result.add(line.toString());
        }
        return result;
    }

    protected float width(String text, float size, PDType1Font font) throws IOException {
        return font.getStringWidth(safe(text, font)) / 1000 * size;
    }

    protected String safe(String input, PDType1Font font) throws IOException {
        var output = new StringBuilder();
        for (int point : value(input).codePoints().toArray()) {
            String character = Character.isISOControl(point) ? " " : new String(Character.toChars(point));
            try { font.encode(character); }
            catch (IllegalArgumentException unsupported) { character = "[U+" + Integer.toHexString(point) + "]"; }
            output.append(character);
        }
        return output.toString();
    }

    protected static String value(Object value) { return value == null || value.toString().isBlank() ? "Not recorded" : value.toString(); }
    protected static String number(Double value) { return value == null ? "Not recorded" : java.math.BigDecimal.valueOf(value).stripTrailingZeros().toPlainString(); }
    protected static String human(String value) {
        return value(value).toLowerCase(Locale.ROOT).replace('_', ' ');
    }
    protected static String date(String value) {
        if (value == null || value.isBlank()) return "Not recorded";
        try { return LocalDate.parse(value.substring(0, 10)).format(DateTimeFormatter.ofPattern("dd MMM uuuu", Locale.ENGLISH)); }
        catch (RuntimeException invalid) { return value; }
    }
    protected static String timestamp(String value) {
        if (value == null || value.isBlank()) return "Not recorded";
        var format = DateTimeFormatter.ofPattern("dd MMM uuuu HH:mm 'UTC'", Locale.ENGLISH);
        try { return OffsetDateTime.parse(value).withOffsetSameInstant(ZoneOffset.UTC).format(format); }
        catch (DateTimeParseException noOffset) {
            try { return LocalDateTime.parse(value).format(DateTimeFormatter.ofPattern("dd MMM uuuu HH:mm", Locale.ENGLISH)); }
            catch (DateTimeParseException invalid) { return value; }
        }
    }
    protected static Color statusColor(String status) {
        if ("RELEASED".equals(status)) return GREEN;
        if ("REJECTED".equals(status)) return RED;
        return AMBER;
    }
}
