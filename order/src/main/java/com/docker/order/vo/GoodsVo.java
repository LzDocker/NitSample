package com.docker.order.vo;

import com.docker.common.common.model.BaseSampleItem;
import com.docker.common.common.model.OnItemClickListener;
import com.docker.order.R;

public class GoodsVo extends BaseSampleItem {
    @Override
    public int getItemLayout() {
        return R.layout.pro_order_item_goods;
    }

    @Override
    public OnItemClickListener getOnItemClickListener() {
        return null;
    }


    /*
    *  "goodsid": "27",
            "orderid": "913",
            "goodsName": "令狐黔冲 · 酱香15年",
            "goodsNum": "1",
            "goodsImg": "static/var/upload/image/2020/01/2020011710241957423_996x998.png",
            "goodsPrice": "699.00",
            "iscomment": "0",
            "dynamicid": "1274"
    * */

    public String goodsid;
    public String orderid;
    public String goodsName;
    public String goodsNum;
    public String goodsImg;
    public String goodsPrice;
    public int iscomment;

    public String dynamicid;
    public String circleid;
    public String utid;
    public String push_memberid;
    public String push_uuid;

    /*
    * "dynamicid":"1271",
"circleid":"64",
"utid":"be49f2cb2627965610be332a4476286c",
"push_memberid":"66",
"push_uuid":"8f08e38dfae8afc5913cd4713eabb0e2"
    * */
}
