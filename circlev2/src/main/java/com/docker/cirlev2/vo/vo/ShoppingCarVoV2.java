package com.docker.cirlev2.vo.vo;

import android.view.View;

import com.docker.cirlev2.R;
import com.docker.common.common.vo.card.BaseCardVo;

public class ShoppingCarVoV2 extends BaseCardVo {


    public ShoppingCarVoV2(int style, int position) {
        super(style, position);
    }

    @Override
    public void onItemClick(BaseCardVo item, View view) {

    }

    @Override
    public int getItemLayout() {
        return R.layout.circlev2_item_shopping_car;
    }


    /*
    *
    * "id": "1",
            "inputtime": "1579426470",
            "goodsid": "25",
            "skuId": "0",
            "goodsName": "无忌智达·酱香30年",
            "picture": "static/var/upload/image/2020/01/2020011517022392900_344x344.png",
            "price": "1688.00",
            "num": "3",
            "memberid": "66"
    * */


    public String id;
    public String inputtime;
    public String goodsid;
    public String skuId;
    public String goodsName;
    public String picture;
    public String price;
    public String memberid;


    private boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
