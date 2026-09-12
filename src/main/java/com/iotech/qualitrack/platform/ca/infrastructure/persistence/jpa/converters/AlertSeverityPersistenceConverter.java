package com.iotech.qualitrack.platform.ca.infrastructure.persistence.jpa.converters;

import com.iotech.qualitrack.platform.ca.domain.model.valueobjects.AlertSeverity;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converts the {@link AlertSeverity} enum between its domain representation
 * and the persistence column value (String).
 */
@Converter(autoApply = true)
public class AlertSeverityPersistenceConverter implements AttributeConverter<AlertSeverity, String> {

    @Override
    public String convertToDatabaseColumn(AlertSeverity attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public AlertSeverity convertToEntityAttribute(String dbData) {
        return dbData == null ? null : AlertSeverity.valueOf(dbData);
    }
}