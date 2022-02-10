package com.flex83.app.utils;

import com.flex83.app.request.DeviceTypeCreateRequest;
import org.bson.Document;
import org.springframework.stereotype.Service;

@Service
public class DeviceTypeUtils {
    public Document generateFrom(DeviceTypeCreateRequest createRequest) {
        Document data = new Document();
        data.put("id", CommonUtils.generateUUID());
        data.put("name", createRequest.getName());
        data.put("description", createRequest.getDescription());
        data.put("reportInterval", createRequest.getReportInterval());
        return data;
    }
}
