package com.iotech.qualitrack.platform.tracking.infrastructure.persistence.jpa.converters;

import com.iotech.qualitrack.platform.tracking.domain.model.valueobjects.TelemetryStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converts the {@link TelemetryStatus} enum between its domain representation
 * and its database column representation.
 */
@Converter(autoApply = true)
public class TelemetryStatusPersistenceConverter implements AttributeConverter<TelemetryStatus, String> {
    /**
     * Converts a telemetry status enum into a database column value.
     *
     * @param attribute the telemetry status enum
     * @return the database column value
     */
    @Override
    public String convertToDatabaseColumn(TelemetryStatus attribute) {
        return attribute == null ? null : attribute.name();
    }

    /**
     * Converts a database column value into a telemetry status enum.
     *
     * @param dbData the database column value
     * @return the telemetry status enum
     */
    @Override
    public TelemetryStatus convertToEntityAttribute(String dbData) {
        return dbData == null ? null : TelemetryStatus.valueOf(dbData);
    }
}