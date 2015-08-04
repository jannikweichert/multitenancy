package de.weichert.multitenancy.scope;

import de.weichert.multitenancy.exception.TenantNotResolveableException;
import de.weichert.multitenancy.exception.TenantScopeNotAvailableException;
import de.weichert.multitenancy.identifier.TenantIdentifierStorage;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.Scope;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class TenantScope implements Scope {

    @Autowired
    private TenantIdentifierStorage tenantIdentifierStorage;

    private final static Map<String, Map<String, Object>> tenantBeanMaps = new ConcurrentHashMap<>();

    public static final String SCOPE_NAME = "tenant";

    private static final String BASIC_TENANT_ID = "BASIC";


    @Override
    public Object get(String beanName, ObjectFactory<?> objectFactory) {
        String tenantName = getConversationId();
        return getTenantBean(beanName, tenantName, objectFactory);
    }

    private static Object getTenantBean(String beanName, String tenantName, ObjectFactory<?> objectFactory) {
        assert beanName != null;
        assert tenantName != null;
        assert objectFactory != null;

        Map<String, Object> beanMap = getBeanMap(tenantName);

        synchronized (beanMap) {
            Object bean = beanMap.get(beanName);
            if (bean == null) {
                bean = objectFactory.getObject();
                beanMap.put(beanName, bean);
            }
            return bean;
        }
    }


    private static synchronized Map<String, Object> getBeanMap(String tenantName) {
        Map<String, Object> beanMap = tenantBeanMaps.get(tenantName);
        if (beanMap == null) {
            beanMap = new ConcurrentHashMap<>();
            tenantBeanMaps.put(tenantName, beanMap);
        }
        return beanMap;
    }

    @Override
    public Object remove(String name) {
        System.out.println("TenantScope.remove");
        return null;
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback) {

    }

    @Override
    public Object resolveContextualObject(String key) {
        return null;
    }

    @Override
    public String getConversationId() {
        if (tenantIdentifierStorage == null) {
            return BASIC_TENANT_ID;
        }
        try {
            return tenantIdentifierStorage.getCurrentTenantId();
        } catch (TenantNotResolveableException e) {
            throw new TenantScopeNotAvailableException(e);
        }
    }


}
