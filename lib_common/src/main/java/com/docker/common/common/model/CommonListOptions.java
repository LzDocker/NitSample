package com.docker.common.common.model;

import com.docker.common.common.config.Constant;

import java.io.Serializable;
import java.util.HashMap;

// 加载 commonlistfragment  入参
public class CommonListOptions implements Serializable {

    /*
     *   0  内部控制 （多用在 viewpager 嵌套）
     *
     *   1  禁用刷新 启用加载更多 （多用在首页 详情页）
     *
     *   2  禁用smart
     *
     *   3  smartc纯滚动
     * */
    public int refreshState = Constant.KEY_REFRESH_PURSE;


    // 请求参数
    public HashMap<String, String> ReqParam = new HashMap<>();


    //recycleview 布局管理器
    /*
     *  0  线性
     *  1  流式布局
     * */
    public int RvUi = Constant.KEY_RVUI_LINER;


    /*
     *  后端传递url的时候，必传
     * */
    public String ApiUrl;


    /*
     * flag  标记coutainercommand
     * */
    public int falg = 0;


    // 参数
    public HashMap<String, Object> externs = new HashMap<>();


    // card 或 list fragment 使用的父容器是否是activity
    public boolean isActParent = false;
}