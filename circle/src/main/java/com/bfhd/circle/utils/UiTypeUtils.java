package com.bfhd.circle.utils;
import com.docker.common.common.command.ReplyCommand;
public class UiTypeUtils {

    public static void processUiType(String type, ReplyCommand succomm, ReplyCommand failcomm) {
        switch (type) {
            case "car":
            case "time":
            case "datetime":
            case "product":
            case "project":
            case "house":
            case "news":
            case "dynamic":
            case "goods":
                succomm.exectue();
                break;
            default:
                failcomm.exectue();
                break;
        }
    }

}
