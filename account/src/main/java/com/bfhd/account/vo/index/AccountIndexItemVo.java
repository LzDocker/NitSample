package com.bfhd.account.vo.index;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.bfhd.account.R;
import com.bfhd.account.vo.MyInfoVo;
import com.bfhd.circle.BR;
import com.docker.common.common.model.BaseItemModel;
import com.docker.common.common.model.OnItemClickListener;

public class AccountIndexItemVo extends BaseObservable implements BaseItemModel {

    @Override
    public int getItemLayout() {
        return R.layout.account_fragment_mine_index_item;
    }

    @Override
    public OnItemClickListener getOnItemClickListener() {
        return (item, view) -> {
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
        };
    }
}
