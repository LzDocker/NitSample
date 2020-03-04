package com.docker.common.common.utils.annotation;

import android.util.Log;

import java.lang.reflect.Method;

public class CustomModeParser {

    public static void inject(Object o) {
        Log.d("sss", "inject: ===========================");
        injectIntercpt(o);
    }

    public static void injectIntercpt(Object o) {
        Class<? extends Object> clazz = o.getClass();
        Method[] demoMethod = clazz.getMethods();
        for (Method method : demoMethod) {
            if (method.isAnnotationPresent(CustomModeAnnotation.class)) {
                CustomModeAnnotation annotationInfo = method.getAnnotation(CustomModeAnnotation.class);
                Log.d("sss", "injectIntercpt: ================method===="+method);
                Log.d("sss", "injectIntercpt: ================annotationInfo.version()===="+annotationInfo.version());
                System.out.println("method: " + method);
                System.out.println("name= " + annotationInfo.version());
            }
        }

    }


}
