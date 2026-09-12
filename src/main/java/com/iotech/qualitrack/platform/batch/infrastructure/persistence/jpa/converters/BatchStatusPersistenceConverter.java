package com.iotech.qualitrack.platform.batch.infrastructure.persistence.jpa.converters;

import com.iotech.qualitrack.platform.batch.domain.model.valueobjects.BatchStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converts the {@link BatchStatus} enum between its domain representation
 * and the persistence column value (String).
 */
@Converter(autoApply = true)
public class BatchStatusPersistenceConverter implements AttributeConverter<BatchStatus, String> {

    @Override
    public String convertToDatabaseColumn(BatchStatus attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public BatchStatus convertToEntityAttribute(String dbData) {
        return dbData == null ? null : BatchStatus.valueOf(dbData);
    }
}