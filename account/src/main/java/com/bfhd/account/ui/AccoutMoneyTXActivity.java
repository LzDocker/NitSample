package com.bfhd.account.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.R;
import com.bfhd.account.databinding.AccountActivityMoneyTxBinding;
import com.bfhd.account.vm.AccountViewModel;
import com.bfhd.circle.base.HivsBaseActivity;
import com.bfhd.circle.base.ViewEventResouce;
import com.dcbfhd.utilcode.utils.ToastUtils;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/*
 * 钱包 wj  提现
 **/
public class AccoutMoneyTXActivity extends HivsBaseActivity<AccountViewModel, AccountActivityMoneyTxBinding> {


    @Inject
    ViewModelProvider.Factory factory;
    private Disposable disposable;


    @Override
    public AccountViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(AccountViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.account_activity_money_tx;
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
        mToolbar.setTitle("提现");
        SpannableString ss = new SpannableString("请输入提现金额");//定义hint的值
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(15, true);//设置字体大小 true表示单位是sp
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mBinding.editTx.setHint(new SpannedString(ss));
        mBinding.accountTvTx.setOnClickListener(v -> {
            if (TextUtils.isEmpty(mBinding.editTx.getText())) {
                ToastUtils.showShort("请输入提现金额");
                return;
            }
            float money = Float.valueOf(mBinding.editTx.getText().toString().trim());
            if (money < 10.00) {
                ToastUtils.showShort("提现金额最少为10元");
                return;
            }
            mViewModel.txMoney(mBinding.editTx.getText().toString().trim());


        });


    }

    @Override
    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);
        switch (viewEventResouce.eventType) {
            case 113:
                AccoutMoneyTXActivity.this.finish();
                break;
        }
    }

}
