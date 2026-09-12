package com.iotech.qualitrack.platform.iam.interfaces.rest.resources;

import java.util.List;

/**
 * Request resource used to register a user.
 *
 * @param username account username
 * @param password raw account password
 * @param roles requested role names
 * @param laboratoryId associated laboratory identifier
 */
public record SignUpResource(
        String username,
        String password,
        List<String> roles,
        Long laboratoryId
) {
}