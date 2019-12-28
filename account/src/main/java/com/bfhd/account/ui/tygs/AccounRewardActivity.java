package com.bfhd.account.ui.tygs;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.R;
import com.bfhd.account.databinding.AccountActivityRewardBinding;
import com.bfhd.account.vm.AccountAttentionViewModel;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.ui.base.NitCommonListActivity;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;

import javax.inject.Inject;

/*
 * 推广的人====我的奖励
 **/

@Route(path = AppRouter.ACCOUNT_reward)
public class AccounRewardActivity extends NitCommonActivity<NitCommonContainerViewModel, AccountActivityRewardBinding> {


    @Inject
    ViewModelProvider.Factory factory;


    @Override
    protected int getLayoutId() {
        return R.layout.account_activity_reward;
    }

    @Override
    public AccountAttentionViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(AccountAttentionViewModel.class);
    }


    @Override
    protected void inject() {
        super.inject();
        ARouter.getInstance().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initView() {

    }


    @Override
    public NitDelegetCommand providerNitDelegetCommand(int flag) {
        NitDelegetCommand nitDelegetCommand = new NitDelegetCommand() {
            @Override
            public Class providerOuterVm() {
                return null;
            }

            @Override
            public void next(NitCommonListVm commonListVm, NitCommonFragment nitCommonFragment) {

            }
        };
        return nitDelegetCommand;
    }

    @Override
    public void initObserver() {

    }

    @Override
    public void initRouter() {

    }


}
