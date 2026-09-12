package com.iotech.qualitrack.platform.iam.infrastructure.persistence.jpa.converters;

import com.iotech.qualitrack.platform.iam.domain.model.valueobjects.Roles;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * JPA converter for IAM role names.
 */
@Converter(autoApply = true)
public class RolesPersistenceConverter implements AttributeConverter<Roles, String> {

    @Override
    public String convertToDatabaseColumn(Roles attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public Roles convertToEntityAttribute(String dbData) {
        return dbData == null ? null : Roles.valueOf(dbData);
    }
}