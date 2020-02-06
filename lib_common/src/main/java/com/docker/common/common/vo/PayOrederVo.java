package com.docker.common.common.vo;

import java.io.Serializable;
import java.util.ArrayList;

public class PayOrederVo implements Serializable {


    /*
    * "sysSn": "JBTS19052914025311480",
    "payPrice": "11.98",
    "title": "警报推送",
    'alipayStr':=' 支付宝支付串'*/
    public String sysSn;
    public String payPrice;
    public String payTotal;
    public String orderId;
    public String title;
    public String alipayStr;

    public ArrayList<String> goodsid;

}
