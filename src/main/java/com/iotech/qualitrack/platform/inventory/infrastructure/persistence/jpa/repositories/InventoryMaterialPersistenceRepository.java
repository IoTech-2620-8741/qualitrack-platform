package com.iotech.qualitrack.platform.inventory.infrastructure.persistence.jpa.repositories;
import com.iotech.qualitrack.platform.inventory.infrastructure.persistence.jpa.entities.InventoryMaterialPersistenceEntity;
import org.springframework.data.jpa.repository.*;
import jakarta.persistence.LockModeType;
import java.util.*;

public interface InventoryMaterialPersistenceRepository extends JpaRepository<InventoryMaterialPersistenceEntity, Long> {
    List<InventoryMaterialPersistenceEntity> findAllByLaboratoryIdOrderByName(Long laboratoryId);
    List<InventoryMaterialPersistenceEntity> findAllByLaboratoryIdAndEnvironmentIdOrderByName(Long laboratoryId, Long environmentId);
    Optional<InventoryMaterialPersistenceEntity> findByLaboratoryIdAndId(Long laboratoryId, Long id);
    Optional<InventoryMaterialPersistenceEntity> findByLaboratoryIdAndLegacyId(Long laboratoryId, Long legacyId);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select m from InventoryMaterialPersistenceEntity m where m.laboratoryId = :lab and m.id = :id")
    Optional<InventoryMaterialPersistenceEntity> findForUpdate(Long lab, Long id);
    @Query("select m.id from InventoryMaterialPersistenceEntity m where m.laboratoryId = :lab and lower(m.code) = lower(:code)")
    List<Long> findIdsByCode(Long lab, String code);
}
