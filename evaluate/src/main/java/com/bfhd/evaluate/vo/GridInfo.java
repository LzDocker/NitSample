package com.bfhd.evaluate.vo;

import android.databinding.BaseObservable;

import java.io.Serializable;

public class GridInfo extends BaseObservable implements Serializable {

    private String count;
    private String info;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
