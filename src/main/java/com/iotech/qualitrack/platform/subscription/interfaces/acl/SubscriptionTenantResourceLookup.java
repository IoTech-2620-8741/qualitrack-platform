package com.iotech.qualitrack.platform.subscription.interfaces.acl;
import com.iotech.qualitrack.platform.subscription.domain.repositories.SubscriptionRepository;
import com.iotech.qualitrack.platform.shared.application.security.ResourceOwner;
import com.iotech.qualitrack.platform.shared.application.security.TenantResourceLookup;
import org.springframework.stereotype.Component;
import java.util.Optional;
import java.util.Set;

@Component
public class SubscriptionTenantResourceLookup implements TenantResourceLookup {
    private final SubscriptionRepository subscriptions;
    public SubscriptionTenantResourceLookup(SubscriptionRepository subscriptions) { this.subscriptions = subscriptions; }
    public Set<String> types() { return Set.of("subscriptionId"); }
    public Optional<ResourceOwner> owner(String type, Long id) {
        return subscriptions.findById(id).map(item -> new ResourceOwner("userId", item.getUserId()));
    }
}
