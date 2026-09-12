package com.iotech.qualitrack.platform.subscription.infrastructure.persistence.jpa.converters;

import com.iotech.qualitrack.platform.subscription.domain.model.valueobjects.SubscriptionStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * JPA converter for SubscriptionStatus.
 */
@Converter(autoApply = true)
public class SubscriptionStatusPersistenceConverter implements AttributeConverter<SubscriptionStatus, String> {

    @Override
    public String convertToDatabaseColumn(SubscriptionStatus attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public SubscriptionStatus convertToEntityAttribute(String dbData) {
        return dbData == null ? null : SubscriptionStatus.valueOf(dbData);
    }
}