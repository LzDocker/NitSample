package com.bfhd.account.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.R;
import com.bfhd.account.databinding.AccountActivityNumCountryBinding;
import com.bfhd.account.vm.AccountViewModel;
import com.bfhd.circle.base.HivsBaseActivity;
import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.docker.common.common.router.AppRouter;
import com.gyf.immersionbar.ImmersionBar;
import javax.inject.Inject;

/*
 * 选择国家代码  选择地址 v2
 **/
@Route(path = AppRouter.ACCOUNT_COUNTRY)
public class AccounSelectCountryNumActivity extends HivsBaseActivity<AccountViewModel, AccountActivityNumCountryBinding> {


    @Inject
    ViewModelProvider.Factory factory;

    private AccountCityCoutainerFragment countryNumListFragment;
    private AccountCountrySearchFragment countrySearchFragment;

    @Override
    public AccountViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(AccountViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.account_activity_num_country;
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

        mToolbar.setTitle("城市选择");
        mBinding.editQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (countryNumListFragment.getList() == null && countryNumListFragment.getList().size() == 0) {
                    return;
                }
                if (!TextUtils.isEmpty(mBinding.editQuery.getText().toString().trim()) && countryNumListFragment.getList().size() > 0) {
                    if (countrySearchFragment == null) {
                        countrySearchFragment = new AccountCountrySearchFragment();
                        FragmentUtils.add(getSupportFragmentManager(), countrySearchFragment, R.id.frame, "CountrySearch");
                    } else {
                        FragmentUtils.show(FragmentUtils.findFragment(getSupportFragmentManager(), "CountrySearch"));
                    }
                    FragmentUtils.hide(FragmentUtils.findFragment(getSupportFragmentManager(), "countryNumList"));
                    countrySearchFragment.bindDatas(countryNumListFragment.getList(),countryNumListFragment.getCurrentPosition());
                    countrySearchFragment.bindQueryText(mBinding.editQuery.getText().toString().trim());
                } else {
                    if (countrySearchFragment != null) {
                        Boolean isVisibleToUser = countrySearchFragment.isResumed() && countrySearchFragment.getUserVisibleHint();
                        if (isVisibleToUser) {
                            FragmentUtils.hide(FragmentUtils.findFragment(getSupportFragmentManager(), "CountrySearch"));
                            FragmentUtils.show(FragmentUtils.findFragment(getSupportFragmentManager(), "countryNumList"));
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        countryNumListFragment = new AccountCityCoutainerFragment();
        FragmentUtils.add(getSupportFragmentManager(), countryNumListFragment, R.id.frame, "countryNumList");
        mBinding.tvCancel.setOnClickListener(v -> {
            finish();
        });
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                .statusBarColor("#ffffff")
                .statusBarDarkFont(true)   //状态栏字体是深色，不写默认为亮色
                .autoDarkModeEnable(true) //自动状态栏字体和导航栏图标变色，必须指定状态栏颜色和导航栏颜色才可以自动变色哦
                .autoStatusBarDarkModeEnable(true, 0.2f) //自动状态栏字体变色，必须指定状态栏颜色才可以自动变色哦
                .autoNavigationBarDarkModeEnable(true, 0.2f) //自动导航栏图标变色，必须指定导航栏颜色才可以自动变色哦
                .flymeOSStatusBarFontColor("#000000")  //修改flyme OS状态栏字体颜色
                .navigationBarColor("#ffffff")
                .fullScreen(true)
                .addTag("PicAndColor")
                .init();
    }

}
