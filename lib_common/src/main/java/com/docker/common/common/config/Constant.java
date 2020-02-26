package com.docker.common.common.config;

import android.os.Environment;

import com.dcbfhd.utilcode.utils.AppUtils;

public class Constant {
    // 页面传值共用key
    public static final String ParamTrans = "ParamTrans";

    // 页面传值key 单fragment activity
    public static final String ContainerParam = "ContainerParam";

    // 页面传值key 单fragment activity
    public static final String ContainerCommand = "ContainerCommand";

    // 页面传值key nitlistactivity  /  nitfragment
    public static final String CommonListParam = "CommonListParam";


    //--------------------nitcommonlist-------------
    /*
     *    0  内部控制 （多用在 viewpager 嵌套）
     *
     *   1  禁用刷新 启用加载更多 （多用在首页 详情页）
     *
     *   2  禁用smart
     *
     *   3  smartc纯滚动
     * */
    public static final int KEY_REFRESH_OWNER = 0;
    public static final int KEY_REFRESH_ONLY_LOADMORE = 1;
    public static final int KEY_REFRESH_NOUSE = 2;
    public static final int KEY_REFRESH_PURSE = 3;

    //recycleview 布局管理器
    /*
     *  0  线性
     *  1  grid 2  每行两个
     *  2  横向
     * */
    public static final int KEY_RVUI_LINER = 0;
    public static final int KEY_RVUI_GRID2 = 1;
    public static final int KEY_RVUI_HOR = 2;
    public static final int KEY_RVUI_GRID5 = 5;
    public static final int KEY_RVUI_GRID3 = 3;


    //----------------------------end---------------------

    public static String BaseFileFloder = Environment.getExternalStorageDirectory() + "/" + AppUtils.getAppName() + "/chache/";

    public static String BarCommonCodeUrl = "http://www.baidu.com";


    public static String ACTIVE_CONTACT_FALG = "#";


}
