package com.iotech.qualitrack.platform.iam.interfaces.rest.resources;

/**
 * Request resource used to authenticate a user.
 *
 * @param username account username
 * @param password raw account password
 */
public record SignInResource(
        String username,
        String password
) {
}