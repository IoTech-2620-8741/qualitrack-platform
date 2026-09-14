package com.iotech.qualitrack.platform.inventory.application.internal.queryservices;
import com.iotech.qualitrack.platform.inventory.application.queryservices.InventoryQueryService;
import com.iotech.qualitrack.platform.inventory.domain.model.queries.*;
import com.iotech.qualitrack.platform.inventory.domain.model.aggregates.RawMaterialBatch;
import com.iotech.qualitrack.platform.inventory.domain.model.entities.InventoryMovement;
import com.iotech.qualitrack.platform.inventory.domain.model.valueobjects.MaterialStockSummary;
import com.iotech.qualitrack.platform.inventory.domain.repositories.InventoryRepository;
import com.iotech.qualitrack.platform.shared.application.result.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.*;
import java.util.List;
import com.iotech.qualitrack.platform.laboratory.interfaces.acl.LegacyInventoryFacade;

@Service
@Transactional(readOnly = true)
public class InventoryQueryServiceImpl implements InventoryQueryService {
    private final InventoryRepository repository;
    private final Clock clock;
    private final LegacyInventoryFacade legacy;
    public InventoryQueryServiceImpl(InventoryRepository repository, Clock inventoryClock, LegacyInventoryFacade legacy) {
        this.repository = repository;
        this.clock = inventoryClock;
        this.legacy = legacy;
    }
    public List<MaterialStockSummary> handle(GetInventoryMaterialsQuery query) {
        var lab = query.laboratoryId();
        return repository.materials(lab).stream().map(material -> {
            var receipts = repository.receipts(lab, material.getId());
            return new MaterialStockSummary(material.getId(), lab, material.getCode(), material.getName(), material.getUnit(),
                material.getMinimumStock(), material.usableStock(receipts, LocalDate.now(clock)),
                receipts.stream().map(RawMaterialBatch::getAvailableAmount).reduce(BigDecimal.ZERO, BigDecimal::add),
                repository.legacyId(lab, material.getId()).orElse(null));
        }).toList();
    }
    public List<RawMaterialBatch> handle(GetMaterialReceiptsQuery query) {
        requireMaterial(query.laboratoryId(), query.materialId());
        return repository.receipts(query.laboratoryId(), query.materialId());
    }
    public List<InventoryMovement> handle(GetMaterialMovementsQuery query) {
        requireMaterial(query.laboratoryId(), query.materialId());
        return repository.movements(query.laboratoryId(), query.materialId());
    }
    @Override
    public List<LegacyInventoryFacade.Material> handle(GetPendingLegacyMaterialsQuery query) {
        return legacy.materials(query.laboratoryId()).stream()
            .filter(material -> repository.importedMaterial(query.laboratoryId(), material.id()).isEmpty()).toList();
    }

    private void requireMaterial(Long lab, Long id) {
        if (repository.material(lab, id, false).isEmpty()) throw new ApplicationException(ApplicationError.notFound("Material", id));
    }
}
