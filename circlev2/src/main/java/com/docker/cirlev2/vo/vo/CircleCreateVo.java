package com.docker.cirlev2.vo.vo;

import com.docker.cirlev2.R;
import com.docker.common.common.model.BaseSampleItem;
import com.docker.common.common.model.OnItemClickListener;

import java.io.Serializable;

public class CircleCreateVo extends BaseSampleItem implements Serializable {


    public int flag;

    public CircleCreateVo(int flag) {
        this.flag = flag;
    }

    @Override
    public int getItemLayout() {

        int lay = R.layout.circlev2_item_create_company;
        switch (flag) {
            case 1:
                lay = R.layout.circlev2_item_create_company;
                break;
            case 2:
                lay = R.layout.circlev2_item_create_active;
                break;
            case 3:
                lay = R.layout.circlev2_item_create_country;
                break;
        }
        return lay;
    }

    @Override
    public OnItemClickListener getOnItemClickListener() {
        return null;
    }

    /**
     * memberid :
     * uuid :
     * type :
     * classid :
     * logoUrl :
     * classid1 :
     * circleName :
     * fullName :
     * surfaceImg :
     * intro :
     * linkman :
     * contact :
     * address :
     * lng :
     * lat :
     * companyName :
     * card :
     * job :
     * goodsname :
     * bookclass :
     * bookclass2 :
     * issetdef :
     */

    public String memberid;
    public String uuid;
    public String utid;
    public String type;
    public String classid;
    public String logoUrl;
    public String classid1;
    public String circleName;
    public String fullName;
    public String surfaceImg;
    public String intro;
    public String linkman;
    public String contact;
    public String address;
    public String lng;
    public String lat;
    public String companyName;
    public String card;
    public String job;
    public String goodsname;
    public String bookclass;
    public String bookclass2;
    public String issetdef;


}
