package com.docker.cirlev2.vo.entity;

import android.databinding.BaseObservable;

import java.io.Serializable;

public class CircleInfo extends BaseObservable implements Serializable {


    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    private String info;

}
