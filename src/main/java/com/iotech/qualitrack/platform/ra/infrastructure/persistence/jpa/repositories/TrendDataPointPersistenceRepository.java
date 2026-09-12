package com.iotech.qualitrack.platform.ra.infrastructure.persistence.jpa.repositories;

import com.iotech.qualitrack.platform.ra.infrastructure.persistence.jpa.entities.TrendDataPointPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for {@link TrendDataPointPersistenceEntity}.
 *
 * <p>Handles database operations for measurement points linked to deviation trends.</p>
 */
@Repository
public interface TrendDataPointPersistenceRepository extends JpaRepository<TrendDataPointPersistenceEntity, Long> {

    /**
     * Finds all data points associated with a deviation trend.
     *
     * @param trendId The numeric ID of the deviation trend.
     */
    List<TrendDataPointPersistenceEntity> findAllByTrendId(Long trendId);

    /**
     * Deletes all data points associated with a deviation trend.
     *
     * @param trendId The numeric ID of the deviation trend.
     */
    void deleteAllByTrendId(Long trendId);
}