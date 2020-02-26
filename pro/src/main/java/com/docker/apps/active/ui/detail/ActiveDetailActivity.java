package com.docker.apps.active.ui.detail;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.docker.apps.R;
import com.docker.apps.databinding.ProActiveDetailActivityBinding;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.vm.NitEmptyViewModel;

@Route(path = AppRouter.ACTIVE_DEATIL_ACTIVITY)
public class ActiveDetailActivity extends NitCommonActivity<NitEmptyViewModel, ProActiveDetailActivityBinding> {


    public int edit = 0;

    @Override
    public void initView() {
        mToolbar.setTitle("活动详情");

        mToolbar.setIvRight(R.mipmap.open_meun, v -> {

        });
    }

    @Override
    public void initObserver() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fragment fragment = (Fragment) ARouter.getInstance()
                .build(AppRouter.ACTIVE_DEATIL)
                .withInt("edit", getIntent().getIntExtra("edit", 0))
                .withSerializable("activityid", getIntent().getStringExtra("activityid")).navigation();
        FragmentUtils.add(getSupportFragmentManager(), fragment, R.id.frame);
    }

    @Override
    public void initRouter() {
        ARouter.getInstance().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.pro_active_detail_activity;
    }

    @Override
    public NitEmptyViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(NitEmptyViewModel.class);
    }
}
