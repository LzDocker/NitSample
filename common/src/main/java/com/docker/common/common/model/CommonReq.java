package com.docker.common.common.model;

import java.io.Serializable;
import java.util.HashMap;

// 加载 commonlistfragment  入参
public class CommonReq implements Serializable {

    /*
     *
     *   0 禁用smart
     *
     *   1  smartc纯滚动
     * */
    public int refreshState;


    // 请求参数
    public HashMap<String, String> ReqParam = new HashMap<>();


    /*
     *  get 请求必传
     * */
    public String ApiUrl;


}
