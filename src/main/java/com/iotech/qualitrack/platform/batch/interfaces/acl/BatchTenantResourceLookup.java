package com.iotech.qualitrack.platform.batch.interfaces.acl;
import com.iotech.qualitrack.platform.batch.domain.repositories.BatchRepository;
import com.iotech.qualitrack.platform.shared.application.security.ResourceOwner;
import com.iotech.qualitrack.platform.shared.application.security.TenantResourceLookup;
import org.springframework.stereotype.Component;
import java.util.Optional;
import java.util.Set;

@Component
public class BatchTenantResourceLookup implements TenantResourceLookup {
    private final BatchRepository batches;
    public BatchTenantResourceLookup(BatchRepository batches) { this.batches = batches; }
    public Set<String> types() { return Set.of("batchId"); }
    public Optional<ResourceOwner> owner(String type, Long id) {
        return batches.findById(id).map(item -> new ResourceOwner("laboratoryId", item.getLabId()));
    }
}
