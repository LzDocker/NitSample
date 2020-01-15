package com.docker.common.common.config;

import com.dcbfhd.utilcode.utils.GsonUtils;

import java.io.Serializable;
import java.util.HashMap;

public class ApiCommonParam implements Serializable {

    public String field;

    public HashMap<String, String[]> where = new HashMap<>();

    public HashMap<String, String> order = new HashMap<>();

    public HashMap<String, ApiCommonParam> extData = new HashMap<>();

    public static String parseParam(ApiCommonParam apiCommonParam) {
        return GsonUtils.toJson(apiCommonParam);
    }
}
