package com.bfhd.account.vo.index;

import android.view.View;

import com.bfhd.account.R;
import com.docker.common.common.model.OnItemClickListener;
import com.docker.common.common.vo.card.BaseCardVo;

public class AccountIndexItemVo extends BaseCardVo<String> {

    public AccountIndexItemVo(int style, int position) {
        super(style, position);
        maxSupport = 2;
    }

    @Override
    public int getItemLayout() {
        int lay = R.layout.account_fragment_mine_index_item;
        switch (style) {
            case 0:
                lay = R.layout.account_fragment_mine_index_menu;

                break;
            case 1:
                lay = R.layout.account_fragment_mine_index_item;
                break;
        }
        return lay;
    }

    @Override
    public void onItemClick(BaseCardVo item, View view) {

        if (view.getId() == R.id.tv_isent) {
        }
        if (view.getId() == R.id.tv_ipay) {
        }
        if (view.getId() == R.id.tv_icollec) {
        }
        if (view.getId() == R.id.tv_iattend) {
        }
        if (view.getId() == R.id.tv_imoney_box) {
        }
        if (view.getId() == R.id.tv_ihome) {
        }
        if (view.getId() == R.id.tv_point) {
        }
        if (view.getId() == R.id.tv_help) {
        }
    }

}
