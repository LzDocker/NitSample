package com.docker.common.common.vo.entity;

import android.databinding.BaseObservable;

import java.io.Serializable;

public class ResourceBean extends BaseObservable implements Serializable {

    @Override
    public String toString() {
        return "{" +
                "t:'" + t + '\'' +
                ", img:'" + img + '\'' +
                ", url:'" + url + '\'' +
                ", sort:'" + sort + '\'' +
                '}';
    }

    private String parentid;  // dynamicID

    private int t;
    private String img;
    private String url;
    private String sort;


    public int getT() {
        return t;
    }

    public void setT(int t) {
        this.t = t;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }
}