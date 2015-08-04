package de.weichert.multitenancy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Jannik on 15.02.15.
 */
@Entity
@Table(name = "T_TENANT_DATA_SOURCE")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TenantDataSourceConfigEntity {
    @Id
    @Column(name = "tenant_id")
    private String tenantId;
    @Column(name = "db_name")
    private String dbName;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "driver_class_name")
    private String driverClassName;
    @Column(name = "server_name")
    private String serverName;
    @Column(name = "port")
    private String port;
    @Column(name = "schema")
    private String schema;
    @Column(name = "url")
    private String url;
}
