package com.iotech.qualitrack.platform.subscription.infrastructure.persistence.jpa.converters;

import com.iotech.qualitrack.platform.subscription.domain.model.valueobjects.PlanCode;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * JPA converter for PlanCode.
 */
@Converter(autoApply = true)
public class PlanCodePersistenceConverter implements AttributeConverter<PlanCode, String> {

    @Override
    public String convertToDatabaseColumn(PlanCode attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public PlanCode convertToEntityAttribute(String dbData) {
        return dbData == null ? null : PlanCode.valueOf(dbData);
    }
}