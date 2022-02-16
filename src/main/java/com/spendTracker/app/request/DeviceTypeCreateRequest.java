package com.spendTracker.app.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

public class DeviceTypeCreateRequest {
    @NotBlank(message = "pattern.ConnectorReq.name")
    @Pattern(regexp="^[a-zA-Z0-9]([\\w]*[a-zA-Z0-9]?$)",message="pattern.ConnectorReq.name")
    @NotNull(message = "pattern.ConnectorReq.name")
    private String name;
    private String description;
    private String reportInterval;
    private List<PublishAcl> publishAcls;
    private List<SubscribeAcl> subscribeAcls;

    public List<PublishAcl> getPublishAcls() {
        return publishAcls;
    }

    public void setPublishAcls(List<PublishAcl> publishAcls) {
        this.publishAcls = publishAcls;
    }

    public List<SubscribeAcl> getSubscribeAcls() {
        return subscribeAcls;
    }

    public void setSubscribeAcls(List<SubscribeAcl> subscribeAcls) {
        this.subscribeAcls = subscribeAcls;
    }

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

    public String getReportInterval() {
        return reportInterval;
    }

    public void setReportInterval(String reportInterval) {
        this.reportInterval = reportInterval;
    }
}
