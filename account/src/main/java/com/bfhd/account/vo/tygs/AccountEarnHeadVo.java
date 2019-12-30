package com.bfhd.account.vo.tygs;

import android.view.View;

import com.bfhd.account.R;
import com.bfhd.account.vo.MyInfoVo;
import com.docker.common.common.vo.card.BaseCardVo;

public class AccountEarnHeadVo extends BaseCardVo<MyInfoVo> {

    private String point;

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public AccountEarnHeadVo(int style, int position) {
        super(style, position);


    }

    @Override
    public int getItemLayout() {
        return R.layout.account_mine_earn_head;
    }

    @Override
    public void onItemClick(BaseCardVo item, View view) {
        if (view.getId() == R.id.tv_rule) {//收益规则

        }

        if (view.getId() == R.id.lin_tx) {//提现

        }


    }

}
