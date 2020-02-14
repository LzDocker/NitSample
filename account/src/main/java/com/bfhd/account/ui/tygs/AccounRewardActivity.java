package com.bfhd.account.ui.tygs;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.R;
import com.bfhd.account.databinding.AccountActivityRewardBinding;
import com.bfhd.account.databinding.AccountActivityRewardV2Binding;
import com.bfhd.account.vm.AccountAttentionViewModel;
import com.bfhd.account.vm.AccountRewardViewModel;
import com.bfhd.account.vo.tygs.AccountRewardHeadVo;
import com.docker.cirlev2.vo.entity.CircleTitlesVo;
import com.docker.cirlev2.vo.param.StaCirParam;
import com.docker.common.common.adapter.CommonpagerAdapter;
import com.docker.common.common.adapter.CommonpagerStateAdapter;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.ui.base.NitCommonListActivity;
import com.docker.common.common.ui.container.NitCommonContainerFragmentV2;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.widget.card.NitBaseProviderCard;
import com.docker.common.common.widget.indector.CommonIndector;
import com.docker.common.common.widget.refresh.api.RefreshLayout;
import com.docker.common.common.widget.refresh.listener.OnRefreshListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

import static com.docker.common.common.config.Constant.CommonListParam;

/*
 * 推广的人====我的奖励
 *
 * todo
 **/

@Route(path = AppRouter.ACCOUNT_reward)
public class AccounRewardActivity extends NitCommonActivity<NitCommonContainerViewModel, AccountActivityRewardV2Binding> {
    public ArrayList<Fragment> fragments = new ArrayList<>();
    private NitCommonListVm OutercardVm;
    private Disposable disposable;

    @Override
    protected int getLayoutId() {
        return R.layout.account_activity_reward_v2;
    }

    @Override
    public NitCommonContainerViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(NitCommonContainerViewModel.class);
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
            if (rxEvent.getT().equals("TX_SUCCESS")) {
                if (OutercardVm != null) {
                    OutercardVm.loadCardData(accountRewardHeadVo);
                }
            }
        });
    }

    @Override
    public void initView() {
        mToolbar.setTitle("我的奖励");
        CommonListOptions commonListOptions = new CommonListOptions();
        commonListOptions.RvUi = Constant.KEY_RVUI_LINER;
        commonListOptions.falg = 0;
        commonListOptions.isActParent = true;
        NitBaseProviderCard.providerCardNoRefreshForFrame(this.getSupportFragmentManager(), R.id.frame_header, commonListOptions);
        peocessTab();

    }

    AccountRewardHeadVo accountRewardHeadVo;

    @Override
    public NitDelegetCommand providerNitDelegetCommand(int flag) {
        NitDelegetCommand nitDelegetCommand = null;
        switch (flag) {
            case 0:
                nitDelegetCommand = new NitDelegetCommand() {
                    @Override
                    public Class providerOuterVm() {
                        return null;
                    }

                    @Override
                    public void next(NitCommonListVm commonListVm, NitCommonFragment nitCommonFragment) {
                        accountRewardHeadVo = new AccountRewardHeadVo(0, 0);
                        accountRewardHeadVo.isNoNetNeed = true;
                        NitBaseProviderCard.providerCard(commonListVm, accountRewardHeadVo, nitCommonFragment);
                        OutercardVm = commonListVm;
                    }
                };
                break;
            case 1:
            case 2:
                nitDelegetCommand = new NitDelegetCommand() {
                    @Override
                    public Class providerOuterVm() {
                        return AccountRewardViewModel.class;
                    }

                    @Override
                    public void next(NitCommonListVm commonListVm, NitCommonFragment nitCommonFragment) {
                        ((AccountRewardViewModel) commonListVm).flag = flag;
                    }
                };
                break;
        }


        return nitDelegetCommand;
    }


    @Override
    public void initObserver() {

    }

    @Override
    public void initRouter() {

    }

    public void peocessTab() {

        CommonListOptions commonListOptions = new CommonListOptions();
        commonListOptions.refreshState = Constant.KEY_REFRESH_ONLY_LOADMORE;
        commonListOptions.isActParent = true;
        commonListOptions.falg = 1;
        commonListOptions.ReqParam.put("memberid", CacheUtils.getUser().uid);
        commonListOptions.ReqParam.put("uuid", CacheUtils.getUser().uuid);
        NitCommonContainerFragmentV2 nitCommonContainerFragmentV2 = NitCommonContainerFragmentV2.newinstance(commonListOptions);
        fragments.add(nitCommonContainerFragmentV2);

        CommonListOptions commonListOptions1 = new CommonListOptions();
        commonListOptions1.refreshState = Constant.KEY_REFRESH_ONLY_LOADMORE;
        commonListOptions1.isActParent = true;
        commonListOptions1.falg = 2;
        commonListOptions1.ReqParam.put("memberid", CacheUtils.getUser().uid);
        commonListOptions1.ReqParam.put("uuid", CacheUtils.getUser().uuid);
        NitCommonContainerFragmentV2 nitCommonContainerFragmentV21 = NitCommonContainerFragmentV2.newinstance(commonListOptions1);
        fragments.add(nitCommonContainerFragmentV21);

        String[] titles = new String[]{"已邀请", "已完成"};
        // magic
        mBinding.viewPager.setAdapter(new CommonpagerAdapter(this.getSupportFragmentManager(), fragments, titles));
        CommonIndector commonIndector = new CommonIndector();
        commonIndector.initMagicIndicator(titles, mBinding.viewPager, mBinding.magicIndicator, this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
