package com.bfhd.account.ui.tygs;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.BR;
import com.bfhd.account.R;
import com.bfhd.account.databinding.AccountActivityAttentionListBinding;
import com.bfhd.account.vm.AccountAttentionViewModel;
import com.bfhd.account.vm.AccountViewModel;
import com.bfhd.account.vo.FansVo;
import com.bfhd.circle.base.HivsBaseActivity;
import com.bfhd.circle.base.ViewEventResouce;
import com.bfhd.circle.base.adapter.HivsSampleAdapter;
import com.bfhd.circle.vo.bean.StaPersionDetail;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonListActivity;

import java.util.Collection;

import javax.inject.Inject;

/*
 * 关注列表
 **/

@Route(path = AppRouter.ACCOUNT_ATTEN_LISt)
public class AccounTygsAttentionListActivity extends NitCommonListActivity<AccountAttentionViewModel> {


    @Inject
    ViewModelProvider.Factory factory;


    @Override
    public AccountAttentionViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(AccountAttentionViewModel.class);
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
        commonListReq.ReqParam.put("uuid", "3c29a4eed44db285468df3443790e64a");
        commonListReq.ReqParam.put("memberid", "3");
//        commonListReq.externs.put("default",mStaCirParam.getCircleid());
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        if ("fans".equals(type)) {
            mToolbar.setTitle("我的粉丝");
        } else if ("attention".equals(type)) {
            mToolbar.setTitle("我的关注");
        }

    }

    @Override
    public void initObserver() {

    }


}
