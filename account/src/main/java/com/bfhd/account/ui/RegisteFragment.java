package com.bfhd.account.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.R;
import com.bfhd.account.databinding.AccountFragmenRegisterBinding;
import com.bfhd.circle.base.CommonFragment;
import com.bfhd.circle.base.EmptyVm;
import javax.inject.Inject;

public class RegisteFragment extends CommonFragment<EmptyVm, AccountFragmenRegisterBinding> {


    public static RegisteFragment newInstance() {
        return new RegisteFragment();
    }

    @Inject
    ViewModelProvider.Factory factory;

    @Override
    protected int getLayoutId() {
        return R.layout.account_fragmen_register;
    }

    @Override
    public EmptyVm getViewModel() {
        return ViewModelProviders.of(this, factory).get(EmptyVm.class);
    }

    @Override
    protected void initView(View var1) {


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void initImmersionBar() {
        superInitImmersionBar(R.color.white, null);
    }

}
