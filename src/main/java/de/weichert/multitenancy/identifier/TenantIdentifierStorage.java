package de.weichert.multitenancy.identifier;

import de.weichert.multitenancy.exception.TenantNotResolveableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This is the key entry class to access the tenantIdentifier to be used for dataSource-decisions.
 * <p/>
 * Created by Jannik on 21.03.15.
 */
@Component
//@Scope(value = SimpleThreadScope., proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TenantIdentifierStorage {
    public final static String TENANT_ADMIN = "admin";
    public final static String TENANT_META = "meta";
    public final static String TENANT_PREFIX = "tenant_";

    @Autowired(required = false)
    TenantIdentifierResolver tenantIdentifierResolver;

    private String storedTenantId;

    private static InheritableThreadLocal<String> threadTenantId = new InheritableThreadLocal<>();

    /**
     * If a tenantIdentifier is stored in the TenantThreadHolder it is returned. Otherwise it is resolved from the
     * TenantIdentifierResolver Implementation provided by the parent module.
     *
     * @return
     * @throws TenantNotResolveableException
     */
    public String getCurrentTenantId() throws TenantNotResolveableException {


        if (storedTenantId == null) {
            if (threadTenantId.get() != null) {
                return threadTenantId.get();
            } else if (tenantIdentifierResolver == null) {
                throw new TenantNotResolveableException("No TenantIdentifier has been stored, and no TenantIdentifierResolver could be autowired");
            }
            storedTenantId = tenantIdentifierResolver.resolveCurrentTenantIdentifier();

        }
        return storedTenantId;
    }

    public void setCurrentTenantId(String tenantId) {
        storedTenantId = tenantId;
    }

    public static void setThreadTenantId(String tenantId) {
        threadTenantId.set(tenantId);
    }

    public void remove() {
        threadTenantId.remove();
    }
}
