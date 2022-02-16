package com.spendTracker.app.utils;

import com.spendTracker.app.annotation.DocumentIgnore;
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

    public U generateFrom(T source, U target) {
        CommonUtils.setCreateEntityFields(target);
        return updateFrom(source, target);
    }

    public U updateFrom(T source, U target) {
        Field[] requestFields = source.getClass().getDeclaredFields();
        for (Field field : requestFields) {
            if (!field.getAnnotatedType().isAnnotationPresent(DocumentIgnore.class)) {
                try {
                    field.setAccessible(true);
                    if (Objects.nonNull(field.get(source))) {
                        if (target.getClass().isAssignableFrom(Document.class) || target.getClass().isAssignableFrom(Map.class)) {
                            ((Document) target).put(field.getName(), field.get(source));
                        } else {
                            Field dataField = target.getClass().getDeclaredField(field.getName());
                            dataField.setAccessible(true);
                            dataField.set(target, field.get(source));
                        }
                    }

                } catch (IllegalAccessException e) {
                    LOG.error(" Illegal Access - " + e.getMessage());
                } catch (NoSuchFieldException e) {
                    LOG.error(" No such Field - " + e.getMessage());
                }
            }
        }
        return target;
    }
}
