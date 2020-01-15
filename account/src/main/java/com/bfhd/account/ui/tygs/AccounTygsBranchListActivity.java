package com.bfhd.account.ui.tygs;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.vm.AccountAttentionViewModel;
import com.bfhd.account.vm.AccountBranchViewModel;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonListActivity;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vo.UserInfoVo;

import javax.inject.Inject;

/*
 * 我的分部列表
 **/

@Route(path = AppRouter.ACCOUNT_BRANCH_LIST)
public class AccounTygsBranchListActivity extends NitCommonListActivity<AccountBranchViewModel> {


    @Inject
    ViewModelProvider.Factory factory;

    @Override
    public AccountBranchViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(AccountBranchViewModel.class);
    }


    @Override
    protected void inject() {
        super.inject();
        ARouter.getInstance().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        commonListReq = new CommonListOptions();
        commonListReq.refreshState = Constant.KEY_REFRESH_OWNER;
        UserInfoVo userInfoVo = CacheUtils.getUser();
        commonListReq.ReqParam.put("uuid", userInfoVo.uuid);
        commonListReq.ReqParam.put("memberid", userInfoVo.uid);
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initView() {
        mToolbar.setTitle("我的分部");


    }

    @Override
    public void initObserver() {

    }


}
