package com.spendTracker.app.bussiness;

import com.spendTracker.app.entities.TenantDatabaseConfig;
import com.spendTracker.app.exception.ValidationException;
import com.spendTracker.app.repository.TenantDatabaseConfigRepository;
import com.spendTracker.app.request.DatabaseConfigRequest;
import com.spendTracker.app.request.DatabaseConfigUpdateRequest;
import com.spendTracker.app.response.DatabaseConfigDetails;
import com.spendTracker.app.utils.CommonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    public List<DatabaseConfigDetails> getDataBaseConfig() {
        List<DatabaseConfigDetails> databaseConfigDetailsList = new ArrayList<>();
        List<TenantDatabaseConfig> response = tenantDatabaseConfigRepository.findAll();
        response.forEach(element -> {
            DatabaseConfigDetails databaseConfigDetails = new DatabaseConfigDetails();
            BeanUtils.copyProperties(element, databaseConfigDetails);
            databaseConfigDetailsList.add(databaseConfigDetails);
        });
        return databaseConfigDetailsList;
    }

    public DatabaseConfigDetails getDataBaseConfigById(String id) {
        Optional<TenantDatabaseConfig> tenantDatabaseConfig = tenantDatabaseConfigRepository.findById(id);
        if (!tenantDatabaseConfig.isPresent()) {
            throw new ValidationException(HttpStatus.BAD_REQUEST.value(), "configuration does not exists for id::" + id);
        }
        TenantDatabaseConfig tenantDatabaseConfigDetails = tenantDatabaseConfig.get();
        DatabaseConfigDetails databaseConfigDetails = new DatabaseConfigDetails();
        BeanUtils.copyProperties(tenantDatabaseConfigDetails, databaseConfigDetails);
        return databaseConfigDetails;
    }

    public void deleteByIdDataBaseConfig(String id) {
        Optional<TenantDatabaseConfig> tenantDatabaseConfig = tenantDatabaseConfigRepository.findById(id);
        if (tenantDatabaseConfig.isPresent()) {
            tenantDatabaseConfigRepository.deleteById(id);
        } else {
            throw new ValidationException(HttpStatus.BAD_REQUEST.value(), "configuration does not exists for id::" + id);
        }
    }

    public void updateDataBaseConfig(DatabaseConfigUpdateRequest databaseConfigUpdateRequest, String id) {
        Optional<TenantDatabaseConfig> tenantDatabaseConfig = tenantDatabaseConfigRepository.findById(id);
        TenantDatabaseConfig tenantDatabaseConfigDetails = tenantDatabaseConfig.get();
        if (Objects.isNull(tenantDatabaseConfig)) {
            throw new ValidationException(HttpStatus.BAD_REQUEST.value(), "configuration does not exists for id::" + id);
        }
        if (!(tenantDatabaseConfigDetails.getServer().equals(databaseConfigUpdateRequest.getServer()))) {
            tenantDatabaseConfigDetails.setServer(databaseConfigUpdateRequest.getServer());
        }
        if (!(tenantDatabaseConfigDetails.getPort().equals(databaseConfigUpdateRequest.getPort()))) {
            tenantDatabaseConfigDetails.setPort(databaseConfigUpdateRequest.getPort());
        }
        if (!(tenantDatabaseConfigDetails.getDatabaseName().equals(databaseConfigUpdateRequest.getDatabaseName()))) {
            tenantDatabaseConfigDetails.setDatabaseName(databaseConfigUpdateRequest.getDatabaseName());
        }
        if (!(tenantDatabaseConfigDetails.getUsername().equals(databaseConfigUpdateRequest.getUsername()))) {
            tenantDatabaseConfigDetails.setUsername(databaseConfigUpdateRequest.getUsername());
        }
        if (!(tenantDatabaseConfigDetails.getPassword().equals(databaseConfigUpdateRequest.getPassword()))) {
            tenantDatabaseConfigDetails.setPassword(databaseConfigUpdateRequest.getPassword());
        }
        CommonUtils.setUpdateEntityFields(tenantDatabaseConfig);
        tenantDatabaseConfigRepository.save(tenantDatabaseConfigDetails);
    }
}
