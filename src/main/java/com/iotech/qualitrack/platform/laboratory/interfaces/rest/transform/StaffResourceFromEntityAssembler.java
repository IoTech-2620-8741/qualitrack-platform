package com.iotech.qualitrack.platform.laboratory.interfaces.rest.transform;

import com.iotech.qualitrack.platform.laboratory.domain.model.aggregates.StaffMember;
import com.iotech.qualitrack.platform.laboratory.interfaces.rest.resources.StaffMemberResource;

/**
 * Assembler to convert a StaffMember entity to a StaffMemberResource.
 */
public class StaffResourceFromEntityAssembler {

    /**
     * Converts a StaffMember entity to a StaffMemberResource.
     *
     * @param entity The {@link StaffMember} entity to convert.
     * @return The {@link StaffMemberResource} resource that results from the conversion.
     */
    public static StaffMemberResource toResourceFromEntity(StaffMember entity) {
        return new StaffMemberResource(
                entity.getId(),
                entity.getLaboratoryId(),
                entity.getFullName(),
                entity.getRole(),
                entity.getEmail(),
                entity.isActive()
        );
    }
}