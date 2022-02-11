package com.flex83.app.bussiness;

import com.flex83.app.exception.ValidationException;
import com.flex83.app.request.DeviceCreateRequest;
import com.flex83.app.request.DeviceFilter;
import com.flex83.app.response.DeviceDetails;
import com.flex83.app.services.MongoDBService;
import com.flex83.app.utils.CommonUtils;
import com.flex83.app.utils.GenerateUtils;
import com.flex83.app.utils.ValidationUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import static com.flex83.app.constant.MongoDBConstants.*;

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
        projection.put(_ID, 0);

        if(ValidationUtils.isNullOrEmpty(mongoDBService.findOne(DEVICE_TYPE_COLLECTION, deviceTypeQuery, projection))){
            throw new ValidationException(HttpStatus.BAD_REQUEST.value(), "DeviceTypeID doest not exists");
        }
        Document groupQuery = new Document();
        groupQuery.put("id", deviceCreateRequest.getGroupId());

        if(ValidationUtils.isNullOrEmpty(mongoDBService.findOne(GROUPS_COLLECTION, groupQuery, projection))){
            throw new ValidationException(HttpStatus.BAD_REQUEST.value(), "Group ID does not exists");
        }
        Document query = new Document();
        query.put("name", deviceCreateRequest.getName());

        Document result = mongoDBService.findOne(DEVICES_COLLECTION, query, projection);
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
        mongoDBService.create(DEVICES_COLLECTION, deviceDetails);
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
        projection.put(_ID, 0);
        List<Document> result = mongoDBService.findList(DEVICES_COLLECTION, query, projection);
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
        projection.put(_ID, 0);
        Document result = mongoDBService.findOne(DEVICES_COLLECTION, query, projection);
        if (Objects.isNull(result) || result.isEmpty()) {
            throw new ValidationException(HttpStatus.BAD_REQUEST.value(), "Device details does not exists");
        }
        return result;
    }

    public void deleteById(String id) {
        Document query = new Document();
        query.put("id", id);
        Document projection = new Document();
        projection.put(_ID, 0);
        Document result = mongoDBService.findOne(DEVICES_COLLECTION, query, projection);
        if (Objects.isNull(result) || result.isEmpty()) {
            throw new ValidationException(HttpStatus.BAD_REQUEST.value(), "Device details does not exists");
        }
        mongoDBService.deleteOne(DEVICES_COLLECTION, query);
    }

    public void updateById(DeviceCreateRequest deviceCreateRequest, String id) {
        Document query = new Document();
        query.put("id",id);
        Document projection = new Document();
        projection.put(_ID, 0);
        Document result = mongoDBService.findOne(DEVICES_COLLECTION, query, projection);
        if (Objects.isNull(result) || result.isEmpty()) {
            throw new ValidationException(HttpStatus.BAD_REQUEST.value(), "Device details does not exists");
        }

        Document set = new Document();
        if (!result.getString("name").equalsIgnoreCase(deviceCreateRequest.getName())) {
            Document deviceName = new Document();
            deviceName.put("name", deviceCreateRequest.getName());
            Document deviceInfo = mongoDBService.findOne(DEVICES_COLLECTION, deviceName, projection);
            if (ValidationUtils.nonNullOrEmpty(deviceInfo)) {
                throw new ValidationException(HttpStatus.BAD_REQUEST.value(), "Device name already exists");
            }
            set.put("name",deviceCreateRequest.getName());
        }
        if (!result.getString("deviceTypeId").equalsIgnoreCase(deviceCreateRequest.getDeviceTypeId())) {
            Document queryDeviceTypeId = new Document();
            queryDeviceTypeId.put("deviceTypeId", deviceCreateRequest.getDeviceTypeId());
            Document deviceInfo = mongoDBService.findOne(DEVICE_TYPE_COLLECTION, queryDeviceTypeId, projection);
            if (ValidationUtils.isNullOrEmpty(deviceInfo)) {
                throw new ValidationException(HttpStatus.BAD_REQUEST.value(), "Device does not exists with this Device Type ID");
            }
            set.put("deviceTypeId",deviceCreateRequest.getDeviceTypeId());
        }
        if (!result.getString("groupId").equalsIgnoreCase(deviceCreateRequest.getGroupId())) {
            Document queryGroupId = new Document();
            queryGroupId.put("groupId", deviceCreateRequest.getGroupId());
            Document deviceInfo = mongoDBService.findOne(GROUPS_COLLECTION, queryGroupId, projection);
            if (ValidationUtils.isNullOrEmpty(deviceInfo)) {
                throw new ValidationException(HttpStatus.BAD_REQUEST.value(), "Device does not exists with this Group ID");
            }
            set.put("groupId",deviceCreateRequest.getGroupId());
        }
        set.put("description", ValidationUtils.nonNullOrEmpty(deviceCreateRequest.getDescription())? deviceCreateRequest.getDescription():result.getString("description"));
        mongoDBService.update(DEVICES_COLLECTION, new Document(ID,id), set);
    }
}