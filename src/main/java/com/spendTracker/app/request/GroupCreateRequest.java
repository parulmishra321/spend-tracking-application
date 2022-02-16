package com.spendTracker.app.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class GroupCreateRequest {
    @NotBlank(message = "pattern.ConnectorReq.name")
    @Pattern(regexp="^[a-zA-Z0-9]([\\w]*[a-zA-Z0-9]?$)",message="pattern.ConnectorReq.name")
    private String name;
    private String description;

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
}
