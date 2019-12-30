package com.bfhd.account.vo.tygs;

import android.view.View;

import com.bfhd.account.R;
import com.bfhd.account.vo.MyInfoVo;
import com.docker.common.common.vo.card.BaseCardVo;

public class AccountPointHeadVo extends BaseCardVo<MyInfoVo> {

    private String point;

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public AccountPointHeadVo(int style, int position) {
        super(style, position);


    }

    @Override
    public int getItemLayout() {
        return R.layout.account_mine_point_head;
    }

    @Override
    public void onItemClick(BaseCardVo item, View view) {
        if (view.getId() == R.id.tv_rule) {//积分规则

        }

    }

}
