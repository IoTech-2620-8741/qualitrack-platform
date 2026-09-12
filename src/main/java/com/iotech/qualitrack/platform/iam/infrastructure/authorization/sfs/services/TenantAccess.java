package com.iotech.qualitrack.platform.iam.infrastructure.authorization.sfs.services;

import com.iotech.qualitrack.platform.iam.infrastructure.authorization.sfs.model.UserDetailsImpl;
import com.iotech.qualitrack.platform.shared.application.security.TenantResourceLookup;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("tenantAccess")
public class TenantAccess {
    private final List<TenantResourceLookup> lookups;

    public TenantAccess(List<TenantResourceLookup> lookups) { this.lookups = lookups; }

    public boolean allows(String type, Long id) { return allows(type, id, 0); }

    private boolean allows(String type, Long id, int depth) {
        if (id == null || id <= 0 || depth > 3) return false;
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetailsImpl user)) return false;
        if (type.equals("laboratoryId") || type.equals("labId")) return id.equals(user.getLaboratoryId());
        if (type.equals("userId")) return id.equals(user.getId());
        return lookups.stream().filter(lookup -> lookup.types().contains(type)).findFirst()
                .flatMap(lookup -> lookup.owner(type, id))
                .map(owner -> allows(owner.type(), owner.id(), depth + 1)).orElse(false);
    }

    public void require(String type, Long id) {
        if (!allows(type, id)) throw new AccessDeniedException("Resource is not available to this account");
    }

    public boolean recognizes(String type) {
        return type.equals("laboratoryId") || type.equals("labId") || type.equals("userId")
                || lookups.stream().anyMatch(lookup -> lookup.types().contains(type));
    }
}
