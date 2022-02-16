package com.spendTracker.app.bussiness;

import com.spendTracker.app.exception.ValidationException;
import com.spendTracker.app.request.DeviceCreateRequest;
import com.spendTracker.app.request.DeviceFilter;
import com.spendTracker.app.response.DeviceDetails;
import com.spendTracker.app.services.MongoDBService;
import com.spendTracker.app.utils.CommonUtils;
import com.spendTracker.app.utils.GenerateUtils;
import com.spendTracker.app.utils.ValidationUtils;
import com.spendTracker.app.constant.MongoDBConstants;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class DevicesService {
    @Autowired
    private MongoDBService mongoDBService;
    @Autowired
    private GenerateUtils generateUtils;

    public void createDevices(DeviceCreateRequest deviceCreateRequest) {
        Document deviceTypeQuery = new Document();
        deviceTypeQuery.put("id", deviceCreateRequest.getDeviceTypeId());

        Document projection = new Document();
        projection.put(MongoDBConstants._ID, 0);

        if(ValidationUtils.isNullOrEmpty(mongoDBService.findOne(MongoDBConstants.DEVICE_TYPE_COLLECTION, deviceTypeQuery, projection))){
            throw new ValidationException(HttpStatus.BAD_REQUEST.value(), "DeviceTypeID doest not exists");
        }
        Document groupQuery = new Document();
        groupQuery.put("id", deviceCreateRequest.getGroupId());

        if(ValidationUtils.isNullOrEmpty(mongoDBService.findOne(MongoDBConstants.GROUPS_COLLECTION, groupQuery, projection))){
            throw new ValidationException(HttpStatus.BAD_REQUEST.value(), "Group ID does not exists");
        }
        Document query = new Document();
        query.put("name", deviceCreateRequest.getName());

        Document result = mongoDBService.findOne(MongoDBConstants.DEVICES_COLLECTION, query, projection);
        if (Objects.nonNull(result) && !result.isEmpty()) {
            throw new ValidationException(HttpStatus.BAD_REQUEST.value(), "Device name already exists" + deviceCreateRequest.getName());
        }
        Document deviceDetails = new Document();
        deviceDetails.put("id", CommonUtils.generateUUID());
        deviceDetails.put("name", deviceCreateRequest.getName());
        deviceDetails.put("description", deviceCreateRequest.getDescription());
        deviceDetails.put("timestamp", CommonUtils.getCurrentTimeInMillis());
        deviceDetails.put("deviceTypeId", deviceCreateRequest.getDeviceTypeId());
        deviceDetails.put("groupId", deviceCreateRequest.getGroupId());
        mongoDBService.create(MongoDBConstants.DEVICES_COLLECTION, deviceDetails);
    }

    public List<DeviceDetails> getAllDevices(DeviceFilter deviceFilter){
        List<DeviceDetails> response = new ArrayList<>();
        Document query = new Document();
        if(ValidationUtils.nonNullOrEmpty(deviceFilter.getDeviceTypeIds())) {
            query.put("deviceTypeId", new Document("$in", deviceFilter.getDeviceTypeIds()));
        }
        if(ValidationUtils.nonNullOrEmpty(deviceFilter.getDeviceGroupIds())) {
            query.put("groupId", new Document("$in", deviceFilter.getDeviceGroupIds()));
        }
        Document projection = new Document();
        projection.put(MongoDBConstants._ID, 0);
        List<Document> result = mongoDBService.findList(MongoDBConstants.DEVICES_COLLECTION, query, projection);
        if(ValidationUtils.nonNullOrEmpty(result)) {
            result.forEach(element -> {
                DeviceDetails deviceDetails = new DeviceDetails();
                deviceDetails.setName(element.getString("name"));
                deviceDetails.setDescription(element.getString("description"));
                deviceDetails.setDeviceTypeId(element.getString("deviceTypeId"));
                deviceDetails.setGroupId(element.getString("groupId"));
                response.add(deviceDetails);
            });
        }
        return response;
    }

    public Document getById(String id) {
        Document query = new Document();
        query.put("id", id);
        Document projection = new Document();
        projection.put(MongoDBConstants._ID, 0);
        Document result = mongoDBService.findOne(MongoDBConstants.DEVICES_COLLECTION, query, projection);
        if (Objects.isNull(result) || result.isEmpty()) {
            throw new ValidationException(HttpStatus.BAD_REQUEST.value(), "Device details does not exists");
        }
        return result;
    }

    public void deleteById(String id) {
        Document query = new Document();
        query.put("id", id);
        Document projection = new Document();
        projection.put(MongoDBConstants._ID, 0);
        Document result = mongoDBService.findOne(MongoDBConstants.DEVICES_COLLECTION, query, projection);
        if (Objects.isNull(result) || result.isEmpty()) {
            throw new ValidationException(HttpStatus.BAD_REQUEST.value(), "Device details does not exists");
        }
        mongoDBService.deleteOne(MongoDBConstants.DEVICES_COLLECTION, query);
    }

    public void updateById(DeviceCreateRequest deviceCreateRequest, String id) {
        Document query = new Document();
        query.put("id",id);
        Document projection = new Document();
        projection.put(MongoDBConstants._ID, 0);
        Document result = mongoDBService.findOne(MongoDBConstants.DEVICES_COLLECTION, query, projection);
        if (Objects.isNull(result) || result.isEmpty()) {
            throw new ValidationException(HttpStatus.BAD_REQUEST.value(), "Device details does not exists");
        }

        Document set = new Document();
        if (!result.getString("name").equalsIgnoreCase(deviceCreateRequest.getName())) {
            Document deviceName = new Document();
            deviceName.put("name", deviceCreateRequest.getName());
            Document deviceInfo = mongoDBService.findOne(MongoDBConstants.DEVICES_COLLECTION, deviceName, projection);
            if (ValidationUtils.nonNullOrEmpty(deviceInfo)) {
                throw new ValidationException(HttpStatus.BAD_REQUEST.value(), "Device name already exists");
            }
            set.put("name",deviceCreateRequest.getName());
        }
        if (!result.getString("deviceTypeId").equalsIgnoreCase(deviceCreateRequest.getDeviceTypeId())) {
            Document queryDeviceTypeId = new Document();
            queryDeviceTypeId.put("deviceTypeId", deviceCreateRequest.getDeviceTypeId());
            Document deviceInfo = mongoDBService.findOne(MongoDBConstants.DEVICE_TYPE_COLLECTION, queryDeviceTypeId, projection);
            if (ValidationUtils.isNullOrEmpty(deviceInfo)) {
                throw new ValidationException(HttpStatus.BAD_REQUEST.value(), "Device Type does not exists");
            }
            set.put("deviceTypeId",deviceCreateRequest.getDeviceTypeId());
        }
        if (!result.getString("groupId").equalsIgnoreCase(deviceCreateRequest.getGroupId())) {
            Document queryGroupId = new Document();
            queryGroupId.put("groupId", deviceCreateRequest.getGroupId());
            Document deviceInfo = mongoDBService.findOne(MongoDBConstants.GROUPS_COLLECTION, queryGroupId, projection);
            if (ValidationUtils.isNullOrEmpty(deviceInfo)) {
                throw new ValidationException(HttpStatus.BAD_REQUEST.value(), "Group does not exists");
            }
            set.put("groupId",deviceCreateRequest.getGroupId());
        }
        set.put("description", ValidationUtils.nonNullOrEmpty(deviceCreateRequest.getDescription())? deviceCreateRequest.getDescription():result.getString("description"));
        mongoDBService.update(MongoDBConstants.DEVICES_COLLECTION, new Document(MongoDBConstants.ID,id), set);
    }
}