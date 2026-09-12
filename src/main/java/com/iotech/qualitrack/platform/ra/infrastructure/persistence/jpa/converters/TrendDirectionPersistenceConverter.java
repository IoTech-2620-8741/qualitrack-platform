package com.iotech.qualitrack.platform.ra.infrastructure.persistence.jpa.converters;

import com.iotech.qualitrack.platform.ra.domain.model.valueobjects.TrendDirection;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converts the {@link TrendDirection} enum between its domain representation
 * and the persistence column value (String).
 */
@Converter(autoApply = true)
public class TrendDirectionPersistenceConverter implements AttributeConverter<TrendDirection, String> {

    @Override
    public String convertToDatabaseColumn(TrendDirection attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public TrendDirection convertToEntityAttribute(String dbData) {
        return dbData == null ? null : TrendDirection.valueOf(dbData);
    }
}