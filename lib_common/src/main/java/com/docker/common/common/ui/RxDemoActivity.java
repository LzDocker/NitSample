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

///评测------新概念英语课程列表
@Route(path = AppRouter.COMMON_RXJAVA)
public class RxDemoActivity extends NitCommonActivity<RxDemoViewModel, CommonActivityRxdemoBinding> {

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
        ViewOnClickBindingAdapter.onClick(mBinding.rxdemoConcat,v -> {
            mViewModel.rxConcatMap("44", "37c0fc402ba039c78b07065bfd4d114c");

        });
//        mBinding.rxdemoConcat.setOnClickListener(v -> {
//            mViewModel.rxConcatMap("44", "37c0fc402ba039c78b07065bfd4d114c");
//
//        });
        ViewOnClickBindingAdapter.onClick(mBinding.rxdemoMerge,v-> {
            mViewModel.rxMerge("44", "37c0fc402ba039c78b07065bfd4d114c");
        });

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
        mViewModel.mContainerLiveData.observe(this, object -> {
                mBinding.rxdemoData.setText("\n\n\n" + JSON.toJSONString(object));
        });
    }


    @Override
    public NitContainerCommand providerNitContainerCommand(int flag) {
        return (NitContainerCommand) () -> (RxDemoViewModel.class);
    }


}
