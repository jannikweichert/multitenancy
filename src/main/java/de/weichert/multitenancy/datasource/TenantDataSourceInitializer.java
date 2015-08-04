package de.weichert.multitenancy.datasource;

import de.weichert.multitenancy.model.TenantDataSourceConfigEntity;

import javax.sql.DataSource;

/**
 * Created by Jannik on 22.03.15.
 */
public interface TenantDataSourceInitializer {
    DataSource initializeDataSource(TenantDataSourceConfigEntity dataSourceConfig);
}
