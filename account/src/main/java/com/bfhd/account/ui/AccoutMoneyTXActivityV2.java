package com.bfhd.account.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.R;
import com.bfhd.account.databinding.AccountActivityMoneyTxV2Binding;
import com.bfhd.account.vm.MoneyBoxCommonViewModel;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.utils.ParamUtils;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;

import java.util.HashMap;

/*
 * 钱包 wj  提现
 **/
@Route(path = AppRouter.ACCOUNT_MONEY_HAND_V2)
public class AccoutMoneyTXActivityV2 extends NitCommonActivity<MoneyBoxCommonViewModel, AccountActivityMoneyTxV2Binding> {


    @Override
    public MoneyBoxCommonViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(MoneyBoxCommonViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.account_activity_money_tx_v2;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel.getMemberWithdraw();
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

            if (TextUtils.isEmpty(mBinding.getTxVo().bankman)) {
                ToastUtils.showShort("持卡人姓名不能为空");
                return;
            }
            if (TextUtils.isEmpty(mBinding.getTxVo().bank)) {
                ToastUtils.showShort("开户行不能为空");
                return;
            }
            if (TextUtils.isEmpty(mBinding.getTxVo().bankcard)) {
                ToastUtils.showShort("银行卡号不能为空");
                return;
            }
            if (TextUtils.isEmpty(mBinding.getTxVo().account_phone)) {
                ToastUtils.showShort("预留手机号不能为空");
                return;
            }

            HashMap<String, String> param = (HashMap<String, String>) ParamUtils.objectToMapRest(mBinding.getTxVo(), false);
            param.put("money",mBinding.editTx.getText().toString().trim());
            param.put("uuid", CacheUtils.getUser().uuid);
            param.put("memberid",CacheUtils.getUser().memberid);
            mViewModel.txMoney(param);
        });

        mBinding.tvAllIn.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(getIntent().getStringExtra("Txmoney"))) {
                mBinding.editTx.setText(getIntent().getStringExtra("Txmoney"));
            }
        });
    }

    @Override
    public void initObserver() {
        mViewModel.mTxMoneyLv.observe(this, s -> {
            if (!TextUtils.isEmpty(s)) {
                RxBus.getDefault().post(new RxEvent<>("TX_SUCCESS", ""));
                finish();
            }
        });

        mViewModel.mTxvoLv.observe(this, txmemberVo -> {
            if (txmemberVo != null) {
                mBinding.setTxVo(txmemberVo);
            }
        });

    }

    @Override
    public void initRouter() {
        ARouter.getInstance().inject(this);
    }


}
