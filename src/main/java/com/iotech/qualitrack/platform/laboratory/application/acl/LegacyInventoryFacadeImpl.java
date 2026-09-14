package com.iotech.qualitrack.platform.laboratory.application.acl;

import com.iotech.qualitrack.platform.laboratory.interfaces.acl.LegacyInventoryFacade;
import com.iotech.qualitrack.platform.laboratory.domain.repositories.RawMaterialRepository;
import com.iotech.qualitrack.platform.laboratory.domain.model.aggregates.RawMaterial;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class LegacyInventoryFacadeImpl implements LegacyInventoryFacade {
    private final RawMaterialRepository repository;
    public LegacyInventoryFacadeImpl(RawMaterialRepository repository) { this.repository = repository; }
    public List<Material> materials(Long lab) {
        return repository.findAllByLaboratoryId(lab).stream().map(this::view).toList();
    }
    @Transactional(propagation = org.springframework.transaction.annotation.Propagation.MANDATORY)
    public Material lock(Long lab, Long id) {
        return repository.findByIdForUpdate(id).filter(material -> lab.equals(material.getLaboratoryId()))
            .map(this::view).orElseThrow(() -> new IllegalArgumentException("Legacy material not found"));
    }
    private Material view(RawMaterial m) {
        return new Material(m.getId(), m.getCode(), m.getName(), m.getUnit(), m.getMinimumThreshold(),
            m.getSupplier(), m.getBatchNumber(), m.getExpirationDate(), m.getCurrentStock());
    }
}
