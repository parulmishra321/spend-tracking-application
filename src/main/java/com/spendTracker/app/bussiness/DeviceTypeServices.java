package com.spendTracker.app.bussiness;

import com.spendTracker.app.exception.ValidationException;
import com.spendTracker.app.request.DeviceTypeCreateRequest;
import com.spendTracker.app.response.DeviceTypeDetails;
import com.spendTracker.app.services.MongoDBService;
import com.spendTracker.app.utils.CommonUtils;
import com.spendTracker.app.utils.GenerateUtils;
import com.spendTracker.app.utils.ValidationUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.spendTracker.app.constant.MongoDBConstants.DEVICE_TYPE_COLLECTION;
import static com.spendTracker.app.constant.MongoDBConstants._ID;

@Service
public class DeviceTypeServices {
    @Autowired
    private MongoDBService mongoDBService;
    @Autowired
    private GenerateUtils generateUtils;

    public void createDeviceType(DeviceTypeCreateRequest createRequest) {
        Document query = new Document();
        query.put("name", createRequest.getName());
        Document projection = new Document();
        projection.put(_ID, 0);
        Document check = mongoDBService.findOne(DEVICE_TYPE_COLLECTION, query, projection);
        if (Objects.nonNull(check) && !check.isEmpty()) {
            throw new ValidationException(HttpStatus.BAD_REQUEST.value(), "Device type by this name exists");
        }
        Document deviceDetails = new Document();
        deviceDetails.put("id", CommonUtils.generateUUID());
        generateUtils.generateFrom(createRequest,deviceDetails);
        mongoDBService.create(DEVICE_TYPE_COLLECTION, deviceDetails);

    }

    public List<DeviceTypeDetails> getAllDeviceType() {
        List<DeviceTypeDetails> result = new ArrayList<>();
        Document query = new Document();
        Document projection = new Document();
        projection.put(_ID, 0);
        List<Document> data = mongoDBService.findList(DEVICE_TYPE_COLLECTION, query, projection);
        data.forEach(ele -> {
            DeviceTypeDetails deviceTypeDetails = new DeviceTypeDetails();
            deviceTypeDetails.setId(ele.getString("id"));
            deviceTypeDetails.setName(ele.getString("name"));
            deviceTypeDetails.setDescription(ele.getString("description"));
            deviceTypeDetails.setReportInterval(ele.getString("reportInterval"));
            result.add(deviceTypeDetails);
        });
        return result;
    }

    public DeviceTypeDetails getDeviceTypeById(String deviceId) {
        Document query = new Document();
        query.put("id", deviceId);
        Document projection = new Document();
        projection.put(_ID, 0);
        Document getDeviceType = mongoDBService.findOne(DEVICE_TYPE_COLLECTION, query, projection);
        if (Objects.isNull(getDeviceType) || getDeviceType.isEmpty()) {
            throw new ValidationException(HttpStatus.BAD_REQUEST.value(), "device type doesn't exist");
        }
        DeviceTypeDetails deviceTypeDetails = new DeviceTypeDetails();
        deviceTypeDetails.setId(getDeviceType.getString("id"));
        deviceTypeDetails.setName(getDeviceType.getString("name"));
        deviceTypeDetails.setDescription(getDeviceType.getString("description"));
        deviceTypeDetails.setReportInterval(getDeviceType.getString("reportInterval"));
        return deviceTypeDetails;
    }

    public void deleteDeviceTypeById(String deviceId) {
        Document query = new Document();
        query.put("id", deviceId);
        mongoDBService.deleteOne(DEVICE_TYPE_COLLECTION, query);
    }

    public void updateDeviceTypeById(String deviceId, DeviceTypeCreateRequest deviceTypeCreateRequest) {
        Document query = new Document();
        query.put("id", deviceId);
        Document projection = new Document();
        projection.put(_ID, 0);

        Document getDeviceType = mongoDBService.findOne(DEVICE_TYPE_COLLECTION,query,projection);
        if (Objects.isNull(getDeviceType) || getDeviceType.isEmpty()) {
            throw new ValidationException(HttpStatus.BAD_REQUEST.value(), "device type doesn't exist");
        }
        Document updateDeviceType = new Document();
        if (!getDeviceType.getString("name").equals(deviceTypeCreateRequest.getName())) {
            Document params = new Document();
            params.put("name", deviceTypeCreateRequest.getName());
            Document deviceDetail = mongoDBService.findOne(DEVICE_TYPE_COLLECTION,params,projection);
            if (ValidationUtils.nonNullOrEmpty(deviceDetail)) {
                throw new ValidationException(HttpStatus.BAD_REQUEST.value(), "device type exists with this name");
            }
            updateDeviceType.put("name", deviceTypeCreateRequest.getName());
        }
        updateDeviceType.put("description", ValidationUtils.nonNullOrEmpty(deviceTypeCreateRequest.getDescription())? deviceTypeCreateRequest.getDescription():getDeviceType.getString("description"));
        updateDeviceType.put("reportInterval", ValidationUtils.nonNullOrEmpty(deviceTypeCreateRequest.getReportInterval())? deviceTypeCreateRequest.getReportInterval():getDeviceType.getString("reportInterval"));
        mongoDBService.update(DEVICE_TYPE_COLLECTION, query, updateDeviceType);
    }
}
