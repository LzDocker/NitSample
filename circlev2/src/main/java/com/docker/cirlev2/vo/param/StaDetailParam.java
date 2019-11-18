package com.docker.cirlev2.vo.param;

import java.io.Serializable;

// 进入详情需要的参数
public class StaDetailParam implements Serializable {

    public String dynamicId;
    /*
     * 0 动态
     * 2 问答
     * 1 h5标签
     * 3 weburl
     * */
    public int uiType = 0;

    // 类型
    public String type = "event";

    // 是否需要推荐
    public boolean isRecomend = false;

    public int speical = -1;

}
