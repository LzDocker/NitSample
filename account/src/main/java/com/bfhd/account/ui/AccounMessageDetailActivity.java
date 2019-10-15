package com.bfhd.account.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.R;
import com.bfhd.account.databinding.AccountActivityMoneyBoxBinding;
import com.bfhd.account.vm.AccountViewModel;
import com.bfhd.circle.base.HivsBaseActivity;
import javax.inject.Inject;

/*
 * 消息详情
 **/
public class AccounMessageDetailActivity extends HivsBaseActivity<AccountViewModel, AccountActivityMoneyBoxBinding> {


    @Inject
    ViewModelProvider.Factory factory;


    @Override
    public AccountViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(AccountViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.account_activity_money_box;
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
        mToolbar.setTitle("详情");

    }

}
