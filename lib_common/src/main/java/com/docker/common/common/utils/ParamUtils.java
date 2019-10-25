package com.docker.common.common.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ParamUtils {


    public static Map objectToMap(Object obj, boolean keepNullVal) {
        if (obj == null) {
            return null;
        }
        HashMap<String, String> map = new HashMap<>();
        try {
            Field[] declaredFields = obj.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                if (keepNullVal == true) {
                    map.put(field.getName(), (String) field.get(obj));
                } else {
                    if (field.get(obj) != null && !"".equals(field.get(obj).toString())) {
                        map.put(field.getName(), (String) field.get(obj));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }


    public static Object mapToObject(Map<String, String> map, Object obj, boolean keepNullVal) {
        if (map == null || map.size() == 0) {
            return null;
        }
        try {
            Field[] declaredFields = obj.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                if (keepNullVal == true) {
                    field.set(obj, map.get(field.getName()));
                } else {
                    if (map.get(field.getName()) != null && !"".equals(map.get(field.getName()))) {
                        field.set(obj, map.get(field.getName()));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }


}
