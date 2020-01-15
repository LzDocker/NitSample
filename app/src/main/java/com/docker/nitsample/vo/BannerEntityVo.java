package com.docker.nitsample.vo;

import android.databinding.BaseObservable;

import java.io.Serializable;

public class BannerEntityVo extends BaseObservable implements Serializable {


/*
* "title": "\u9996\u9875",
        "imgurl": "\/static\/var\/upload\/image\/2019\/09\/2019091111140610623_712x280.png",
        "type": "0",
        "http": "http:\/\/htj.wgc360.com\/index.php?m=default.goods_info&memberid=3&uuid=b9bf86035e086493575dba1e79ebda92&dynamicid=143",
        "dynamicType": "house",
        "dataid": "9",
        "dynamicid": "206"

* */

    public String title;
    public String imgurl;
    public String type;
    public String http;
    public String dynamicType;
    public String dataid;
    public String dynamicid;


}
