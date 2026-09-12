package com.iotech.qualitrack.platform.ra.infrastructure.persistence.jpa.converters;

import com.iotech.qualitrack.platform.ra.domain.model.valueobjects.ReportType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converts the {@link ReportType} enum between its domain representation
 * and the persistence column value (String).
 */
@Converter(autoApply = true)
public class ReportTypePersistenceConverter implements AttributeConverter<ReportType, String> {

    @Override
    public String convertToDatabaseColumn(ReportType attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public ReportType convertToEntityAttribute(String dbData) {
        return dbData == null ? null : ReportType.valueOf(dbData);
    }
}