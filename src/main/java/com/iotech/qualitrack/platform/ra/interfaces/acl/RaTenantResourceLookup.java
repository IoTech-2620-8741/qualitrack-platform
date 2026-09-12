package com.iotech.qualitrack.platform.ra.interfaces.acl;
import com.iotech.qualitrack.platform.ra.domain.repositories.AuditReportRepository;
import com.iotech.qualitrack.platform.shared.application.security.ResourceOwner;
import com.iotech.qualitrack.platform.shared.application.security.TenantResourceLookup;
import org.springframework.stereotype.Component;
import java.util.Optional;
import java.util.Set;

@Component
public class RaTenantResourceLookup implements TenantResourceLookup {
    private final AuditReportRepository reports;
    public RaTenantResourceLookup(AuditReportRepository reports) { this.reports = reports; }
    public Set<String> types() { return Set.of("reportId"); }
    public Optional<ResourceOwner> owner(String type, Long id) {
        return reports.findById(id).map(item -> new ResourceOwner("laboratoryId", item.getLaboratoryId()));
    }
}
