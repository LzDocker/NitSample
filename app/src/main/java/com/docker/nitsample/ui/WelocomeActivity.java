package com.docker.nitsample.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Window;
import android.view.WindowManager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.vm.AccountViewModel;
import com.bfhd.circle.base.adapter.CommonpagerAdapter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.nitsample.R;
import com.docker.nitsample.databinding.ActivityWelcomeBinding;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/*
 * 欢迎界面
 * */
public class WelocomeActivity extends NitCommonActivity<AccountViewModel, ActivityWelcomeBinding> {

    @Inject
    ViewModelProvider.Factory factory;

    private CommonpagerAdapter commonpagerAdapter;
    private List<Fragment> fragments;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    public AccountViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(AccountViewModel.class);
    }

    @Override
    protected void inject() {
        super.inject();
        ARouter.getInstance().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        isOverrideContentView = true;
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .transparentBar()
                .init();
    }

    @Override
    public void initView() {
        int[] bacArr = new int[]{
                R.drawable.welcome_style1,
                R.drawable.welcome_style2,
                R.drawable.welcome_style3,
                R.drawable.welcome_style4};
        fragments = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            fragments.add(WelcomeFragment.newInstance(i, bacArr[i]));
        }
        commonpagerAdapter = new CommonpagerAdapter(getSupportFragmentManager(), fragments);
        mBinding.viewpager.setAdapter(commonpagerAdapter);
    }

    @Override
    public void initObserver() {

    }

    @Override
    public void initRouter() {

    }

}
