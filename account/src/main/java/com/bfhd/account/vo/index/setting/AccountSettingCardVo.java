package com.bfhd.account.vo.index.setting;

import android.view.View;

import com.bfhd.account.R;
import com.docker.common.common.vo.card.BaseCardVo;

public class AccountSettingCardVo extends BaseCardVo<String> {

    public AccountSettingCardVo(int style, int position) {
        super(style, position);
        maxSupport = 1;
    }

    @Override
    public int getItemLayout() {
        return R.layout.account_mine_setting_card;
    }


    @Override
    public void onItemClick(BaseCardVo item, View view) {

    }
}
