package com.spendTracker.app.utils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class ValidationUtils {

    public static boolean isNullOrEmpty(Map map) {
        return Objects.isNull(map) || map.isEmpty();
    }

    public static boolean nonNullOrEmpty(Map map) {
        return !isNullOrEmpty(map);
    }

    public static boolean isNullOrEmpty(List list) { return Objects.isNull(list) || list.isEmpty(); }

    public static boolean nonNullOrEmpty(List list) {
        return !isNullOrEmpty(list);
    }

    public static boolean isNullOrEmpty(String string) {
        return Objects.isNull(string) || string.isEmpty();
    }

    public static boolean nonNullOrEmpty(String string) {
        return !isNullOrEmpty(string);
    }
}
