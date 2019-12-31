package com.docker.cirlev2.ui.detail.home;

import java.io.Serializable;
import java.util.HashMap;

public class CircleConfig implements Serializable {

    /*
     * 模版 非必传
     * */
    public int Temple = 0;


    public String utid;

    /*
     * 圈子id
     * */
    public String circleid;

    /*
     * 圈子类型
     * */
    public String circleType;


    /*
     *
     * 扩展字段  title  影响toobar和沉浸式
     *          fmrouter 应用自己的详情fragment的访问路径（arouter）
     *
     * */
    public HashMap<String, String> extens = new HashMap<>();
}
