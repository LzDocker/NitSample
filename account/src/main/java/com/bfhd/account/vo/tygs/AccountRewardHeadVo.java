package com.bfhd.account.vo.tygs;

import android.databinding.Bindable;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.BR;
import com.bfhd.account.R;
import com.bfhd.account.vm.card.AccountHeadCardViewModel;
import com.bfhd.account.vo.MyInfoVo;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonContainerOptions;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.vo.card.BaseCardVo;

public class AccountRewardHeadVo extends BaseCardVo<MyInfoVo> {


    public AccountRewardHeadVo(int style, int position) {
        super(style, position);
        maxSupport = 3;
        mVmPath = "com.bfhd.account.vm.card.AccountHeadCardViewModel";
    }

    @Override
    public int getItemLayout() {
        return R.layout.account_mine_reward_top;
    }

    @Override
    public void onItemClick(BaseCardVo item, View view) {
        if (view.getId() == R.id.tv_txmx) {

        }

        if (view.getId() == R.id.tv_qbtx) {

        }
    }

}
