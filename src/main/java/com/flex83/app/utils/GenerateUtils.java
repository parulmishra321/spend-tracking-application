package com.flex83.app.utils;

import com.flex83.app.annotation.DocumentIgnore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;

@Component
public class GenerateUtils<T, U> {
    private static final Logger LOG = LogManager.getLogger();

    public U generateFrom(T request, U data) {
        CommonUtils.setCreateEntityFields(data);
        return updateFrom(request, data);
    }

    public U updateFrom(T request, U data) {
        Field[] requestFields = request.getClass().getDeclaredFields();
        for (Field field : requestFields) {
            if (!field.getAnnotatedType().isAnnotationPresent(DocumentIgnore.class)) {
                try {
                    field.setAccessible(true);
                    if (Objects.nonNull(field.get(request))) {
                        if (data.getClass().isAssignableFrom(Document.class) || data.getClass().isAssignableFrom(Map.class)) {
                            ((Document) data).put(field.getName(), field.get(request));
                        } else {
                            Field dataField = data.getClass().getDeclaredField(field.getName());
                            dataField.setAccessible(true);
                            dataField.set(data, field.get(request));
                        }
                    }

                } catch (IllegalAccessException e) {
                    LOG.error(" Illegal Access - " + e.getMessage());
                } catch (NoSuchFieldException e) {
                    LOG.error(" No such Field - " + e.getMessage());
                }
            }
        }
        return data;
    }
}
