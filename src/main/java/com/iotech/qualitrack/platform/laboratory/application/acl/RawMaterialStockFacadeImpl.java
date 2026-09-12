package com.iotech.qualitrack.platform.laboratory.application.acl;

import com.iotech.qualitrack.platform.laboratory.domain.model.events.RawMaterialLowStockEvent;
import com.iotech.qualitrack.platform.laboratory.domain.repositories.RawMaterialRepository;
import com.iotech.qualitrack.platform.laboratory.interfaces.acl.RawMaterialStockFacade;
import com.iotech.qualitrack.platform.shared.application.result.ApplicationError;
import com.iotech.qualitrack.platform.shared.application.result.ApplicationException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class RawMaterialStockFacadeImpl implements RawMaterialStockFacade {
    private final RawMaterialRepository materials;
    private final ApplicationEventPublisher events;

    public RawMaterialStockFacadeImpl(RawMaterialRepository materials, ApplicationEventPublisher events) {
        this.materials = materials;
        this.events = events;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public StockConsumption consume(Long materialId, Long laboratoryId, BigDecimal quantity, String unit) {
        var material = materials.findByIdForUpdate(materialId).orElseThrow(() ->
                new ApplicationException(ApplicationError.notFound("RawMaterial", materialId)));
        if (!material.getLaboratoryId().equals(laboratoryId)) {
            throw new ApplicationException(ApplicationError.validationError("RawMaterial", "Material and batch must belong to the same laboratory"));
        }
        var before = material.getCurrentStock();
        try {
            material.consumeStock(quantity, unit);
        } catch (IllegalStateException exception) {
            throw new ApplicationException(ApplicationError.conflict("RawMaterialStock", exception.getMessage()));
        }
        materials.save(material);
        if (before.compareTo(material.getMinimumThreshold()) > 0
                && material.getCurrentStock().compareTo(material.getMinimumThreshold()) <= 0) {
            events.publishEvent(new RawMaterialLowStockEvent(materialId, laboratoryId, material.getName(),
                    material.getCurrentStock(), material.getMinimumThreshold()));
        }
        return new StockConsumption(material.getName(), material.getUnit(), before, material.getCurrentStock());
    }
}
