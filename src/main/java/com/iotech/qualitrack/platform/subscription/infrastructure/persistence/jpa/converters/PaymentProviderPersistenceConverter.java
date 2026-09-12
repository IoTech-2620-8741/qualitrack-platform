package com.iotech.qualitrack.platform.subscription.infrastructure.persistence.jpa.converters;

import com.iotech.qualitrack.platform.subscription.domain.model.valueobjects.PaymentProvider;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * JPA converter for PaymentProvider.
 */
@Converter(autoApply = true)
public class PaymentProviderPersistenceConverter implements AttributeConverter<PaymentProvider, String> {

    @Override
    public String convertToDatabaseColumn(PaymentProvider attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public PaymentProvider convertToEntityAttribute(String dbData) {
        return dbData == null ? null : PaymentProvider.valueOf(dbData);
    }
}