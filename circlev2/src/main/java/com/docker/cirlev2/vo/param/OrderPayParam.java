package com.docker.cirlev2.vo.param;

import java.io.Serializable;

// 进入订单详情页面需要传递的参数
public class OrderPayParam implements Serializable {

    public String circleid;
    public String utid;
    public String t;
    public String total_price;
    public String dynamicid;
    public String shoptype;
    public String orderid;


}
