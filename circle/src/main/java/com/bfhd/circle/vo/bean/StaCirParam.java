package com.bfhd.circle.vo.bean;

import android.support.annotation.Nullable;

import com.bfhd.circle.vo.ServiceDataBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/*
进入圈子详情 入参
* */
public class StaCirParam implements Serializable {


    // 在圈子详情里发布动态
    public StaCirParam(String circleid, String utid, @Nullable String extentron) {
        this.circleid = circleid;
        this.utid = utid;
        this.extentron = extentron;
    }

    public StaCirParam() {
    }


    public int role = 0;

    private String circleid;

    private String utid;

    /*
     * 扩展字段
     * */
    private String extentron;

    public String extentron2;

    public ArrayList extentronList = new ArrayList();

    public HashMap<String, String> getExtenMap() {
        return extenMap;
    }

    public void setExtenMap(HashMap<String, String> extenMap) {
        this.extenMap = extenMap;
    }


    private HashMap<String, String> extenMap = new HashMap<>();

    public int type;

    public String getCircleid() {
        return circleid;
    }

    public void setCircleid(String circleid) {
        this.circleid = circleid;
    }

    public String getUtid() {
        return utid;
    }

    public void setUtid(String utid) {
        this.utid = utid;
    }

    public String getExtentron() {
        return extentron;
    }

    public void setExtentron(String extentron) {
        this.extentron = extentron;
    }


    public ServiceDataBean serviceDataBean;  // 编辑的时候用到

    public String DataType;  // wj


}
