package com.docker.apps.active.ui.manager;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.apps.R;
import com.docker.apps.active.vm.ActivePersionListViewModel;
import com.docker.apps.databinding.ProActivePersionDetailBinding;
import com.docker.apps.databinding.ProActivePersionManagerBinding;
import com.docker.common.common.adapter.CommonpagerAdapter;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.ui.container.NitCommonContainerFragmentV2;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.vm.NitEmptyViewModel;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.widget.indector.CommonIndector;

import java.util.ArrayList;

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
