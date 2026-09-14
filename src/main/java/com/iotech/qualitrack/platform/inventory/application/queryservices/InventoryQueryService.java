package com.iotech.qualitrack.platform.inventory.application.queryservices;
import com.iotech.qualitrack.platform.inventory.domain.model.queries.*;
import com.iotech.qualitrack.platform.inventory.domain.model.aggregates.RawMaterialBatch;
import com.iotech.qualitrack.platform.inventory.domain.model.entities.InventoryMovement;
import com.iotech.qualitrack.platform.inventory.domain.model.valueobjects.MaterialStockSummary;
import java.util.List;
import com.iotech.qualitrack.platform.laboratory.interfaces.acl.LegacyInventoryFacade;

public interface InventoryQueryService {
    List<LegacyInventoryFacade.Material> handle(GetPendingLegacyMaterialsQuery query);
    List<MaterialStockSummary> handle(GetInventoryMaterialsQuery query);
    List<RawMaterialBatch> handle(GetMaterialReceiptsQuery query);
    List<InventoryMovement> handle(GetMaterialMovementsQuery query);
}
