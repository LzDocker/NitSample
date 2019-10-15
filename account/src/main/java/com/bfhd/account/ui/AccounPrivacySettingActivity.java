package com.bfhd.account.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.R;
import com.bfhd.account.databinding.AccountActivityPrivacySettingBinding;
import com.bfhd.account.vm.AccountViewModel;
import com.bfhd.circle.base.HivsBaseActivity;
import com.docker.common.common.utils.versionmanager.AppVersionManager;
import com.docker.core.widget.BottomSheetDialog;

import javax.inject.Inject;

/*
 * 设置
 **/
public class AccounPrivacySettingActivity extends HivsBaseActivity<AccountViewModel, AccountActivityPrivacySettingBinding> {


    @Inject
    ViewModelProvider.Factory factory;
    private BottomSheetDialog bottomSheetDialog = new BottomSheetDialog();
    private String tel;
    @Inject
    AppVersionManager versionManager;

    @Override
    public AccountViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(AccountViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.account_activity_privacy_setting;
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
        mToolbar.setTitle("隐私设置");
        mBinding.linNoSee.setOnClickListener(v -> {
            Intent intent = new Intent(AccounPrivacySettingActivity.this, AccounNoSeeActivity.class);
            startActivity(intent);
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 10086 && resultCode == RESULT_OK) {

            }
        }
    }
}
