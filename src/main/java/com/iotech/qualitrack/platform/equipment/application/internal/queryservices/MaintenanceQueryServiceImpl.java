package com.iotech.qualitrack.platform.equipment.application.internal.queryservices;

import com.iotech.qualitrack.platform.equipment.application.queryservices.MaintenanceQueryService;
import com.iotech.qualitrack.platform.equipment.domain.model.aggregates.MaintenanceRecord;
import com.iotech.qualitrack.platform.equipment.domain.model.queries.GetMaintenanceByEquipmentIdQuery;
import com.iotech.qualitrack.platform.equipment.domain.repositories.MaintenanceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Application service implementation that resolves maintenance record read queries.
 */
@Service
public class MaintenanceQueryServiceImpl implements MaintenanceQueryService {

    private final MaintenanceRepository maintenanceRepository;

    public MaintenanceQueryServiceImpl(MaintenanceRepository maintenanceRepository) {
        this.maintenanceRepository = maintenanceRepository;
    }

    @Override
    public List<MaintenanceRecord> handle(GetMaintenanceByEquipmentIdQuery query) {
        return maintenanceRepository.findAllByEquipmentId(query.equipmentId());
    }
}