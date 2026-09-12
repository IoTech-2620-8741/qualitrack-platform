package com.iotech.qualitrack.platform.laboratory.infrastructure.persistence.jpa.converters;

import com.iotech.qualitrack.platform.laboratory.domain.model.valueobjects.LaboratoryStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converts the {@link LaboratoryStatus} enum between its domain representation
 * and the persistence column value (String).
 */
@Converter(autoApply = true)
public class LaboratoryStatusPersistenceConverter implements AttributeConverter<LaboratoryStatus, String> {

    @Override
    public String convertToDatabaseColumn(LaboratoryStatus attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public LaboratoryStatus convertToEntityAttribute(String dbData) {
        return dbData == null ? null : LaboratoryStatus.valueOf(dbData);
    }
}