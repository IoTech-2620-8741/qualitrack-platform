package com.iotech.qualitrack.platform.iam.infrastructure.persistence.jpa.converters;

import com.iotech.qualitrack.platform.iam.domain.model.valueobjects.UserStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * JPA converter for IAM user status values.
 */
@Converter(autoApply = true)
public class UserStatusPersistenceConverter implements AttributeConverter<UserStatus, String> {

    @Override
    public String convertToDatabaseColumn(UserStatus attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public UserStatus convertToEntityAttribute(String dbData) {
        return dbData == null ? null : UserStatus.valueOf(dbData);
    }
}