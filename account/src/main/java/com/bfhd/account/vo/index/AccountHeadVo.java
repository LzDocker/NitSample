package com.bfhd.account.vo.index;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.R;
import com.bfhd.account.vo.MyInfoVo;
import com.bfhd.circle.BR;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.BaseItemModel;
import com.docker.common.common.model.CommonContainerOptions;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.model.OnItemClickListener;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
public class AccountHeadVo extends BaseObservable implements BaseItemModel {

    @Override
    public int getItemLayout() {
        return R.layout.account_fragment_mine_index_header;
    }

    @Override
    public OnItemClickListener getOnItemClickListener() {
        return (item, view) -> {
            if (view.getId() == R.id.account_iv_setting) { // 设置界面
//                ARouter.getInstance().build(AppRouter.ACCOUNT_ATTEN_SETTING).navigation();
                CommonContainerOptions options = new CommonContainerOptions();
                options.title = "设置";
                options.commonListOptions = new CommonListOptions();
                ARouter.getInstance().build(AppRouter.COMMON_CONTAINER)
                        .withSerializable(Constant.ContainerParam, options)
                        .withSerializable(Constant.ContainerCommand, "com.bfhd.account.vm.AccountIndexListViewModel")
                        .navigation();
            }
            if (view.getId() == R.id.account_iv_message) {
                RxBus.getDefault().post(new RxEvent<>("change", 3));
            }
            if (view.getId() == R.id.account_iv_user_icon) {

            }
            if (view.getId() == R.id.ll_mine_dynamic) {

            }
            if (view.getId() == R.id.ll_mine_company_dz) {

            }
            if (view.getId() == R.id.ll_mine_comment) {

            }
        };
    }

    public MyInfoVo myinfo;

    @Bindable
    public MyInfoVo getMyinfo() {
        return myinfo;
    }

    public void setMyinfo(MyInfoVo myinfo) {
        this.myinfo = myinfo;
        notifyPropertyChanged(BR.myinfo);
    }
}
