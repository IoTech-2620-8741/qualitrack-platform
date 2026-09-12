package com.iotech.qualitrack.platform.ra.application.commandservices;

import com.iotech.qualitrack.platform.ra.domain.model.aggregates.KpiDashboard;
import com.iotech.qualitrack.platform.ra.domain.model.commands.CalculateDeviationTrendCommand;
import com.iotech.qualitrack.platform.ra.domain.model.commands.CalculateKpiDashboardCommand;
import com.iotech.qualitrack.platform.ra.domain.model.commands.ExportEquipmentLogCommand;
import com.iotech.qualitrack.platform.ra.domain.model.commands.GenerateBatchReportCommand;
import com.iotech.qualitrack.platform.ra.domain.model.commands.GenerateComplianceReportCommand;
import com.iotech.qualitrack.platform.ra.domain.model.entities.DeviationTrend;
import com.iotech.qualitrack.platform.shared.application.result.ApplicationError;
import com.iotech.qualitrack.platform.shared.application.result.Result;

/**
 * Application service contract for Reporting and Analysis commands.
 *
 * <p>This service coordinates write-side use cases such as generating reports,
 * exporting equipment logs, calculating KPI dashboards, calculating deviation
 * trends, and creating immutable audit report records.</p>
 */
public interface RaCommandService {

    /**
     * Handles KPI dashboard calculation for a laboratory.
     *
     * @param command command containing the laboratory identifier
     * @return calculated KPI dashboard or an application error
     * @see CalculateKpiDashboardCommand
     */
    Result<KpiDashboard, ApplicationError> handle(CalculateKpiDashboardCommand command);

    /**
     * Handles deviation trend calculation for an equipment parameter.
     *
     * @param command command containing equipment and parameter identifiers
     * @return calculated deviation trend or an application error
     * @see CalculateDeviationTrendCommand
     */
    Result<DeviationTrend, ApplicationError> handle(CalculateDeviationTrendCommand command);

    /**
     * Handles the generation of a production batch report.
     *
     * @param command command containing batch report generation settings
     * @return generated report content as bytes or an application error
     * @see GenerateBatchReportCommand
     */
    Result<byte[], ApplicationError> handle(GenerateBatchReportCommand command);

    /**
     * Handles the generation of a regulatory compliance report.
     *
     * @param command command containing compliance report generation settings
     * @return generated report content as bytes or an application error
     * @see GenerateComplianceReportCommand
     */
    Result<byte[], ApplicationError> handle(GenerateComplianceReportCommand command);

    /**
     * Handles the export of historical equipment logs.
     *
     * @param command command containing equipment log export settings
     * @return exported log content as bytes or an application error
     * @see ExportEquipmentLogCommand
     */
    Result<byte[], ApplicationError> handle(ExportEquipmentLogCommand command);
}