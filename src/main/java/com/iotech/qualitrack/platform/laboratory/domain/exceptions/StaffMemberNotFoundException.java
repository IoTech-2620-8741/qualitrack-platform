package com.iotech.qualitrack.platform.laboratory.domain.exceptions;

/**
 * Exception thrown when a staff member is not found.
 */
public class StaffMemberNotFoundException extends RuntimeException {
    /**
     * Constructor for the exception.
     * @param staffMemberId The numeric ID of the staff member that was not found.
     */
    public StaffMemberNotFoundException(Long staffMemberId) {
        super(String.format("Staff member with ID %d not found.", staffMemberId));
    }
}