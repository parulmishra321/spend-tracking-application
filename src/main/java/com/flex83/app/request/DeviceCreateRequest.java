package com.flex83.app.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class DeviceCreateRequest {
    @NotBlank(message = "pattern.ConnectorReq.name")
    @Pattern(regexp="^[a-zA-Z0-9]([\\w]*[a-zA-Z0-9]?$)",message="pattern.ConnectorReq.name")
    @NotNull(message = "pattern.ConnectorReq.name")
    private String name;
    private String description;
    private String deviceTypeId;
    private String groupId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(String deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
