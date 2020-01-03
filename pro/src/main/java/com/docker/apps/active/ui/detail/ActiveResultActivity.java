package com.docker.apps.active.ui.detail;

import android.arch.lifecycle.ViewModelProviders;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.apps.R;
import com.docker.apps.databinding.ProActiveResultActivityBinding;
import com.docker.apps.databinding.ProActiveSuccActivityBinding;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.vm.NitEmptyViewModel;

@Route(path = AppRouter.ACTIVE_RESULT)
public class ActiveResultActivity extends NitCommonActivity<NitEmptyViewModel, ProActiveResultActivityBinding> {

    @Override
    public void initView() {
        mToolbar.setTitle("报名结果");

        mBinding.tvOther.setOnClickListener(v -> {
            // 活动列表

        });
        mBinding.tvMineActive.setOnClickListener(v -> {
            // 我的活动
        });
    }

    @Override
    public void initObserver() {

    }

    @Override
    public void initRouter() {
        ARouter.getInstance().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.pro_active_result_activity;
    }

    @Override
    public NitEmptyViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(NitEmptyViewModel.class);
    }

}

