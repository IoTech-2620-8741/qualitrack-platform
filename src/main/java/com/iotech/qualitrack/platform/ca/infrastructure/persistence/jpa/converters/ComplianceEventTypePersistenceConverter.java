package com.iotech.qualitrack.platform.ca.infrastructure.persistence.jpa.converters;

import com.iotech.qualitrack.platform.ca.domain.model.valueobjects.ComplianceEventType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converts the {@link ComplianceEventType} enum between its domain representation
 * and the persistence column value (String).
 */
@Converter(autoApply = true)
public class ComplianceEventTypePersistenceConverter implements AttributeConverter<ComplianceEventType, String> {

    @Override
    public String convertToDatabaseColumn(ComplianceEventType attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public ComplianceEventType convertToEntityAttribute(String dbData) {
        return dbData == null ? null : ComplianceEventType.valueOf(dbData);
    }
}