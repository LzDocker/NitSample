package com.bfhd.account.vo.tygs;

import android.view.View;

import com.bfhd.account.R;
import com.bfhd.account.vo.MyInfoVo;
import com.docker.common.common.vo.card.BaseCardVo;
import com.google.gson.annotations.SerializedName;

public class MoneyItemVo extends BaseCardVo<MyInfoVo> {

    public MoneyItemVo(int style, int position) {
        super(style, position);
    }

    @Override
    public void onItemClick(BaseCardVo item, View view) {

    }

    @Override
    public int getItemLayout() {
        return R.layout.account_tygs_item_money;
    }

    public String operation;
    public String title;
    public String money;
    public String dotype;
    public String inputtime;


}
