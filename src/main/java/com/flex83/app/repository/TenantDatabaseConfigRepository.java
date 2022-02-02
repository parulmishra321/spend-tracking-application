package com.flex83.app.repository;

import com.flex83.app.entities.TenantDatabaseConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TenantDatabaseConfigRepository extends JpaRepository<TenantDatabaseConfig, String> {
    @Query("FROM TenantDatabaseConfig where tenant =:tenant")
    TenantDatabaseConfig getDatabaseConfigByTenant(@Param("tenant") String tenant);
}
