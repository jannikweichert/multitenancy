package de.weichert.multitenancy.identifier;

import de.weichert.multitenancy.exception.TenantNotResolveableException;

/**
 * Created by Jannik on 21.04.15.
 */
public interface TenantIdentifierStorageProxy {
    String getCurrentTenantId() throws TenantNotResolveableException;

    void setCurrentTenantId(String tenantId);
}
