package com.docker.common.common.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.docker.common.R;
import com.docker.common.common.binding.ViewOnClickBindingAdapter;
import com.docker.common.common.command.NitContainerCommand;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.vm.RxDemoViewModel;
import com.docker.common.databinding.CommonActivityRxdemoBinding;

import javax.inject.Inject;

///livedata demo
@Route(path = AppRouter.COMMON_LIVEDATA)
public class LivedataDemoActivity extends NitCommonActivity<RxDemoViewModel, CommonActivityRxdemoBinding> {

    @Inject
    ViewModelProvider.Factory factory;

    @Override
    protected int getLayoutId() {
        return R.layout.common_activity_rxdemo;
    }

    @Override
    public RxDemoViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(RxDemoViewModel.class);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





    }

    @Override
    public void initView() {

    }

    @Override
    public void initRouter() {
        ARouter.getInstance().inject(this);
    }


    @Override
    public void initObserver() {

    }

    @Override
    public NitContainerCommand providerNitContainerCommand(int flag) {
        return (NitContainerCommand) () -> (RxDemoViewModel.class);
    }


}
