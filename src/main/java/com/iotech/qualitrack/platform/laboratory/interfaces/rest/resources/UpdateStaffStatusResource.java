package com.iotech.qualitrack.platform.laboratory.interfaces.rest.resources;

/**
 * Resource used to request a staff member status update.
 *
 * @param active whether the staff member should be active
 */
public record UpdateStaffStatusResource(
        Boolean active
) {
}