package com.flex83.app.utils;


import com.flex83.app.entities.ParentEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.UUID;

public final class CommonUtils {
    private static final Logger LOG = LogManager.getLogger();

    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static void setCreateEntityFields(Object obj) {
        if (obj instanceof ParentEntity) {
            ParentEntity entity = (ParentEntity) obj;
            entity.setCreatedAt(new Date());
        }
    }

    public static void setUpdateEntityFields(Object obj) {
        if (obj instanceof ParentEntity) {
            ParentEntity entity = (ParentEntity) obj;
            entity.setUpdatedAt(new Date());
        }
    }


}
