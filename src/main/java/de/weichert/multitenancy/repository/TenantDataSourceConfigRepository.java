package de.weichert.multitenancy.repository;


import de.weichert.multitenancy.model.TenantDataSourceConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the TenantDataSourceEntity.
 * Created by Jannik on 09.03.15.
 */
public interface TenantDataSourceConfigRepository extends JpaRepository<TenantDataSourceConfigEntity, String> {
}
