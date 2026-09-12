package com.iotech.qualitrack.platform.ca.interfaces.acl;

import java.math.BigDecimal;

import com.iotech.qualitrack.platform.ca.domain.model.entities.ComplianceEvent;
import com.iotech.qualitrack.platform.ca.domain.model.valueobjects.ComplianceEventType;
import com.iotech.qualitrack.platform.ca.domain.repositories.ComplianceEventRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

/**
 * ACL facade exposed by the Compliance and Alerts bounded context.
 *
 * <p>Provides a stable interface for other bounded contexts to interact with CA
 * without depending on CA internal application services or domain commands.</p>
 */
@Service
public class ComplianceContextFacade {

    private final ComplianceEventRepository complianceEventRepository;

    /**
     * Creates a new ComplianceContextFacade.
     *
     * @param complianceEventRepository compliance event repository
     */
    public ComplianceContextFacade(ComplianceEventRepository complianceEventRepository) {
        this.complianceEventRepository = complianceEventRepository;
    }

    /**
     * Records a raw material low stock compliance event.
     *
     * @param rawMaterialId the raw material identifier
     * @param laboratoryId the laboratory identifier
     * @param materialName the raw material name
     * @param currentStock the current stock quantity
     * @param minimumThreshold the minimum required stock quantity
     */
    public void recordRawMaterialLowStockEvent(
            Long rawMaterialId,
            Long laboratoryId,
            String materialName,
            BigDecimal currentStock,
            BigDecimal minimumThreshold
    ) {
        complianceEventRepository.save(new ComplianceEvent(
                rawMaterialId,
                ComplianceEventType.RAW_MATERIAL_LOW_STOCK,
                "Raw material '%s' from laboratory ID %d is below minimum stock. Current stock: %s, minimum threshold: %s."
                        .formatted(materialName, laboratoryId, currentStock, minimumThreshold),
                Instant.now().toString(),
                null
        ));
    }

    /**
     * Verifies whether a batch can be released according to compliance rules.
     *
     * <p>At this stage, the rule is intentionally conservative and simple:
     * a batch can be released when there are no unresolved non-compliance events
     * associated with that batch identifier.</p>
     *
     * @param batchId the batch identifier
     * @return true when the batch can be released
     */
    public boolean canReleaseBatch(Long batchId) {
        if (batchId == null || batchId <= 0) {
            return false;
        }

        return complianceEventRepository.findAllByRelatedEntityId(batchId).stream()
                .filter(event -> event.getEventType().subject()
                        == com.iotech.qualitrack.platform.ca.domain.model.valueobjects.ComplianceEventSubject.BATCH)
                .noneMatch(event -> !event.isResolved());
    }
}
