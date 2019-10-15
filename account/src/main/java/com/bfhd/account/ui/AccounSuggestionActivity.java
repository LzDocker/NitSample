package com.bfhd.account.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.R;
import com.bfhd.account.databinding.AccountActivitySuggestionBinding;
import com.bfhd.account.vm.AccountViewModel;
import com.bfhd.circle.base.HivsBaseActivity;
import com.bfhd.circle.base.ViewEventResouce;
import com.dcbfhd.utilcode.utils.ToastUtils;
import javax.inject.Inject;

/*
 * 问题反馈
 **/
public class AccounSuggestionActivity extends HivsBaseActivity<AccountViewModel, AccountActivitySuggestionBinding> {


    @Inject
    ViewModelProvider.Factory factory;


    @Override
    public AccountViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(AccountViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.account_activity_suggestion;
    }

    @Override
    protected void inject() {
        super.inject();
        ARouter.getInstance().inject(this);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding.tvSubmit.setOnClickListener(v -> {
            //提交反馈意见
            String sug = mBinding.accountEdSug.getText().toString().trim();
            String phone = mBinding.accountEdCona.getText().toString().trim();
            if (TextUtils.isEmpty(sug)) {
                ToastUtils.showShort("请输入反馈意见");
                return;
            } else {
                mViewModel.commitSuggestion(sug, phone);
            }

        });

    }

    @Override
    public void initView() {
        mToolbar.setTitle("意见反馈");
    }

    @Override
    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);
            switch (viewEventResouce.eventType) {
                case 532:
                    AccounSuggestionActivity.this.finish();
                    break;
            }
    }
}
