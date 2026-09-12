package com.iotech.qualitrack.platform.laboratory.application.internal.outboundservices.acl;

import com.iotech.qualitrack.platform.ca.interfaces.acl.ComplianceContextFacade;
import com.iotech.qualitrack.platform.laboratory.domain.model.events.RawMaterialLowStockEvent;
import org.springframework.stereotype.Service;

/**
 * ACL service used by the Laboratory bounded context to interact with Compliance & Alerts (CA) capabilities.
 *
 * <p>Acts as an anti-corruption layer to prevent external domain concepts from leaking into the
 * laboratory bounded context.</p>
 */
@Service
public class LaboratoryExternalComplianceService {

    private final ComplianceContextFacade complianceContextFacade;

    public LaboratoryExternalComplianceService(ComplianceContextFacade complianceContextFacade) {
        this.complianceContextFacade = complianceContextFacade;
    }

    /**
     * Notifies the Compliance & Alerts context about a low stock compliance event.
     *
     * @param event the raw material low stock event
     */
    public void notifyLowStockDeviation(RawMaterialLowStockEvent event) {
        complianceContextFacade.recordRawMaterialLowStockEvent(
                event.rawMaterialId(),
                event.laboratoryId(),
                event.materialName(),
                event.currentStock(),
                event.minimumThreshold()
        );
    }
}