package com.docker.cirlev2.vo.entity;

import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import java.io.Serializable;
import java.util.List;

public class CircleDynamicVo extends BaseObservable implements Serializable {


    private String name;

    private String info;

    private int type;

    private List<String> imglist;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<String> getImglist() {
        return imglist;
    }

    public void setImglist(List<String> imglist) {
        this.imglist = imglist;
        this.imgItemsVO.addAll(imglist);
        if (imgItemsVO != null && imgItemsVO.size() > 1) {
            imgItems.addAll(imgItemsVO.subList(0, imgItemsVO.size() > 3 ? 3 : imgItemsVO.size()));
        }
    }

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

    public final ObservableList<String> imgItemsVO = new ObservableArrayList<>();

    // imgItemsVO.subList(0,3)
    public final ObservableList<String> imgItems = new ObservableArrayList<>();


}
