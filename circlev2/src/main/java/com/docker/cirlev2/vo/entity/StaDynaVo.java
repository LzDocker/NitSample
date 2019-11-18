package com.docker.cirlev2.vo.entity;

import android.databinding.ObservableBoolean;

import java.io.Serializable;
import java.util.HashMap;

public class StaDynaVo implements Serializable {


    /*
     * 使用的地方
     * */
    public int ScopeType = 0;

    /*
     * 1 外部控制刷新
     * 2 内部自己控制
     * 3 相关推荐 无数据的时候隐藏自己
     * */
    public int UiType = 1;

    /**
     * 1 固定url
     * <p>
     * 2 后端传递urlapi
     */
    public int ReqType = 1;

    // 请求参数
    public HashMap<String, String> ReqParam = new HashMap<>();

    // 开启fragment 所需的额外参数
    public HashMap<String, Object> ExtensParam = new HashMap<>();


    public String ApiUrl;

    //
    public ObservableBoolean status = new ObservableBoolean();

    // 界面可见是否自动加载数据
    public boolean isVisibleLoad = true;


    public int special = 0;

}
