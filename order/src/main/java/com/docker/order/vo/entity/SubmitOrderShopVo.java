package com.docker.order.vo.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class SubmitOrderShopVo implements Serializable {

    public String circleId;
    public String circleName;
    public String remark;

    public ArrayList<SubmitOrderGoodsVo> goodsarr = new ArrayList<>();
}
