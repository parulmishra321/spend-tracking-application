package com.spendTracker.app.request;

import java.util.List;

public class DeviceFilter {
    private List<String> deviceTypeIds;
    private List<String> deviceGroupIds;

    public List<String> getDeviceTypeIds() {
        return deviceTypeIds;
    }

    public void setDeviceTypeIds(List<String> deviceTypeIds) {
        this.deviceTypeIds = deviceTypeIds;
    }

    public List<String> getDeviceGroupIds() {
        return deviceGroupIds;
    }

    public void setDeviceGroupIds(List<String> deviceGroupIds) {
        this.deviceGroupIds = deviceGroupIds;
    }
}
