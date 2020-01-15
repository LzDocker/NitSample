package com.docker.nitsample.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vm.NitEmptyViewModel;
import com.docker.nitsample.R;
import com.docker.nitsample.databinding.WelcomeFragmentBinding;

import javax.inject.Inject;

/*
 * 欢迎页
 * */
public class WelcomeFragment extends NitCommonFragment<NitEmptyViewModel, WelcomeFragmentBinding> {

    private int index;
    private int bac;

    public static WelcomeFragment newInstance(int index, int bac) {
        WelcomeFragment welcomeFragment = new WelcomeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        bundle.putInt("bac", bac);
        welcomeFragment.setArguments(bundle);
        return welcomeFragment;
    }

    @Inject
    ViewModelProvider.Factory factory;


    @Override
    protected int getLayoutId() {
        return R.layout.welcome_fragment;
    }

    @Override
    protected NitEmptyViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(NitEmptyViewModel.class);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceStates) {
        super.onCreate(savedInstanceStates);

    }

    @Override
    protected void initView(View var1) {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        index = getArguments().getInt("index");
        bac = getArguments().getInt("bac");
        mBinding.get().ivWelcome.setImageResource(bac);
        if (index == 3) {
//            mBinding.get().tvEnter.setVisibility(View.VISIBLE);
            mBinding.get().getRoot().setOnClickListener(v -> {
                if (index == 3) {
                    CacheUtils.saveWelcomeFlag();
                    ARouter.getInstance().build(AppRouter.HOME).navigation();
                }
            });
        }
    }

    @Override
    public void initImmersionBar() {

    }
}
