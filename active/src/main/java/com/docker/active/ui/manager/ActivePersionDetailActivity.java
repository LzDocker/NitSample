package com.docker.active.ui.manager;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.active.R;
import com.docker.active.databinding.ProActivePersionDetailBinding;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.vm.NitEmptyViewModel;

/*
 * 活动-人员详情
 **/

@Route(path = AppRouter.ACTIVE_MANAGER_PERSION_DETAIL)
public class ActivePersionDetailActivity extends NitCommonActivity<NitEmptyViewModel, ProActivePersionDetailBinding> {

    @Override
    public NitEmptyViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(NitEmptyViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.pro_active_persion_detail;
    }

    @Override
    protected void inject() {
        super.inject();
        ARouter.getInstance().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolbar.setTitle("人员详情");

    }

    @Override
    public void initView() {
    }

    @Override
    public void initObserver() {


    }

    @Override
    public void initRouter() {

    }
}
