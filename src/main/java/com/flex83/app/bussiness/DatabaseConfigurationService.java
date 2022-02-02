package com.flex83.app.bussiness;

import com.flex83.app.entities.TenantDatabaseConfig;
import com.flex83.app.repository.TenantDatabaseConfigRepository;
import com.flex83.app.request.DatabaseConfigRequest;
import com.flex83.app.utils.CommonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseConfigurationService {
    @Autowired
    private TenantDatabaseConfigRepository tenantDatabaseConfigRepository;

    public void createDataBaseConfig(DatabaseConfigRequest databaseConfigRequest) {
        TenantDatabaseConfig tenantDatabaseConfig = new TenantDatabaseConfig();
        BeanUtils.copyProperties(databaseConfigRequest, tenantDatabaseConfig);

        tenantDatabaseConfig.setId(CommonUtils.generateUUID());
        CommonUtils.setCreateEntityFields(tenantDatabaseConfig);
        CommonUtils.setUpdateEntityFields(tenantDatabaseConfig);

        tenantDatabaseConfigRepository.save(tenantDatabaseConfig);
    }
}
