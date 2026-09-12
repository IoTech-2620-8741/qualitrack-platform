package com.iotech.qualitrack.platform.ra.interfaces.rest.resources;

/**
 * Resource used to request KPI dashboard calculation.
 *
 * @param laboratoryId The laboratory identifier.
 */
public record CalculateKpiDashboardResource(Long laboratoryId) {
}