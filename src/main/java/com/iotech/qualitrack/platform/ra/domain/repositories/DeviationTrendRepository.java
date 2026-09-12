package com.iotech.qualitrack.platform.ra.domain.repositories;

import com.iotech.qualitrack.platform.ra.domain.model.entities.DeviationTrend;

import java.util.List;
import java.util.Optional;

/**
 * Repository port for deviation trend analyses.
 *
 * <p>Defines the persistence contract for trend analysis records related to
 * equipment parameters and historical measurements.</p>
 */
public interface DeviationTrendRepository {

    /**
     * Finds a deviation trend by its unique numeric identifier.
     *
     * @param id The trend identifier.
     * @return The matching deviation trend, if found.
     */
    Optional<DeviationTrend> findById(Long id);

    /**
     * Finds all deviation trend analyses associated with an equipment.
     *
     * @param equipmentId The equipment identifier.
     * @return List of deviation trends.
     */
    List<DeviationTrend> findAllByEquipmentId(Long equipmentId);

    /**
     * Finds all deviation trends for a specific equipment and parameter.
     *
     * @param equipmentId The equipment identifier.
     * @param parameterName The monitored parameter name.
     * @return List of deviation trends.
     */
    List<DeviationTrend> findAllByEquipmentIdAndParameterName(Long equipmentId, String parameterName);

    /**
     * Saves a deviation trend analysis.
     *
     * @param deviationTrend The deviation trend entity to persist.
     * @return The persisted deviation trend entity.
     */
    DeviationTrend save(DeviationTrend deviationTrend);

    /**
     * Checks whether a trend exists by its unique numeric identifier.
     *
     * @param id The trend identifier.
     * @return true if the trend exists; otherwise false.
     */
    boolean existsById(Long id);
}