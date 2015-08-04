package de.weichert.multitenancy.service;

import de.weichert.multitenancy.exception.TenantDataSourceNotResolvableException;
import de.weichert.multitenancy.model.TenantDataSourceConfigEntity;
import de.weichert.multitenancy.repository.TenantDataSourceConfigRepository;
import org.jinq.jpa.JinqJPAStreamProvider;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.util.NoSuchElementException;

/**
 * Created by Jannik on 15.02.15.
 */
@Service
public class TenantDataSourceConfigService implements InitializingBean {

    @PersistenceContext(unitName = "metaPU")
    EntityManager em;

    @Autowired
    TenantDataSourceConfigRepository tenantDsRepository;

    @Autowired
            @Qualifier("metaDataSource")
    DataSource metaDataSource;

    JinqJPAStreamProvider streamProvider;

    private final org.slf4j.Logger log = LoggerFactory.getLogger(TenantDataSourceConfigService.class);

    public TenantDataSourceConfigService() {
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        log.debug("Configuring Meta JinqJPAStreamProvider");
        streamProvider = new JinqJPAStreamProvider(em.getMetamodel());
    }

    @Transactional
    public TenantDataSourceConfigEntity createTenantDataSource(String db_name,
                                                               String driver_class_name,
                                                               String tenant_id,
                                                               String password,
                                                               String server_name,
                                                               String port,
                                                               String url) {
        String username = tenant_id + "_login";
        String schema = tenant_id;

        TenantDataSourceConfigEntity tenantDs = new TenantDataSourceConfigEntity(db_name, driver_class_name, tenant_id, username, password, server_name, port, schema, url);
        return tenantDsRepository.save(tenantDs);
    }


    @Transactional
    public TenantDataSourceConfigEntity getTenantDatasourceConfig(String tenantId) {
        try{
            TenantDataSourceConfigEntity tenantDsConfig = streamProvider.streamAll(em,TenantDataSourceConfigEntity.class)
                    .where(entity -> tenantId.equals(entity.getTenantId())).getOnlyValue();
            return tenantDsConfig;
        } catch(NoSuchElementException e){
            throw new TenantDataSourceNotResolvableException();
        }
    }
}
