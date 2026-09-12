package com.iotech.qualitrack.platform.shared.application.security;

/** Identity of the authenticated actor; absent for system-originated events. */
public interface CurrentUser {
    Long userId();
}
