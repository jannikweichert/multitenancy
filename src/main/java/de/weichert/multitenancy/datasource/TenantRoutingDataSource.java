package de.weichert.multitenancy.datasource;

import de.weichert.multitenancy.exception.TenantDataSourceNotResolvableException;
import de.weichert.multitenancy.exception.TenantNotResolveableException;
import de.weichert.multitenancy.identifier.TenantIdentifierStorage;
import de.weichert.multitenancy.model.TenantDataSourceConfigEntity;
import de.weichert.multitenancy.service.TenantDataSourceConfigService;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jannik on 26.02.15.
 */
public class TenantRoutingDataSource extends AbstractDataSource {

    @Autowired
    TenantIdentifierStorage tenantIdentifierStorage;

    @Autowired
    TenantDataSourceInitializer tenantDataSourceInitializer;

    Map<String, DataSource> tenantDataSourceMap = new HashMap<>();

    @Autowired
    @Lazy
    TenantDataSourceConfigService tenantDataSourceConfigService;

    private final DataSource defaultDataSource;

    public TenantRoutingDataSource(DataSource defaultDataSource) {
        this.defaultDataSource = defaultDataSource;
    }

    private boolean defaultReturned = false;

    @Override
    public Connection getConnection() throws SQLException {
        if (defaultReturned) {
            return getDataSourceForCurrentTenant().getConnection();
        } else {
            defaultReturned = true;
            return defaultDataSource.getConnection();
        }
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {

        if (defaultReturned) {
            return getDataSourceForCurrentTenant().getConnection(username, password);
        } else {
            defaultReturned = true;
            return defaultDataSource.getConnection(username, password);
        }
    }

    private String determineCurrentTenandIdentifer() throws TenantNotResolveableException {
        try {
            return tenantIdentifierStorage.getCurrentTenantId();
        } catch (BeanCreationException e) {
            throw new TenantNotResolveableException();
        }
    }

    private DataSource getDataSourceForCurrentTenant() {
        String tenantIdentifier;
        try {
            tenantIdentifier = determineCurrentTenandIdentifer();
        } catch (TenantNotResolveableException e) {
            return defaultDataSource;
        }

        Assert.notNull(tenantIdentifier);
        DataSource tenantDataSource = getDataSourceForTenant(tenantIdentifier);
        return tenantDataSource;
    }

    public DataSource getDataSourceForTenant(String tenantId) {
        DataSource obtainedDataSource;
        if (isDataSourceCached(tenantId)) {
            obtainedDataSource = getCachedDataSource(tenantId);
        } else {
            obtainedDataSource = obtainDataSourceFromDatabase(tenantId);
            tenantDataSourceMap.put(tenantId, obtainedDataSource);
        }
        return obtainedDataSource;
    }

    private boolean isDataSourceCached(String tenantIdentifier) {
        return tenantDataSourceMap.containsKey(tenantIdentifier);
    }

    private DataSource getCachedDataSource(String tenantIdentifier) {
        return tenantDataSourceMap.get(tenantIdentifier);
    }

    private DataSource obtainDataSourceFromDatabase(String tenantId) {
        TenantDataSourceConfigEntity dataSourceConfig = tenantDataSourceConfigService.getTenantDatasourceConfig(tenantId);
        try {
            return tenantDataSourceInitializer.initializeDataSource(dataSourceConfig);
        } catch (Exception e) {
            throw new TenantDataSourceNotResolvableException("Failed to initialize with given config from database", e);
        }
    }
}
