package com.bfhd.account.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.R;
import com.bfhd.account.databinding.AccountActivitySafeCollecBinding;
import com.bfhd.account.vm.AccountViewModel;
import com.bfhd.circle.base.HivsBaseActivity;
import com.docker.common.common.adapter.CommonpagerAdapter;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/*
 * 和万家购买的
 **/
public class AccountWjBuyActivity extends HivsBaseActivity<AccountViewModel, AccountActivitySafeCollecBinding> {
    @Inject
    ViewModelProvider.Factory factory;
    private Disposable disposable;
    private List<Fragment> fragments;

    @Override
    public AccountViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(AccountViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.account_activity_safe_collec;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disposable = RxBus.getDefault().toObservable(RxEvent.class).subscribe(rxEvent -> {
            if (rxEvent.getT().equals("refresh_buy")) {
                mBinding.proTabTitle.setCurrentTab(1);
                mBinding.viewPager.setCurrentItem(mBinding.proTabTitle.getCurrentTab());
            }
        });

    }

    @Override
    public void initView() {
        mToolbar.setTitle("我购买的");
        String[] titleArr = new String[]{"待付款", "已完成"};
        fragments = new ArrayList<>();

//        fragments.add((Fragment) ARouter.getInstance().build(AppRouter.Pro_goods_buy).withString("status", "0").withString("is_push", "0").navigation());//待发货
//        fragments.add((Fragment) ARouter.getInstance().build(AppRouter.Pro_goods_buy).withString("status", "2").withString("is_push", "0").navigation());//已完成
//
        mBinding.viewPager.setAdapter(new CommonpagerAdapter(getSupportFragmentManager(), fragments, titleArr));
        mBinding.proTabTitle.setViewPager(mBinding.viewPager);
        mBinding.proTabTitle.setCurrentTab(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable!=null){
            disposable.dispose();
        }
    }
}
