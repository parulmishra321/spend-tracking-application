package com.flex83.app.bussiness;

import com.flex83.app.exception.ValidationException;
import com.flex83.app.request.DeviceTypeCreateRequest;
import com.flex83.app.services.MongoDBService;
import com.flex83.app.utils.DeviceTypeUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class DeviceTypeServices {
    @Autowired
    private DeviceTypeUtils deviceTypeUtils;
    @Autowired
    private MongoDBService mongoDBService;

    public void createDeviceType(DeviceTypeCreateRequest createRequest) {
        Document createDevice = deviceTypeUtils.generateFrom(createRequest);
        mongoDBService.create("Device Details", createDevice);

    }

    public List<Document> getAllDeviceType() {
        Document matchEq = new Document();
        Document projection = new Document();
        projection.put("Id",0);
        return mongoDBService.findList("Device Details", matchEq, projection);

    }

    public Document getDeviceTypeById(String deviceId){
        Document query = new Document();
        query.put("id",deviceId);
        Document projection = new Document();
        projection.put("id",0);
        Document getDeviceType = mongoDBService.findOne("Device Details",query,projection);
        if(Objects.isNull(getDeviceType) || getDeviceType.isEmpty()){
            throw new ValidationException(HttpStatus.BAD_REQUEST.value(), "device type doesn't exist");
        }
        return getDeviceType;
    }

    public void deleteDeviceTypeById(String deviceId){
        Document query = new Document();
        query.put("id",deviceId);
        mongoDBService.deleteOne("Device Details",query);
    }

    public void updateDeviceTypeById(String deviceId,DeviceTypeCreateRequest deviceTypeCreateRequest){
        Document query = new Document();
        query.put("id",deviceId);
        Document projection = new Document();
        projection.put("id",0);

        Document getDeviceType = getDeviceTypeById(deviceId);
        if(Objects.isNull(getDeviceType) || getDeviceType.isEmpty()){
            throw new ValidationException(HttpStatus.BAD_REQUEST.value(), "device type doesn't exist");
        }
        Document updateDeviceType = new Document();
         if(!getDeviceType.getString("name").equals(deviceTypeCreateRequest.getName())){
            updateDeviceType.put("name",deviceTypeCreateRequest.getName());
         }
         if(!deviceTypeCreateRequest.getDescription().isEmpty()) {
             updateDeviceType.put("description", deviceTypeCreateRequest.getDescription() );
         }
         else{
             updateDeviceType.put("description",getDeviceType.getString("description"));
         }
         mongoDBService.update("Device Details",query,updateDeviceType);
    }
}
