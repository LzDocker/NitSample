package com.bfhd.account.ui.tygs;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.R;
import com.bfhd.account.databinding.AccountActivityRewardBinding;
import com.bfhd.account.vm.card.AccountPointRecycleViewModel;
import com.bfhd.account.vo.tygs.AccountEarnHeadVo;
import com.bfhd.account.vo.tygs.AccountPointHeadVo;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.vm.NitEmptyViewModel;
import com.docker.common.common.widget.card.NitBaseProviderCard;

import javax.inject.Inject;

/*
 * 我的积分
 **/

@Route(path = AppRouter.ACCOUNT_point)
public class AccounPointActivity extends NitCommonActivity<NitEmptyViewModel, AccountActivityRewardBinding> {

    @Inject
    ViewModelProvider.Factory factory;
    private NitCommonListVm outerVm;
    private String type;


    @Override
    protected int getLayoutId() {
        return R.layout.account_activity_point;
    }

    @Override
    public NitEmptyViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(NitEmptyViewModel.class);
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

        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        if ("point".equals(type)) {
            mToolbar.setTitle("积分收益");
        } else if ("earn".equals(type)) {
            mToolbar.setTitle("我的收益");
        }
        CommonListOptions commonListOptions = new CommonListOptions();
        commonListOptions.RvUi = Constant.KEY_RVUI_LINER;
        commonListOptions.falg = 0;
        commonListOptions.refreshState = Constant.KEY_REFRESH_ONLY_LOADMORE;
        commonListOptions.isActParent = true;
        NitBaseProviderCard.providerCoutainerForFrame(this.getSupportFragmentManager(), R.id.frame_header, commonListOptions);
    }

    @Override
    public NitDelegetCommand providerNitDelegetCommand(int flag) {
        NitDelegetCommand nitDelegetCommand = new NitDelegetCommand() {
            @Override
            public Class providerOuterVm() {
                return AccountPointRecycleViewModel.class;
            }

            @Override
            public void next(NitCommonListVm commonListVm, NitCommonFragment nitCommonFragment) {

                if ("point".equals(type)) {
                    AccountPointHeadVo accountPointHeadVo = new AccountPointHeadVo(0, 0);
                    NitBaseProviderCard.providerCard(commonListVm, accountPointHeadVo, nitCommonFragment);
                    accountPointHeadVo.setPoint("111");
                } else if ("earn".equals(type)) {
                    AccountEarnHeadVo accountEarnHeadVo = new AccountEarnHeadVo(0, 0);
                    NitBaseProviderCard.providerCard(commonListVm, accountEarnHeadVo, nitCommonFragment);
                    accountEarnHeadVo.setPoint("111");
                }
            }
        };
        return nitDelegetCommand;
    }


    @Override
    public void initObserver() {

    }

    @Override
    public void initRouter() {

    }

}
