package com.bfhd.account.ui.tygs;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.R;
import com.bfhd.account.databinding.AccountActivityRewardBinding;
import com.bfhd.account.vm.AccountPointViewModel;
import com.bfhd.account.vm.card.AccountPointRecycleViewModel;
import com.bfhd.account.vo.tygs.AccountEarnHeadVo;
import com.bfhd.account.vo.tygs.AccountPointHeadVo;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.common.common.widget.card.NitBaseProviderCard;

import javax.inject.Inject;

/*
 * 我的积分
 **/

@Route(path = AppRouter.ACCOUNT_point)
public class AccounPointActivity extends NitCommonActivity<AccountPointViewModel, AccountActivityRewardBinding> {

    @Inject
    ViewModelProvider.Factory factory;
    private AccountPointRecycleViewModel outerVm;
    private String type;


    @Override
    protected int getLayoutId() {
        return R.layout.account_activity_point;
    }

    @Override
    public AccountPointViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(AccountPointViewModel.class);
    }


    @Override
    protected void inject() {
        super.inject();
        ARouter.getInstance().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel.fetchPointTotal();
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
        commonListOptions.refreshState = Constant.KEY_REFRESH_OWNER;
        commonListOptions.isActParent = true;
        UserInfoVo userInfoVo = CacheUtils.getUser();
        commonListOptions.ReqParam.put("memberid", userInfoVo.uid);
        commonListOptions.ReqParam.put("uuid", userInfoVo.uuid);
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
                outerVm = (AccountPointRecycleViewModel) commonListVm;
                if ("point".equals(type)) {
                    outerVm.scope = 0;
                    AccountPointHeadVo accountPointHeadVo = new AccountPointHeadVo(0, 0);
                    accountPointHeadVo.isNoNetNeed = true;
                    NitBaseProviderCard.providerCard(commonListVm, accountPointHeadVo, nitCommonFragment);
                    mViewModel.myInfoDispatcherVoMediatorLiveData.observe(AccounPointActivity.this, myInfoDispatcherVo -> {
                        if (myInfoDispatcherVo != null) {
                            if (myInfoDispatcherVo.member != null) {
                                accountPointHeadVo.setPoint(myInfoDispatcherVo.member.getPoint());
                            }
                        }
                    });
                } else if ("earn".equals(type)) {
                    outerVm.scope = 1;
                    AccountEarnHeadVo accountEarnHeadVo = new AccountEarnHeadVo(0, 0);
                    accountEarnHeadVo.isNoNetNeed = true;
                    NitBaseProviderCard.providerCard(commonListVm, accountEarnHeadVo, nitCommonFragment);
                    mViewModel.myInfoDispatcherVoMediatorLiveData.observe(AccounPointActivity.this, myInfoDispatcherVo -> {
                        if (myInfoDispatcherVo != null) {
                            if (myInfoDispatcherVo.member != null) {
                                accountEarnHeadVo.setPoint(myInfoDispatcherVo.member.amount);
                            }
                        }
                    });
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
