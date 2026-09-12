package com.iotech.qualitrack.platform.laboratory.infrastructure.persistence.jpa.converters;

import com.iotech.qualitrack.platform.laboratory.domain.model.valueobjects.Regulation;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converts the {@link Regulation} value object between the domain model
 * and its persistence column value (String).
 */
@Converter(autoApply = true)
public class RegulationPersistenceConverter implements AttributeConverter<Regulation, String> {

    @Override
    public String convertToDatabaseColumn(Regulation attribute) {
        return attribute == null ? null : attribute.code();
    }

    @Override
    public Regulation convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new Regulation(dbData);
    }
}