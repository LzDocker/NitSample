package com.bfhd.account.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.R;
import com.bfhd.account.databinding.AccountActivityMoneyBoxBinding;
import com.bfhd.account.vm.AccountViewModel;
import com.bfhd.circle.base.HivsBaseActivity;
import com.bfhd.circle.base.ViewEventResouce;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/*
 * 钱包 wj
 **/
public class AccoutMoneyBoxActivity extends HivsBaseActivity<AccountViewModel, AccountActivityMoneyBoxBinding> {


    @Inject
    ViewModelProvider.Factory factory;
    private Disposable disposable;


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
        disposable = RxBus.getDefault().toObservable(RxEvent.class).subscribe(rxEvent -> {
            if (rxEvent.getT().equals("refresh_tx")) {
                mViewModel.moneyBox();
            }
        });
        mViewModel.moneyBox();
    }

    @Override
    public void initView() {
        mToolbar.setTitle("我的钱包");
        // 提现
        mBinding.accountTvTx.setOnClickListener(v -> {
            String money = mBinding.accountTvMoney.getText().toString().trim();
            if (!TextUtils.isEmpty(money)) {
                float m = Float.valueOf(money);
                if (m < 0.0 || m == 0.0) {
                    ToastUtils.showShort("您没有可提现的余额");
                } else {
                    Intent intent = new Intent(AccoutMoneyBoxActivity.this, AccoutMoneyTXActivity.class);
                    startActivity(intent);
                }
            }else{
                ToastUtils.showShort("您没有可提现的余额");
            }

        });

        // 明细
        mBinding.accountTvMx.setOnClickListener(v -> {
            Intent intent = new Intent(AccoutMoneyBoxActivity.this, AccounMoneyDetailListActivity.class);
            startActivity(intent);
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }

    @Override
    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);
        switch (viewEventResouce.eventType) {
            case 113:
                if (viewEventResouce.data != null) {
                    String money = (String) viewEventResouce.data;
                    mBinding.accountTvMoney.setText(money);
                }
                break;
        }
    }
}
