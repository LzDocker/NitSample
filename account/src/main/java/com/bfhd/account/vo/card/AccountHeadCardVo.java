package com.bfhd.account.vo.card;

import android.databinding.Bindable;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.BR;
import com.bfhd.account.R;
import com.bfhd.account.vm.AccountPointViewModel;
import com.bfhd.account.vm.card.AccountHeadCardViewModel;
import com.bfhd.account.vo.MyInfoVo;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonContainerOptions;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.vo.card.BaseCardVo;

public class AccountHeadCardVo extends BaseCardVo<MyInfoVo> {
    public transient MyInfoVo myinfo;

    public AccountHeadCardVo(int style, int position) {
        super(style, position);
        maxSupport = 3;
        mVmPath = "com.bfhd.account.vm.card.AccountHeadCardViewModel";
    }

    @Override
    public int getItemLayout() {
        int lay = R.layout.account_headvo_style_card;
        switch (style) {
            case 0:
                lay = R.layout.account_headvo_style_card;
                break;
            case 1:
                lay = R.layout.account_fragment_mine_index_header;
                break;
            case 2:
                lay = R.layout.account_fragment_mine_index_header_tygs;
                break;
        }
        return lay;
    }

    @Override
    public void onItemClick(BaseCardVo item, View view) {
        if (view.getId() == R.id.account_iv_setting) { // 设置界面
            CommonContainerOptions options = new CommonContainerOptions();
            options.title = "设置";
            options.commonListOptions = new CommonListOptions();
            ARouter.getInstance().build(AppRouter.COMMON_CONTAINER)
                    .withSerializable(Constant.ContainerParam, options)
                    .withSerializable(Constant.ContainerCommand, "com.bfhd.account.vm.AccountSettingViewModel")
                    .navigation();
            ((AccountHeadCardViewModel) mNitcommonCardViewModel).process();
        }


        if (view.getId() == R.id.account_iv_help) {

        }
        if (view.getId() == R.id.account_iv_message) {
            RxBus.getDefault().post(new RxEvent<>("change", 3));
        }
        if (view.getId() == R.id.account_iv_user_icon) {
        }
        if (view.getId() == R.id.ll_mine_point) {

            ARouter.getInstance().build(AppRouter.ACCOUNT_point).withString("type","point").navigation();
//            ((AccountPointViewModel) mNitcommonCardViewModel).process();
        }
        if (view.getId() == R.id.ll_mine_earn) {
            ARouter.getInstance().build(AppRouter.ACCOUNT_point).withString("type","earn").navigation();
        }

        if (view.getId() == R.id.ll_mine_company_dz) {
        }
        if (view.getId() == R.id.ll_mine_reward) {//推广的人 ---  我的收益
            ARouter.getInstance().build(AppRouter.ACCOUNT_reward).navigation();
        }
    }

    @Bindable
    public MyInfoVo getMyinfo() {
        return myinfo;
    }

    public void setMyinfo(MyInfoVo myinfo) {
        this.myinfo = myinfo;
        notifyPropertyChanged(BR.myinfo);
    }
}
