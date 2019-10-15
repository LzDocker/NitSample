package com.bfhd.account.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.R;
import com.bfhd.account.databinding.AccountActivityMineActiveBinding;
import com.bfhd.account.vm.AccountViewModel;
import com.bfhd.circle.base.HivsBaseActivity;
import com.bfhd.circle.ui.safe.DynamicFragment;
import com.bfhd.circle.vo.StaDynaVo;
import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vo.UserInfoVo;
import javax.inject.Inject;

/*
 * 我的动态
 **/
public class AccountMineActiveActivity extends HivsBaseActivity<AccountViewModel, AccountActivityMineActiveBinding> {
    @Inject
    ViewModelProvider.Factory factory;

    @Override
    public AccountViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(AccountViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.account_activity_mine_active;
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

    public void initView() {
        StaDynaVo staDynaVo = new StaDynaVo();
        staDynaVo.UiType = 2;
        staDynaVo.ReqType = 1;
        UserInfoVo userInfoVo = CacheUtils.getUser();
        staDynaVo.ReqParam.put("memberid", userInfoVo.uid);
        staDynaVo.ReqParam.put("uuid", userInfoVo.uuid);
        staDynaVo.ReqParam.put("memberid2", userInfoVo.uid);
        staDynaVo.ReqParam.put("uuid2", userInfoVo.uuid);
        staDynaVo.ReqParam.put("t", "circle");
        FragmentUtils.add(getSupportFragmentManager(), DynamicFragment.newInstance(staDynaVo, null), R.id.account_mine_active_frame);
    }

}
