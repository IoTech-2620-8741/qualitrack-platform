package com.iotech.qualitrack.platform.laboratory.infrastructure.persistence.jpa.converters;

import com.iotech.qualitrack.platform.laboratory.domain.model.entities.LaboratoryAddress;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converts the {@link LaboratoryAddress} domain entity into a single String
 * column for the database.
 *
 * <p>By using this converter, the infrastructure layer treats the address
 * purely as a Value Object (persisted as a single string column in the
 * laboratory table), seamlessly bridging the gap between the rich domain
 * model and the flat database schema.</p>
 */
@Converter(autoApply = true)
public class LaboratoryAddressPersistenceConverter implements AttributeConverter<LaboratoryAddress, String> {

    @Override
    public String convertToDatabaseColumn(LaboratoryAddress attribute) {
        // Extrae el string unificado para guardarlo en la base de datos
        return attribute == null ? null : attribute.getAddress();
    }

    @Override
    public LaboratoryAddress convertToEntityAttribute(String dbData) {
        // Reconstruye el objeto de dominio a partir del string de la base de datos.
        // El ID interno quedará en null, lo cual es correcto ya que la base de datos
        // no le asignará un ID independiente a esta columna.
        return dbData == null ? null : new LaboratoryAddress(dbData);
    }
}