package com.bfhd.account.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.bfhd.account.R;
import com.bfhd.account.databinding.AccountActivitySafeCollecBinding;
import com.bfhd.account.vm.AccountViewModel;
import com.bfhd.circle.base.HivsBaseActivity;
import com.bfhd.circle.ui.safe.DynamicFragment;
import com.bfhd.circle.vo.StaDynaVo;
import com.docker.common.common.adapter.CommonpagerAdapter;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vo.UserInfoVo;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/*
 * 收藏列表  ---- wj
 **/
public class AccountSafeCollectionListActivity extends HivsBaseActivity<AccountViewModel, AccountActivitySafeCollecBinding> {
    @Inject
    ViewModelProvider.Factory factory;

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
    }


    @Override
    public void initView() {
        mToolbar.setTitle("我收藏的");
        String[] titleArr = new String[]{"宝贝", "文章", "动态"};
        List<Fragment> fragments = new ArrayList<>();
        UserInfoVo userInfoVo = CacheUtils.getUser();
        StaDynaVo staDynaVo = new StaDynaVo();
        staDynaVo.UiType = 2;
        staDynaVo.ReqType = 1;
        staDynaVo.ReqParam.put("memberid", userInfoVo.uid);
        staDynaVo.ReqParam.put("uuid", userInfoVo.uuid);
        staDynaVo.ReqParam.put("t", "search");
        staDynaVo.ReqParam.put("act", "collect");
        staDynaVo.ReqParam.put("goodsui", "product");

        fragments.add(DynamicFragment.newInstance(staDynaVo, null));

        StaDynaVo staDynaVo1 = new StaDynaVo();
        staDynaVo1.UiType = 2;
        staDynaVo1.ReqType = 1;
        staDynaVo1.ReqParam.put("memberid", userInfoVo.uid);
        staDynaVo1.ReqParam.put("uuid", userInfoVo.uuid);
        staDynaVo1.ReqParam.put("t", "news");
        staDynaVo1.ReqParam.put("act", "collect");

        fragments.add(DynamicFragment.newInstance(staDynaVo1, null));


        StaDynaVo staDynaVo2 = new StaDynaVo();
        staDynaVo2.UiType = 2;
        staDynaVo2.ReqType = 1;
        staDynaVo2.ReqParam.put("memberid", userInfoVo.uid);
        staDynaVo2.ReqParam.put("uuid", userInfoVo.uuid);
        staDynaVo2.ReqParam.put("t", "dynamic");
        staDynaVo2.ReqParam.put("act", "collect");
        fragments.add(DynamicFragment.newInstance(staDynaVo2, null));

//        fragments.add((Fragment) ARouter.getInstance().build(AppRouter.Pro_Training_collect).navigation());
        mBinding.viewPager.setAdapter(new CommonpagerAdapter(getSupportFragmentManager(), fragments, titleArr));
        mBinding.proTabTitle.setViewPager(mBinding.viewPager);
        mBinding.proTabTitle.setCurrentTab(0);


    }

}
