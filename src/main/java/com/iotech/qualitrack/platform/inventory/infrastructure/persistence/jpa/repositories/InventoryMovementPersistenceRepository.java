package com.iotech.qualitrack.platform.inventory.infrastructure.persistence.jpa.repositories;
import com.iotech.qualitrack.platform.inventory.infrastructure.persistence.jpa.entities.InventoryMovementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface InventoryMovementPersistenceRepository extends JpaRepository<InventoryMovementEntity, Long> {
    List<InventoryMovementEntity> findAllByLaboratoryIdAndMaterialIdOrderByOccurredAtDescIdDesc(Long laboratoryId, Long materialId);
    Optional<InventoryMovementEntity> findByLaboratoryIdAndOperationId(Long laboratoryId, String operationId);
}
