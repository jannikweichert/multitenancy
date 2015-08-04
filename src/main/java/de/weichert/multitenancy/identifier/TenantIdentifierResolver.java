package de.weichert.multitenancy.identifier;

import de.weichert.multitenancy.exception.TenantNotResolveableException;
import org.springframework.stereotype.Component;

/**
 * Allows a custom implementation to resolve the tenantId to be used to resolve the tenant-specific DataSource.
 * Common usages are:
 * <ul>
 * <li><b>Filter</b> to resolve the tenantId directly from the WebRequest</li>
 * <li><b>SecurityContext</b>: Map principals to their corresponding tenantId. This is a good if there is a 1:n relation.</li>
 * </ul>
 * <p/>
 * Created by Jannik on 24.02.15.
 */
@Component
public interface TenantIdentifierResolver {

    /**
     * Resolve and return the tenantId to be used to resolve the tenant-spefic DataSource.
     *
     * @return String identifier
     * @throws TenantNotResolveableException is expected to be thrown if no tenantId could be resolved.
     */
    String resolveCurrentTenantIdentifier() throws TenantNotResolveableException;

}
