package com.bfhd.circle.vo;

import android.databinding.BaseObservable;

import java.io.Serializable;

public class BaseModelVo<T> extends BaseObservable implements Serializable {


    public T resource;

    public T getResource() {
        return resource;
    }

    public void setResource(T resource) {
        this.resource = resource;
    }
}
