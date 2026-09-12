package com.iotech.qualitrack.platform.subscription.infrastructure.persistence.jpa.converters;

import com.iotech.qualitrack.platform.subscription.domain.model.valueobjects.BillingCycle;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * JPA converter for BillingCycle.
 */
@Converter(autoApply = true)
public class BillingCyclePersistenceConverter implements AttributeConverter<BillingCycle, String> {

    @Override
    public String convertToDatabaseColumn(BillingCycle attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public BillingCycle convertToEntityAttribute(String dbData) {
        return dbData == null ? null : BillingCycle.valueOf(dbData);
    }
}