package com.spendTracker.app.request;

import com.sun.xml.bind.v2.schemagen.xmlschema.Schema;

public class PublishAcl {
    private String id;
    private String topic;
    private String schema;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }
}
