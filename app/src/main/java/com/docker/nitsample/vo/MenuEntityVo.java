package com.docker.nitsample.vo;

import android.databinding.BaseObservable;

import java.io.Serializable;

public class MenuEntityVo extends BaseObservable implements Serializable {

/*
*  "linkageid": "3472",
        "parentid": "0",
        "name": "闲置物品",
        "img": "\/var\/upload\/image\/2019\/08\/2019082318080725276_80x80.png",
        "recommend": "0"
* */

    public String linkageid;
    public String parentid;
    public String name;
    public String img;
    public String recommend;

    public String getName() {
        return name;
    }

}
