package com.iotech.qualitrack.platform.ra.infrastructure.persistence.jpa.converters;

import com.iotech.qualitrack.platform.ra.domain.model.valueobjects.KpiMetricStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converts the {@link KpiMetricStatus} enum between its domain representation
 * and the persistence column value (String).
 */
@Converter(autoApply = true)
public class KpiMetricStatusPersistenceConverter implements AttributeConverter<KpiMetricStatus, String> {

    @Override
    public String convertToDatabaseColumn(KpiMetricStatus attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public KpiMetricStatus convertToEntityAttribute(String dbData) {
        return dbData == null ? null : KpiMetricStatus.valueOf(dbData);
    }
}