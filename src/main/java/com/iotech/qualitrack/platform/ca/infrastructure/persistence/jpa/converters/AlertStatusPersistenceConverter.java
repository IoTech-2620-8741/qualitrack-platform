package com.iotech.qualitrack.platform.ca.infrastructure.persistence.jpa.converters;

import com.iotech.qualitrack.platform.ca.domain.model.valueobjects.AlertStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converts the {@link AlertStatus} enum between its domain representation
 * and the persistence column value (String).
 */
@Converter(autoApply = true)
public class AlertStatusPersistenceConverter implements AttributeConverter<AlertStatus, String> {

    @Override
    public String convertToDatabaseColumn(AlertStatus attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public AlertStatus convertToEntityAttribute(String dbData) {
        return dbData == null ? null : AlertStatus.valueOf(dbData);
    }
}