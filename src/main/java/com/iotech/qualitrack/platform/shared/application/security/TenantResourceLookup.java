package com.iotech.qualitrack.platform.shared.application.security;

import java.util.Optional;
import java.util.Set;

/** Each bounded context resolves ownership using only its own repositories. */
public interface TenantResourceLookup {
    Set<String> types();
    Optional<ResourceOwner> owner(String type, Long id);
}
