package com.iotech.qualitrack.platform.laboratory.application.queryservices;

import com.iotech.qualitrack.platform.laboratory.domain.model.aggregates.StaffMember;
import com.iotech.qualitrack.platform.laboratory.domain.model.queries.GetStaffByLabIdQuery;

import java.util.List;

/**
 * Application service contract for staff member read queries.
 */
public interface StaffQueryService {

    /**
     * Handles retrieval of all staff members belonging to a specific laboratory.
     *
     * @param query laboratory-id query
     * @return list of staff members for the given laboratory
     * @see GetStaffByLabIdQuery
     */
    List<StaffMember> handle(GetStaffByLabIdQuery query);
}