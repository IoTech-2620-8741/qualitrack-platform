package com.iotech.qualitrack.platform.subscription.infrastructure.persistence.jpa.converters;

import com.iotech.qualitrack.platform.subscription.domain.model.valueobjects.PaymentStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * JPA converter for PaymentStatus.
 */
@Converter(autoApply = true)
public class PaymentStatusPersistenceConverter implements AttributeConverter<PaymentStatus, String> {

    @Override
    public String convertToDatabaseColumn(PaymentStatus attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public PaymentStatus convertToEntityAttribute(String dbData) {
        return dbData == null ? null : PaymentStatus.valueOf(dbData);
    }
}