package com.docker.cirlev2.vo.pro;

import com.docker.cirlev2.R;
import com.docker.cirlev2.vo.pro.base.ExtDataBase;

import java.util.ArrayList;

public class Goods extends ExtDataBase {


    public String point;

    public String price;

    public String name;

    @Override
    public int getItemLayout() {
        int lay = 0;
        if (this.parent.scope == 0) {
            lay = R.layout.circlev2_item_dynamic_goodsv2;
        } else {
            lay = R.layout.circlev2_item_dynamic_goodsv3;
        }
        return lay;
    }
}
