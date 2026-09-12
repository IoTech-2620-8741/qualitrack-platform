package com.iotech.qualitrack.platform.ra.domain.model.commands;

/**
 * Command used to calculate a KPI dashboard snapshot for a laboratory.
 *
 * @param laboratoryId The laboratory identifier.
 */
public record CalculateKpiDashboardCommand(Long laboratoryId) {

    /**
     * Compact constructor for validation.
     */
    public CalculateKpiDashboardCommand {
        if (laboratoryId == null || laboratoryId <= 0) {
            throw new IllegalArgumentException("laboratoryId cannot be null or less than 1");
        }
    }
}