package com.iotech.qualitrack.platform.laboratory.infrastructure.persistence.jpa.entities;

import com.iotech.qualitrack.platform.laboratory.domain.model.valueobjects.LaboratoryStatus;
import com.iotech.qualitrack.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "laboratories")
@Getter
@Setter
@NoArgsConstructor
public class LaboratoryPersistenceEntity extends AuditableAbstractPersistenceEntity {

    // El ID numérico (Long) se hereda de AuditableAbstractPersistenceEntity.
    // El antiguo String 'domainId' fue removido para unificar los IDs con el frontend.

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 20, unique = true)
    private String ruc;

    /**
     * Mapeado automáticamente por LaboratoryAddressPersistenceConverter (gracias al autoApply = true).
     */
    @Column(nullable = false, length = 255)
    private String address;

    @Column(nullable = false, length = 50)
    private String phone;

    /**
     * Representa la lista de marcos regulatorios (ej. "DIGEMID", "ISO-9001").
     * Se mapea a una tabla hija independiente en la base de datos.
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "laboratory_regulations", joinColumns = @JoinColumn(name = "laboratory_id"))
    @Column(name = "regulation")
    private List<String> applicableRegulations = new ArrayList<>();

    /**
     * Convertido automáticamente por LaboratoryStatusPersistenceConverter.
     */
    @Column(nullable = false)
    private LaboratoryStatus status;
}