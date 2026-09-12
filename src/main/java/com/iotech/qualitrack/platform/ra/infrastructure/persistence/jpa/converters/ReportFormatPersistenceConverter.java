package com.iotech.qualitrack.platform.ra.infrastructure.persistence.jpa.converters;

import com.iotech.qualitrack.platform.ra.domain.model.valueobjects.ReportFormat;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converts the {@link ReportFormat} enum between its domain representation
 * and the persistence column value (String).
 */
@Converter(autoApply = true)
public class ReportFormatPersistenceConverter implements AttributeConverter<ReportFormat, String> {

    @Override
    public String convertToDatabaseColumn(ReportFormat attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public ReportFormat convertToEntityAttribute(String dbData) {
        return dbData == null ? null : ReportFormat.valueOf(dbData);
    }
}