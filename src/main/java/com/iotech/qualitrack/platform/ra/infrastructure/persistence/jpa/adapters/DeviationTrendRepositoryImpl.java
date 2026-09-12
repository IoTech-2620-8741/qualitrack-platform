package com.iotech.qualitrack.platform.ra.infrastructure.persistence.jpa.adapters;

import com.iotech.qualitrack.platform.ra.domain.model.entities.DeviationTrend;
import com.iotech.qualitrack.platform.ra.domain.repositories.DeviationTrendRepository;
import com.iotech.qualitrack.platform.ra.infrastructure.persistence.jpa.assemblers.DeviationTrendPersistenceAssembler;
import com.iotech.qualitrack.platform.ra.infrastructure.persistence.jpa.assemblers.TrendDataPointPersistenceAssembler;
import com.iotech.qualitrack.platform.ra.infrastructure.persistence.jpa.repositories.DeviationTrendPersistenceRepository;
import com.iotech.qualitrack.platform.ra.infrastructure.persistence.jpa.repositories.TrendDataPointPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA adapter implementation for the {@link DeviationTrendRepository} domain port.
 */
@Repository
public class DeviationTrendRepositoryImpl implements DeviationTrendRepository {

    private final DeviationTrendPersistenceRepository trendPersistenceRepository;
    private final TrendDataPointPersistenceRepository dataPointPersistenceRepository;

    public DeviationTrendRepositoryImpl(
            DeviationTrendPersistenceRepository trendPersistenceRepository,
            TrendDataPointPersistenceRepository dataPointPersistenceRepository
    ) {
        this.trendPersistenceRepository = trendPersistenceRepository;
        this.dataPointPersistenceRepository = dataPointPersistenceRepository;
    }

    @Override
    public Optional<DeviationTrend> findById(Long id) {
        return trendPersistenceRepository.findById(id)
                .map(entity -> {
                    var dataPoints = dataPointPersistenceRepository.findAllByTrendId(entity.getId()).stream()
                            .map(TrendDataPointPersistenceAssembler::toDomainFromPersistence)
                            .toList();

                    return DeviationTrendPersistenceAssembler.toDomainFromPersistence(entity, dataPoints);
                });
    }

    @Override
    public List<DeviationTrend> findAllByEquipmentId(Long equipmentId) {
        return trendPersistenceRepository.findAllByEquipmentId(equipmentId).stream()
                .map(entity -> {
                    var dataPoints = dataPointPersistenceRepository.findAllByTrendId(entity.getId()).stream()
                            .map(TrendDataPointPersistenceAssembler::toDomainFromPersistence)
                            .toList();

                    return DeviationTrendPersistenceAssembler.toDomainFromPersistence(entity, dataPoints);
                })
                .toList();
    }

    @Override
    public List<DeviationTrend> findAllByEquipmentIdAndParameterName(Long equipmentId, String parameterName) {
        return trendPersistenceRepository.findAllByEquipmentIdAndParameterName(equipmentId, parameterName).stream()
                .map(entity -> {
                    var dataPoints = dataPointPersistenceRepository.findAllByTrendId(entity.getId()).stream()
                            .map(TrendDataPointPersistenceAssembler::toDomainFromPersistence)
                            .toList();

                    return DeviationTrendPersistenceAssembler.toDomainFromPersistence(entity, dataPoints);
                })
                .toList();
    }

    @Override
    public DeviationTrend save(DeviationTrend deviationTrend) {
        var trendEntity = DeviationTrendPersistenceAssembler.toPersistenceFromDomain(deviationTrend);
        var savedTrendEntity = trendPersistenceRepository.save(trendEntity);

        dataPointPersistenceRepository.deleteAllByTrendId(savedTrendEntity.getId());

        if (deviationTrend.getDataPoints() != null) {
            deviationTrend.getDataPoints().stream()
                    .map(dataPoint -> TrendDataPointPersistenceAssembler.toPersistenceFromDomain(
                            savedTrendEntity.getId(),
                            dataPoint
                    ))
                    .forEach(dataPointPersistenceRepository::save);
        }

        var savedDataPoints = dataPointPersistenceRepository.findAllByTrendId(savedTrendEntity.getId()).stream()
                .map(TrendDataPointPersistenceAssembler::toDomainFromPersistence)
                .toList();

        return DeviationTrendPersistenceAssembler.toDomainFromPersistence(savedTrendEntity, savedDataPoints);
    }

    @Override
    public boolean existsById(Long id) {
        return trendPersistenceRepository.existsById(id);
    }
}