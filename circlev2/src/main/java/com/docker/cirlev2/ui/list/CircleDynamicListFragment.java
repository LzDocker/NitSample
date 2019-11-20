package com.docker.cirlev2.ui.list;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.cirlev2.vm.CircleDynamicListViewModel;
import com.docker.common.common.config.CommonConstant;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonListFragment;

@Route(path = AppRouter.CIRCLE_DYNAMIC_LIST_FRAME)
public class CircleDynamicListFragment extends NitCommonListFragment<CircleDynamicListViewModel> {

    CommonListOptions commonListReq = new CommonListOptions();


    @Override
    public CircleDynamicListViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleDynamicListViewModel.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceStates) {
        super.onCreate(savedInstanceStates);
        ARouter.getInstance().inject(this);
    }

    @Override
    protected void initView(View var1) {

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public CommonListOptions getArgument() {

        commonListReq.refreshState = Constant.KEY_REFRESH_ONLY_LOADMORE;
        commonListReq.ReqParam.put("uuid", "3c29a4eed44db285468df3443790e64a");
//        commonListReq.ReqParam.put("uuid", "8621e837a2a1579710a95143e5862424");
        commonListReq.ReqParam.put("memberid", "3");
//        commonListReq.ReqParam.put("memberid", "64");
//        commonListReq.ReqParam.put("companyid", "1");
        return commonListReq;
    }

    @Override
    public void initImmersionBar() {

    }

    @Override
    public void onVisible() {
        super.onVisible();

    }
}
