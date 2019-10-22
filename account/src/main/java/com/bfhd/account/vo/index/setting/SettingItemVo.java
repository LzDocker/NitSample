package com.bfhd.account.vo.index.setting;

import android.databinding.BaseObservable;

import com.bfhd.account.R;
import com.docker.common.common.model.BaseItemModel;
import com.docker.common.common.model.OnItemClickListener;

public class SettingItemVo extends BaseObservable implements BaseItemModel {

    @Override
    public int getItemLayout() {
        return R.layout.account_fragment_mine_setting_item;
    }

    @Override
    public OnItemClickListener getOnItemClickListener() {
        return null;
    }
}
